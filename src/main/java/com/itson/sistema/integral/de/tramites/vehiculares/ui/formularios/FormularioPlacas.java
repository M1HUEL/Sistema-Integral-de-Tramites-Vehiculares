package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.AutomovilDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.AutomovilDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PlacaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Automovil;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Persona;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioPlacas extends JPanel {

    public JTextField txtNumSerie, txtMarca, txtLinea, txtColor, txtModelo, txtNumPlacasAnteriores, txtMonto, txtRfcDuenio;
    public JComboBox<String> comboTipoAutomovil;
    public JButton btnAgregar, btnCancelar;

    private final PlacaDAO placaDAO = new PlacaDAOImpl();
    private final AutomovilDAO automovilDAO = new AutomovilDAOImpl();

    public FormularioPlacas() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Formulario de Placas");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel contenido = new JPanel(new GridLayout(0, 1, 10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        txtNumSerie = new JTextField();
        txtMarca = new JTextField();
        txtLinea = new JTextField();
        txtColor = new JTextField();
        txtModelo = new JTextField();
        txtNumPlacasAnteriores = new JTextField();
        txtNumPlacasAnteriores.setEnabled(false);
        txtMonto = new JTextField();
        txtMonto.setEnabled(false);
        txtRfcDuenio = new JTextField();

        comboTipoAutomovil = new JComboBox<>(new String[]{"Auto Nuevo", "Auto Usado"});

        btnAgregar = new JButton("Agregar");
        btnCancelar = new JButton("Cancelar");

        contenido.add(new CampoFormulario("RFC del dueño", txtRfcDuenio));
        contenido.add(new CampoFormulario("Número de serie", txtNumSerie));
        contenido.add(new CampoFormulario("Marca", txtMarca));
        contenido.add(new CampoFormulario("Línea", txtLinea));
        contenido.add(new CampoFormulario("Color", txtColor));
        contenido.add(new CampoFormulario("Modelo (año)", txtModelo));
        contenido.add(new CampoFormulario("Tipo de automóvil", comboTipoAutomovil));
        contenido.add(new CampoFormulario("Placas anteriores (si aplica)", txtNumPlacasAnteriores));
        contenido.add(new CampoFormulario("Monto", txtMonto));

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        contenido.add(panelBotones);

        add(titulo, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);

        comboTipoAutomovil.addActionListener(e -> actualizarFormulario());
        btnAgregar.addActionListener(e -> agregarPlaca());
        btnCancelar.addActionListener(e -> limpiarFormulario());

        actualizarFormulario();
    }

    private void actualizarFormulario() {
        String tipo = (String) comboTipoAutomovil.getSelectedItem();
        boolean esNuevo = "Auto Nuevo".equals(tipo);
        txtNumPlacasAnteriores.setEnabled(!esNuevo);
        txtNumPlacasAnteriores.setText("");

        double monto = esNuevo ? 1500.00 : 1000.00;
        txtMonto.setText(String.format("$%.2f", monto));
    }

    private boolean validarCampos() {
        if (txtRfcDuenio.getText().isBlank() || txtNumSerie.getText().isBlank() || txtMarca.getText().isBlank()
                || txtLinea.getText().isBlank() || txtColor.getText().isBlank() || txtModelo.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos de Dueño y Automóvil son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String tipo = (String) comboTipoAutomovil.getSelectedItem();
        if ("Auto Usado".equals(tipo) && txtNumPlacasAnteriores.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar las placas anteriores para automóviles usados.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.valueOf(txtModelo.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El modelo debe ser un número válido (año).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtRfcDuenio.setText("");
        txtNumSerie.setText("");
        txtMarca.setText("");
        txtLinea.setText("");
        txtColor.setText("");
        txtModelo.setText("");
        txtNumPlacasAnteriores.setText("");
        comboTipoAutomovil.setSelectedIndex(0);
        actualizarFormulario();
    }

    private void agregarPlaca() {
        if (!validarCampos()) {
            return;
        }

        try {
            Automovil auto = automovilDAO.buscarPorNumeroSerie(txtNumSerie.getText().trim());

            if (auto == null) {
                auto = new Automovil();
                auto.setNumeroSerie(txtNumSerie.getText().trim());
                auto.setMarca(txtMarca.getText().trim());
                auto.setLinea(txtLinea.getText().trim());
                auto.setColor(txtColor.getText().trim());
                auto.setModelo(Integer.valueOf(txtModelo.getText().trim()));

                Persona dueño = new Persona();

                auto.setPersona(dueño);
                automovilDAO.crear(auto);
            }

            Placa placa = new Placa();
            placa.setAutomovil(auto);
            placa.setFechaEmision(LocalDate.now());

            placa.setNumeroPlaca("GENERADO");

            placa.setCosto(Double.valueOf(txtMonto.getText().replace("$", "").replace(",", "")));

            if ("Auto Usado".equals((String) comboTipoAutomovil.getSelectedItem())) {
                // Aquí DEBERÍA haber lógica para invalidar/reemplazar la placa anterior
            }

            placaDAO.crear(placa);

            JOptionPane.showMessageDialog(this, "Placa agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato de número: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Error de persistencia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
