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

public class FormularioLicencias extends JPanel {

    public JTextField txtRFC, txtNombre, txtFecha, txtTelefono, txtMonto;
    public JComboBox<String> comboPaises, comboVigencias;
    public JCheckBox checkDiscapacitado;

    public FormularioLicencias() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Formulario de Licencia");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel contenido = new JPanel();
        contenido.setLayout(new GridLayout(3, 3, 10, 10));
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

        contenido.add(new CampoFormulario("RFC", txtRFC));
        contenido.add(new CampoFormulario("Nombre completo", txtNombre));
        contenido.add(new CampoFormulario("Fecha nacimiento", txtFecha));
        contenido.add(new CampoFormulario("Teléfono", txtTelefono));
        contenido.add(new CampoFormulario("País", comboPaises));
        contenido.add(new CampoFormulario("Vigencia", comboVigencias));
        contenido.add(new CampoFormulario("Monto", txtMonto));
        contenido.add(new CampoFormulario("Discapacidad", checkDiscapacitado));

        add(titulo, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
    }

}
