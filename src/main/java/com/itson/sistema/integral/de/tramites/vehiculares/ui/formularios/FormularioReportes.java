package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;

public class FormularioReportes extends JPanel {

    public DatePicker fechaInicio, fechaFin;
    public JComboBox<String> comboTipoTramite;
    public JButton btnBuscar;
    public javax.swing.JTextField txtNombre;

    public FormularioReportes() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Generador de Reportes de Trámites");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel formulario = new JPanel(new GridLayout(2, 3, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        DatePickerSettings fechaInicioSettings = new DatePickerSettings();
        fechaInicioSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        fechaInicio = new DatePicker(fechaInicioSettings);

        DatePickerSettings fechaFinSettings = new DatePickerSettings();
        fechaFinSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        fechaFin = new DatePicker(fechaFinSettings);

        txtNombre = new javax.swing.JTextField();

        String[] tipos = {"Todos", "Licencias", "Placas"};
        comboTipoTramite = new JComboBox<>(tipos);

        btnBuscar = new JButton("Generar Reporte");

        formulario.add(new CampoFormulario("Fecha inicio", fechaInicio));
        formulario.add(new CampoFormulario("Fecha fin", fechaFin));
        formulario.add(new CampoFormulario("Nombre (similar)", txtNombre));
        formulario.add(new CampoFormulario("Tipo de trámite", comboTipoTramite));
        formulario.add(new CampoFormulario("", btnBuscar));

        add(titulo, BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
    }
}
