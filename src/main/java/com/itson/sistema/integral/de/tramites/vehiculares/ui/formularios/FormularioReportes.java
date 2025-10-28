package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.TramiteDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.TramiteDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.TipoTramite;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Tramite;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioReportes extends JPanel {

    public DatePicker fechaInicio, fechaFin;
    public JComboBox<String> comboTipoTramite;
    public JButton btnBuscar;
    public JTextField txtNombre;

    private final TramiteDAO tramiteDAO = new TramiteDAOImpl();

    public FormularioReportes() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Generador de Reportes de Trámites");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel formulario = new JPanel(new GridLayout(2, 3, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        DatePickerSettings inicioSettings = new DatePickerSettings();
        inicioSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        fechaInicio = new DatePicker(inicioSettings);

        DatePickerSettings finSettings = new DatePickerSettings();
        finSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        fechaFin = new DatePicker(finSettings);

        txtNombre = new JTextField();

        comboTipoTramite = new JComboBox<>(new String[]{"Todos", "Licencias", "Placas"});

        btnBuscar = new JButton("Generar Reporte");
        btnBuscar.addActionListener(e -> generarReporte());

        formulario.add(new CampoFormulario("Fecha inicio", fechaInicio));
        formulario.add(new CampoFormulario("Fecha fin", fechaFin));
        formulario.add(new CampoFormulario("Nombre (similar)", txtNombre));
        formulario.add(new CampoFormulario("Tipo de trámite", comboTipoTramite));
        formulario.add(new CampoFormulario("", btnBuscar));

        add(titulo, BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
    }

    private void generarReporte() {
        try {
            LocalDateTime inicio = fechaInicio.getDate() != null ? fechaInicio.getDate().atStartOfDay() : null;
            LocalDateTime fin = fechaFin.getDate() != null ? fechaFin.getDate().atTime(23, 59, 59) : null;
            String nombre = txtNombre.getText().trim();
            String tipoSeleccionado = (String) comboTipoTramite.getSelectedItem();

            TipoTramite tipo = switch (tipoSeleccionado) {
                case "Licencias" ->
                    TipoTramite.LICENCIA;
                case "Placas" ->
                    TipoTramite.PLACAS;
                default ->
                    null;
            };

            List<Tramite> resultados = tramiteDAO.buscarPorFiltros(
                    null,
                    tipo,
                    inicio,
                    fin
            );

            if (!nombre.isEmpty()) {
                resultados.removeIf(t -> !t.getPersona().getNombreCompleto().toLowerCase().contains(nombre.toLowerCase()));
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
                JOptionPane.showMessageDialog(this, sb.toString(), "Reporte", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
