package com.itson.sistema.integral.de.tramites.vehiculares.ui.tablas;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.LicenciaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaLicenciasPanel extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;
    private final LicenciaDAO licenciaDAO;

    private static final String[] COLUMNAS = {
        "ID Licencia", "Persona (RFC)", "Vigencia (Años)",
        "Fecha Expedición", "Costo", "¿Discapacitado?"
    };

    public TablaLicenciasPanel() {
        setLayout(new BorderLayout());
        this.licenciaDAO = new LicenciaDAOImpl();

        modelo = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);

        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(200, 230, 255));

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        cargarDatosLicencias();
    }

    public final void cargarDatosLicencias() {
        modelo.setRowCount(0);

        try {
            List<Licencia> licencias = licenciaDAO.buscarTodos();

            for (Licencia licencia : licencias) {
                Object[] fila = new Object[COLUMNAS.length];

                fila[0] = licencia.getId();
                fila[1] = licencia.getPersona() != null ? licencia.getPersona().getRfc() : "N/A";
                fila[2] = licencia.getVigenciaAnios();
                fila[3] = licencia.getFechaExpedicion();
                fila[4] = String.format("$%.2f", licencia.getMonto());
                fila[5] = licencia.isDiscapacitado() ? "Sí" : "No";

                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las licencias: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void agregarLicencia(Licencia licencia) {
        Object[] fila = new Object[COLUMNAS.length];

        fila[0] = licencia.getId();
        fila[1] = licencia.getPersona() != null ? licencia.getPersona().getRfc() : "N/A";
        fila[2] = licencia.getVigenciaAnios();
        fila[3] = licencia.getFechaExpedicion();
        fila[4] = String.format("$%.2f", licencia.getMonto());
        fila[5] = licencia.isDiscapacitado() ? "Sí" : "No";

        modelo.addRow(fila);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JTable getTabla() {
        return tabla;
    }
}
