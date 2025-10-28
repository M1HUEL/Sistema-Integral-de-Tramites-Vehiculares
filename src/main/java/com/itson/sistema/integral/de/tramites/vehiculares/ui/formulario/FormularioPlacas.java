package com.itson.sistema.integral.de.tramites.vehiculares.ui.formulario;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.AutomovilDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.AutomovilDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PlacaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Automovil;
import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Persona;
import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Placa;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioPlacas extends JPanel {

    private final JTextField txtRfc = new JTextField();
    private final JTextField txtNumSerie = new JTextField();
    private final JTextField txtMarca = new JTextField();
    private final JTextField txtLinea = new JTextField();
    private final JTextField txtColor = new JTextField();
    private final JTextField txtModelo = new JTextField();
    private final JTextField txtPlacasAnteriores = new JTextField();
    private final JTextField txtMonto = new JTextField();

    private final JComboBox<String> comboTipoAuto = new JComboBox<>(new String[]{
        "Auto Nuevo", "Auto Usado"
    });

    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final PlacaDAO placaDAO = new PlacaDAOImpl();
    private final AutomovilDAO automovilDAO = new AutomovilDAOImpl();

    public FormularioPlacas() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Formulario de Placas", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
        agregarCampo(panelCampos, "RFC del dueño:", txtRfc);
        agregarCampo(panelCampos, "Número de serie:", txtNumSerie);
        agregarCampo(panelCampos, "Marca:", txtMarca);
        agregarCampo(panelCampos, "Línea:", txtLinea);
        agregarCampo(panelCampos, "Color:", txtColor);
        agregarCampo(panelCampos, "Modelo (año):", txtModelo);
        agregarCampo(panelCampos, "Tipo de automóvil:", comboTipoAuto);
        agregarCampo(panelCampos, "Placas anteriores (si aplica):", txtPlacasAnteriores);
        agregarCampo(panelCampos, "Monto:", txtMonto);

        txtMonto.setEnabled(false);
        txtPlacasAnteriores.setEnabled(false);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        comboTipoAuto.addActionListener(e -> actualizarMonto());
        btnAgregar.addActionListener(e -> agregarPlaca());
        btnCancelar.addActionListener(e -> limpiarFormulario());

        actualizarMonto();
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        panel.add(new JLabel(etiqueta));
        panel.add(campo);
    }

    private void actualizarMonto() {
        String tipo = (String) comboTipoAuto.getSelectedItem();
        if (tipo == null) {
            return;
        }

        double monto = tipo.equals("Auto Nuevo") ? 1500 : 1000;
        txtMonto.setText(String.format("$ %.2f", monto));

        txtPlacasAnteriores.setEnabled(tipo.equals("Auto Usado"));
        if (!tipo.equals("Auto Usado")) {
            txtPlacasAnteriores.setText("");
        }
    }

    private boolean validarCampos() {
        if (txtRfc.getText().isBlank() || txtNumSerie.getText().isBlank()
                || txtMarca.getText().isBlank() || txtLinea.getText().isBlank()
                || txtColor.getText().isBlank() || txtModelo.getText().isBlank()) {
            mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        if (comboTipoAuto.getSelectedItem().equals("Auto Usado") && txtPlacasAnteriores.getText().isBlank()) {
            mostrarError("Debe ingresar las placas anteriores para automóviles usados.");
            return false;
        }

        try {
            int modelo = Integer.parseInt(txtModelo.getText().trim());
            if (modelo < 1900 || modelo > LocalDate.now().getYear() + 1) {
                mostrarError("El modelo debe ser un año válido.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El modelo debe ser un número válido (año).");
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        txtRfc.setText("");
        txtNumSerie.setText("");
        txtMarca.setText("");
        txtLinea.setText("");
        txtColor.setText("");
        txtModelo.setText("");
        txtPlacasAnteriores.setText("");
        comboTipoAuto.setSelectedIndex(0);
        actualizarMonto();
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

                Persona duenio = new Persona();
                duenio.setRfc(txtRfc.getText().trim());
                auto.setPersona(duenio);

                automovilDAO.crear(auto);
            }

            Placa placa = new Placa();
            placa.setAutomovil(auto);
            placa.setFechaEmision(LocalDate.now());
            placa.setNumeroPlaca(generarNumeroPlaca());
            placa.setCosto(Double.valueOf(txtMonto.getText().replace("$", "").trim()));

            if (comboTipoAuto.getSelectedItem().equals("Auto Usado")) {
                // Aquí podrías agregar lógica para invalidar la placa anterior.
            }

            placaDAO.crear(placa);

            JOptionPane.showMessageDialog(this, "Placa registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (HeadlessException | NumberFormatException ex) {
            mostrarError("Error al registrar la placa: " + ex.getMessage());
        }
    }

    private String generarNumeroPlaca() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(letras.charAt((int) (Math.random() * letras.length())));
        }
        sb.append("-").append((int) (Math.random() * 9000 + 1000));
        return sb.toString();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
