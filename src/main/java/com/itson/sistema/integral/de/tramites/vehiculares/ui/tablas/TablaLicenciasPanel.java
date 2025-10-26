package com.itson.sistema.integral.de.tramites.vehiculares.ui.tablas;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaLicenciasPanel extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    public TablaLicenciasPanel() {
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "País", "Edad", "Correo"};

        Object[][] datos = {
            {1, "Miguel Sánchez", "México", 25, "miguel@example.com"},
            {2, "Laura Pérez", "Chile", 30, "laura@example.com"},
            {3, "Carlos Gómez", "Argentina", 22, "carlos@example.com"},
            {4, "Ana Torres", "España", 27, "ana@example.com"},
            {5, "Lucía Díaz", "Colombia", 29, "lucia@example.com"}
        };

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(200, 230, 255));
        tabla.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void agregarUsuario(Object[] fila) {
        modelo.addRow(fila);
    }

    public void eliminarUsuario(int fila) {
        if (fila >= 0 && fila < modelo.getRowCount()) {
            modelo.removeRow(fila);
        }
    }

    public JTable getTabla() {
        return tabla;
    }

}
