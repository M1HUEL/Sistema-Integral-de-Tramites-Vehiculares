package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.LicenciaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Persona;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;

import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;

public class FormularioLicencias extends JPanel {

    public JTextField txtRFC, txtNombre, txtFecha, txtTelefono, txtMonto;
    public JComboBox<String> comboPaises, comboVigencias;
    public JCheckBox checkDiscapacitado;
    public JButton btnAgregar, btnCancelar;
    private final LicenciaDAO licenciaDAO = new LicenciaDAOImpl();

    public FormularioLicencias() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Formulario de Licencia");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel contenido = new JPanel(new GridLayout(4, 3, 10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        txtRFC = new JTextField();
        txtNombre = new JTextField();
        txtFecha = new JTextField();
        txtTelefono = new JTextField();
        txtMonto = new JTextField();
        txtMonto.setEnabled(false);

        String[] paises = {"México", "Argentina", "Brasil", "Chile", "Colombia", "EEUU", "España", "Francia", "Italia", "Japón"};
        comboPaises = new JComboBox<>(paises);

        String[] vigencias = {"1 Year", "2 Year", "3 Year"};
        comboVigencias = new JComboBox<>(vigencias);

        checkDiscapacitado = new JCheckBox();

        btnAgregar = new JButton("Agregar");
        btnCancelar = new JButton("Cancelar");

        contenido.add(new CampoFormulario("RFC", txtRFC));
        contenido.add(new CampoFormulario("Nombre completo", txtNombre));
        contenido.add(new CampoFormulario("Fecha nacimiento", txtFecha));
        contenido.add(new CampoFormulario("Teléfono", txtTelefono));
        contenido.add(new CampoFormulario("País", comboPaises));
        contenido.add(new CampoFormulario("Vigencia", comboVigencias));
        contenido.add(new CampoFormulario("Monto", txtMonto));
        contenido.add(new CampoFormulario("Discapacidad", checkDiscapacitado));

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        contenido.add(panelBotones);

        add(titulo, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);

        comboVigencias.addActionListener(e -> calcularMonto());
        checkDiscapacitado.addActionListener(e -> calcularMonto());

        btnAgregar.addActionListener(e -> agregarLicencia());
        btnCancelar.addActionListener(e -> limpiarFormulario());
    }

    public void calcularMonto() {
        String vigenciaStr = (String) comboVigencias.getSelectedItem();
        boolean discapacitado = checkDiscapacitado.isSelected();

        if (vigenciaStr == null) {
            return;
        }

        double monto = switch (vigenciaStr) {
            case "1 Year" ->
                discapacitado ? 200 : 600;
            case "2 Year" ->
                discapacitado ? 500 : 900;
            case "3 Year" ->
                discapacitado ? 700 : 1100;
            default ->
                0;
        };

        txtMonto.setText("$" + monto);
    }

    public boolean validarCampos() {
        if (txtRFC.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || txtFecha.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()
                || comboPaises.getSelectedItem() == null
                || comboVigencias.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            LocalDate.parse(txtFecha.getText().trim());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public void limpiarFormulario() {
        txtRFC.setText("");
        txtNombre.setText("");
        txtFecha.setText("");
        txtTelefono.setText("");
        comboPaises.setSelectedIndex(0);
        comboVigencias.setSelectedIndex(0);
        checkDiscapacitado.setSelected(false);
        txtMonto.setText("");
    }

    private void agregarLicencia() {
        if (!validarCampos()) {
            return;
        }

        try {
            Licencia licencia = new Licencia();

            Persona persona = new Persona();
            persona.setRfc(txtRFC.getText().trim());
            persona.setNombreCompleto(txtNombre.getText().trim());
            persona.setFechaNacimiento(LocalDate.parse(txtFecha.getText().trim()));
            persona.setTelefono(txtTelefono.getText().trim());
            persona.setPais((String) comboPaises.getSelectedItem());

            licencia.setPersona(persona);
            licencia.setFechaExpedicion(LocalDate.now());
            licencia.setVigenciaAnios(Integer.parseInt(((String) comboVigencias.getSelectedItem()).split(" ")[0]));
            licencia.setDiscapacitado(checkDiscapacitado.isSelected());
            licencia.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));

            licenciaDAO.crear(licencia);

            JOptionPane.showMessageDialog(this, "Licencia agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar la licencia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
