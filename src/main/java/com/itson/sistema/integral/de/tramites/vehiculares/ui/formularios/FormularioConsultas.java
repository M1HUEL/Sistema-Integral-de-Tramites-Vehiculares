package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioConsultas extends JPanel {

    public JTextField txtRFC, txtNombre, txtAnioNacimiento;
    public JComboBox<String> comboTipoConsulta;
    public JButton btnBuscar;

    public FormularioConsultas() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Consulta de Historial");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel formulario = new JPanel(new GridLayout(2, 3, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        txtRFC = new JTextField();
        txtNombre = new JTextField();
        txtAnioNacimiento = new JTextField();

        String[] tiposConsulta = {"Licencias", "Placas", "Ambos"};
        comboTipoConsulta = new JComboBox<>(tiposConsulta);

        btnBuscar = new JButton("Buscar");

        formulario.add(new CampoFormulario("RFC", txtRFC));
        formulario.add(new CampoFormulario("Nombre (similar)", txtNombre));
        formulario.add(new CampoFormulario("Año de nacimiento", txtAnioNacimiento));
        formulario.add(new CampoFormulario("Tipo de consulta", comboTipoConsulta));
        formulario.add(new CampoFormulario("", btnBuscar)); // botón centrado

        add(titulo, BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
    }
}
