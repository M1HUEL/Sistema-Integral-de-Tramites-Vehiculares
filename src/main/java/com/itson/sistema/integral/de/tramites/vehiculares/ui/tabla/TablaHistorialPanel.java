package com.itson.sistema.integral.de.tramites.vehiculares.ui.tabla;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaHistorialPanel extends JPanel {

    public JTable tabla;
    private DefaultTableModel modelo;

    public TablaHistorialPanel() {
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Tipo de Trámite", "Nombre", "Costo"}
        );

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);
    }

    public void agregarFila(String fecha, String tipo, String nombre, double costo) {
        modelo.addRow(new Object[]{fecha, tipo, nombre, "$" + costo});
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }
}
