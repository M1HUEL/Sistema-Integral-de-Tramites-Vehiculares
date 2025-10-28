package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Persona;
import com.itson.sistema.integral.de.tramites.vehiculares.servicios.ServicioLicencias;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioLicencias extends JPanel {

    private final JTextField txtRFC = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtFecha = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    public final JTextField txtMonto = new JTextField();

    private final JComboBox<String> comboPaises = new JComboBox<>(new String[]{
        "México", "Argentina", "Brasil", "Chile", "Colombia", "EEUU", "España", "Francia", "Italia", "Japón"
    });

    public final JComboBox<String> comboVigencias = new JComboBox<>(new String[]{
        "1 Año", "2 Años", "3 Años"
    });

    public final JCheckBox checkDiscapacitado = new JCheckBox();
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final ServicioLicencias servicioLicencias = new ServicioLicencias();

    public FormularioLicencias() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Formulario de Licencia", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
        agregarCampo(panelCampos, "RFC:", txtRFC);
        agregarCampo(panelCampos, "Nombre completo:", txtNombre);
        agregarCampo(panelCampos, "Fecha de nacimiento (YYYY-MM-DD):", txtFecha);
        agregarCampo(panelCampos, "Teléfono:", txtTelefono);
        agregarCampo(panelCampos, "País:", comboPaises);
        agregarCampo(panelCampos, "Vigencia:", comboVigencias);
        agregarCampo(panelCampos, "Monto:", txtMonto);
        agregarCampo(panelCampos, "¿Discapacitado?:", checkDiscapacitado);

        txtMonto.setEnabled(false);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        comboVigencias.addActionListener(e -> calcularMonto());
        checkDiscapacitado.addActionListener(e -> calcularMonto());
        btnAgregar.addActionListener(e -> agregarLicencia());
        btnCancelar.addActionListener(e -> limpiarFormulario());
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        panel.add(new JLabel(etiqueta));
        panel.add(campo);
    }

    private void calcularMonto() {
        String vigencia = (String) comboVigencias.getSelectedItem();
        boolean discapacitado = checkDiscapacitado.isSelected();
        if (vigencia == null) {
            return;
        }

        double monto = servicioLicencias.calcularMonto(vigencia, discapacitado);
        txtMonto.setText(String.format("$ %.2f", monto));
    }

    private boolean validarCampos() {
        if (txtRFC.getText().isBlank() || txtNombre.getText().isBlank()
                || txtFecha.getText().isBlank() || txtTelefono.getText().isBlank()) {
            mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        try {
            LocalDate.parse(txtFecha.getText().trim());
        } catch (DateTimeParseException e) {
            mostrarError("La fecha debe tener formato YYYY-MM-DD.");
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
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
            Persona persona = new Persona();
            persona.setRfc(txtRFC.getText().trim());
            persona.setNombreCompleto(txtNombre.getText().trim());
            persona.setFechaNacimiento(LocalDate.parse(txtFecha.getText().trim()));
            persona.setTelefono(txtTelefono.getText().trim());
            persona.setPais((String) comboPaises.getSelectedItem());

            Licencia licencia = new Licencia();
            licencia.setVigenciaAnios(Integer.parseInt(((String) comboVigencias.getSelectedItem()).split(" ")[0]));
            licencia.setDiscapacitado(checkDiscapacitado.isSelected());
            licencia.setMonto(Double.valueOf(txtMonto.getText().replace("$", "").trim()));

            servicioLicencias.tramitarLicencia(persona,
                    (String) comboVigencias.getSelectedItem(),
                    checkDiscapacitado.isSelected(),
                    licencia.getMonto());

            JOptionPane.showMessageDialog(this, "Licencia tramitada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (HeadlessException | NumberFormatException ex) {
            mostrarError("Error al tramitar licencia: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
