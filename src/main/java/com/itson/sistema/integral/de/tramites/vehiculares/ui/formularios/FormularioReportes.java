package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.TramiteDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.TramiteDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.TipoTramite;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Tramite;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioReportes extends JPanel {

    private final DatePicker fechaInicio;
    private final DatePicker fechaFin;
    private final JTextField txtNombre = new JTextField();
    private final JComboBox<String> comboTipoTramite = new JComboBox<>(new String[]{"Todos", "Licencias", "Placas"});
    public final JButton btnBuscar = new JButton("Generar Reporte");
    private final JButton btnLimpiar = new JButton("Limpiar");

    private final TramiteDAO tramiteDAO = new TramiteDAOImpl();

    public FormularioReportes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Generador de Reportes de Trámites", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));

        DatePickerSettings sInicio = new DatePickerSettings();
        sInicio.setFormatForDatesCommonEra("yyyy-MM-dd");
        fechaInicio = new DatePicker(sInicio);

        DatePickerSettings sFin = new DatePickerSettings();
        sFin.setFormatForDatesCommonEra("yyyy-MM-dd");
        fechaFin = new DatePicker(sFin);

        agregarCampo(panelCampos, "Fecha inicio:", fechaInicio);
        agregarCampo(panelCampos, "Fecha fin:", fechaFin);
        agregarCampo(panelCampos, "Nombre (similar):", txtNombre);
        agregarCampo(panelCampos, "Tipo de trámite:", comboTipoTramite);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> generarReporte());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        panel.add(new JLabel(etiqueta));
        panel.add(campo);
    }

    private void limpiarCampos() {
        fechaInicio.clear();
        fechaFin.clear();
        txtNombre.setText("");
        comboTipoTramite.setSelectedIndex(0);
    }

    private void generarReporte() {
        try {
            LocalDateTime inicio = fechaInicio.getDate() != null ? fechaInicio.getDate().atStartOfDay() : null;
            LocalDateTime fin = fechaFin.getDate() != null ? fechaFin.getDate().atTime(23, 59, 59) : null;
            String nombre = txtNombre.getText().trim().toLowerCase();
            String tipoSeleccionado = (String) comboTipoTramite.getSelectedItem();

            TipoTramite tipo = switch (tipoSeleccionado) {
                case "Licencias" ->
                    TipoTramite.LICENCIA;
                case "Placas" ->
                    TipoTramite.PLACAS;
                default ->
                    null;
            };

            List<Tramite> resultados = tramiteDAO.buscarPorFiltros(null, tipo, inicio, fin);
            if (!nombre.isEmpty()) {
                resultados.removeIf(t -> !t.getPersona().getNombreCompleto().toLowerCase().contains(nombre));
            }

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Reporte", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("Resultados del Reporte:\n\n");
                for (Tramite t : resultados) {
                    sb.append("ID: ").append(t.getId())
                            .append(", Tipo: ").append(t.getTipoTramite())
                            .append(", Persona: ").append(t.getPersona().getNombreCompleto())
                            .append(", Fecha: ").append(t.getFechaTramite().toLocalDate())
                            .append(", Costo: $").append(t.getCosto())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString(), "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (HeadlessException ex) {
            mostrarError("Error al generar el reporte: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
