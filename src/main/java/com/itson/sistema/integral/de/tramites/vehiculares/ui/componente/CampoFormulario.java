package com.itson.sistema.integral.de.tramites.vehiculares.ui.componente;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CampoFormulario extends JPanel {

    public CampoFormulario(String textoLabel, JComponent campo) {
        setLayout(new GridLayout(1, 2, 5, 5));
        JLabel label = new JLabel(textoLabel);
        add(label);
        add(campo);
    }

    public Component getCampo() {
        return getComponent(1);
    }
}
