package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.LicenciaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PlacaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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

public class FormularioConsultas extends JPanel {

    private final JTextField txtRFC = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtAnioNacimiento = new JTextField();
    private final JComboBox<String> comboTipoConsulta = new JComboBox<>(new String[]{"Licencias", "Placas", "Ambos"});
    public final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    private final LicenciaDAO licenciaDAO = new LicenciaDAOImpl();
    private final PlacaDAO placaDAO = new PlacaDAOImpl();

    public FormularioConsultas() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Consulta de Historial", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
        agregarCampo(panelCampos, "RFC:", txtRFC);
        agregarCampo(panelCampos, "Nombre (similar):", txtNombre);
        agregarCampo(panelCampos, "Año de nacimiento:", txtAnioNacimiento);
        agregarCampo(panelCampos, "Tipo de consulta:", comboTipoConsulta);
        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> realizarConsulta());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        panel.add(new JLabel(etiqueta));
        panel.add(campo);
    }

    private void limpiarCampos() {
        txtRFC.setText("");
        txtNombre.setText("");
        txtAnioNacimiento.setText("");
        comboTipoConsulta.setSelectedIndex(0);
    }

    private void realizarConsulta() {
        try {
            String rfc = txtRFC.getText().trim().toLowerCase();
            String nombre = txtNombre.getText().trim().toLowerCase();
            String anioStr = txtAnioNacimiento.getText().trim();
            String tipoConsulta = (String) comboTipoConsulta.getSelectedItem();

            Integer anioNacimiento = null;
            if (!anioStr.isEmpty()) {
                anioNacimiento = Integer.valueOf(anioStr);
            }

            StringBuilder resultados = new StringBuilder();

            if ("Licencias".equalsIgnoreCase(tipoConsulta) || "Ambos".equalsIgnoreCase(tipoConsulta)) {
                List<Licencia> licencias = licenciaDAO.buscarTodos();
                for (Licencia l : licencias) {
                    if (l.getPersona() == null) {
                        continue;
                    }
                    boolean coincide = (rfc.isEmpty() || l.getPersona().getRfc().toLowerCase().contains(rfc))
                            && (nombre.isEmpty() || l.getPersona().getNombreCompleto().toLowerCase().contains(nombre))
                            && (anioNacimiento == null || l.getPersona().getFechaNacimiento().getYear() == anioNacimiento);
                    if (coincide) {
                        resultados.append("Licencia ID: ").append(l.getId())
                                .append(", Persona: ").append(l.getPersona().getNombreCompleto())
                                .append(", RFC: ").append(l.getPersona().getRfc())
                                .append(", Fecha Exp: ").append(l.getFechaExpedicion())
                                .append(", Vigencia: ").append(l.getVigenciaAnios()).append(" años")
                                .append(", Monto: $").append(l.getMonto())
                                .append("\n");
                    }
                }
            }

            if ("Placas".equalsIgnoreCase(tipoConsulta) || "Ambos".equalsIgnoreCase(tipoConsulta)) {
                List<Placa> placas = placaDAO.buscarTodos();
                for (Placa p : placas) {
                    if (p.getAutomovil() == null || p.getAutomovil().getPersona() == null) {
                        continue;
                    }
                    boolean coincide = (rfc.isEmpty() || p.getAutomovil().getPersona().getRfc().toLowerCase().contains(rfc))
                            && (nombre.isEmpty() || p.getAutomovil().getPersona().getNombreCompleto().toLowerCase().contains(nombre))
                            && (anioNacimiento == null || p.getAutomovil().getPersona().getFechaNacimiento().getYear() == anioNacimiento);
                    if (coincide) {
                        resultados.append("Placa ID: ").append(p.getId())
                                .append(", Persona: ").append(p.getAutomovil().getPersona().getNombreCompleto())
                                .append(", RFC: ").append(p.getAutomovil().getPersona().getRfc())
                                .append(", Número Placa: ").append(p.getNumeroPlaca())
                                .append(", Costo: $").append(p.getCosto())
                                .append("\n");
                    }
                }
            }

            if (resultados.length() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Consulta", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, resultados.toString(), "Resultados de Consulta", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            mostrarError("El año de nacimiento debe ser un número válido.");
        } catch (HeadlessException ex) {
            mostrarError("Error al realizar la consulta: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
