package com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes;

import com.itson.sistema.integral.de.tramites.vehiculares.ui.AdministradorConsultas;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.AdministradorLicencias;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.AdministradorPlacas;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.AdministradorReportes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EncabezadoComponente extends JPanel {

    public JButton btnLicencias;
    public JButton btnPlacas;
    public JButton btnConsultas;
    public JButton btnReportes;

    public EncabezadoComponente() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(30, 144, 255));
        setPreferredSize(new Dimension(0, 50));

        btnLicencias = crearBoton("Licencias");
        btnPlacas = crearBoton("Placas");
        btnConsultas = crearBoton("Consultas");
        btnReportes = crearBoton("Reportes");

        add(btnLicencias);
        add(btnPlacas);
        add(btnConsultas);
        add(btnReportes);

        btnLicencias.addActionListener(e -> {
            AdministradorLicencias frame = new AdministradorLicencias();
            frame.setVisible(true);
        });

        btnPlacas.addActionListener(e -> {
            AdministradorPlacas frame = new AdministradorPlacas();
            frame.setVisible(true);
        });

        btnConsultas.addActionListener(e -> {
            AdministradorConsultas frame = new AdministradorConsultas();
            frame.setVisible(true);
        });

        btnReportes.addActionListener(e -> {
            AdministradorReportes frame = new AdministradorReportes();
            frame.setVisible(true);
        });
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(240, 248, 255));
        boton.setForeground(Color.BLACK);
        boton.setPreferredSize(new Dimension(120, 30));
        return boton;
    }
}
