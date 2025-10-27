package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PlacaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Automovil;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioPlacas extends JPanel {

    public JTextField txtNumSerie, txtMarca, txtLinea, txtColor, txtModelo, txtNumPlacasAnteriores, txtMonto;
    public JComboBox<String> comboTipoTramite;
    public JCheckBox checkAutoNuevo;
    public JButton btnAgregar, btnCancelar;

    private final PlacaDAO placaDAO = new PlacaDAOImpl();

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

        comboTipoTramite = new JComboBox<>(new String[]{"Auto Nuevo", "Auto Usado"});
        checkAutoNuevo = new JCheckBox("¿Automóvil nuevo?", true);

        btnAgregar = new JButton("Agregar");
        btnCancelar = new JButton("Cancelar");

        contenido.add(new CampoFormulario("Número de serie", txtNumSerie));
        contenido.add(new CampoFormulario("Marca", txtMarca));
        contenido.add(new CampoFormulario("Línea", txtLinea));
        contenido.add(new CampoFormulario("Color", txtColor));
        contenido.add(new CampoFormulario("Modelo (año)", txtModelo));
        contenido.add(new CampoFormulario("Tipo de trámite", comboTipoTramite));
        contenido.add(new CampoFormulario("¿Automóvil nuevo?", checkAutoNuevo));
        contenido.add(new CampoFormulario("Placas anteriores", txtNumPlacasAnteriores));
        contenido.add(new CampoFormulario("Monto", txtMonto));

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        contenido.add(panelBotones);

        add(titulo, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);

        comboTipoTramite.addActionListener(e -> actualizarFormulario());
        checkAutoNuevo.addActionListener(e -> actualizarFormulario());
        btnAgregar.addActionListener(e -> agregarPlaca());
        btnCancelar.addActionListener(e -> limpiarFormulario());

        actualizarFormulario();
    }

    private void actualizarFormulario() {
        boolean esNuevo = comboTipoTramite.getSelectedItem().equals("Auto Nuevo") || checkAutoNuevo.isSelected();
        txtNumPlacasAnteriores.setEnabled(!esNuevo);
        double monto = esNuevo ? 1500 : 1000;
        txtMonto.setText("$" + monto);
    }

    private boolean validarCampos() {
        if (txtNumSerie.getText().isBlank() || txtMarca.getText().isBlank() || txtLinea.getText().isBlank()
                || txtColor.getText().isBlank() || txtModelo.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos de Automóvil son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!checkAutoNuevo.isSelected() && txtNumPlacasAnteriores.getText().isBlank()) {
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
        txtNumSerie.setText("");
        txtMarca.setText("");
        txtLinea.setText("");
        txtColor.setText("");
        txtModelo.setText("");
        txtNumPlacasAnteriores.setText("");
        txtMonto.setText("");
        comboTipoTramite.setSelectedIndex(0);
        checkAutoNuevo.setSelected(true);
        actualizarFormulario();
    }

    private void agregarPlaca() {
        if (!validarCampos()) {
            return;
        }

        try {
            Automovil auto = new Automovil();
            auto.setNumeroSerie(txtNumSerie.getText().trim());
            auto.setMarca(txtMarca.getText().trim());
            auto.setLinea(txtLinea.getText().trim());
            auto.setColor(txtColor.getText().trim());
            auto.setModelo(Integer.valueOf(txtModelo.getText().trim()));

            Placa placa = new Placa();
            placa.setAutomovil(auto);
            placa.setFechaEmision(LocalDate.now());
            placa.setCosto(Double.valueOf(txtMonto.getText().replace("$", "")));

            placaDAO.crear(placa);

            JOptionPane.showMessageDialog(this, "Placa agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar la placa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
