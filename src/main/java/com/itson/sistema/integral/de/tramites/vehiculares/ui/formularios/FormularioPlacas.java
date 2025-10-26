package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioPlacas extends JPanel {

    public JTextField txtNumSerie, txtMarca, txtLinea, txtColor, txtModelo, txtNumPlacasAnteriores, txtMonto;
    public JComboBox<String> comboTipoTramite;
    public JCheckBox checkAutoNuevo;

    public FormularioPlacas() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Formulario de Placas");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel contenido = new JPanel();
        contenido.setLayout(new GridLayout(0, 1, 10, 10));
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

        String[] tiposTramite = {"Auto Nuevo", "Auto Usado"};
        comboTipoTramite = new JComboBox<>(tiposTramite);

        checkAutoNuevo = new JCheckBox("¿Automóvil nuevo?");
        checkAutoNuevo.setSelected(true);

        comboTipoTramite.addActionListener(e -> actualizarFormulario());
        checkAutoNuevo.addActionListener(e -> actualizarFormulario());

        contenido.add(new CampoFormulario("Número de serie", txtNumSerie));
        contenido.add(new CampoFormulario("Marca", txtMarca));
        contenido.add(new CampoFormulario("Línea", txtLinea));
        contenido.add(new CampoFormulario("Color", txtColor));
        contenido.add(new CampoFormulario("Modelo (año)", txtModelo));
        contenido.add(new CampoFormulario("Tipo de trámite", comboTipoTramite));
        contenido.add(new CampoFormulario("¿Automóvil nuevo?", checkAutoNuevo));
        contenido.add(new CampoFormulario("Placas anteriores", txtNumPlacasAnteriores));
        contenido.add(new CampoFormulario("Monto", txtMonto));

        add(titulo, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);

        actualizarFormulario();
    }

    private void actualizarFormulario() {
        String tipoSeleccionado = (String) comboTipoTramite.getSelectedItem();
        boolean esNuevo = tipoSeleccionado.equals("Auto Nuevo") || checkAutoNuevo.isSelected();

        txtNumPlacasAnteriores.setEnabled(!esNuevo);

        double monto = esNuevo ? 1500 : 1000;
        txtMonto.setText("$" + monto);
    }
}
