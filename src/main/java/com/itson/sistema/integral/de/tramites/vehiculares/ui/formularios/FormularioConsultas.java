package com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.LicenciaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PlacaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.CampoFormulario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioConsultas extends JPanel {

    public JTextField txtRFC, txtNombre, txtAnioNacimiento;
    public JComboBox<String> comboTipoConsulta;
    public JButton btnBuscar;

    private final LicenciaDAO licenciaDAO = new LicenciaDAOImpl();
    private final PlacaDAO placaDAO = new PlacaDAOImpl();

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

        comboTipoConsulta = new JComboBox<>(new String[]{"Licencias", "Placas", "Ambos"});

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> realizarConsulta());

        formulario.add(new CampoFormulario("RFC", txtRFC));
        formulario.add(new CampoFormulario("Nombre (similar)", txtNombre));
        formulario.add(new CampoFormulario("Año de nacimiento", txtAnioNacimiento));
        formulario.add(new CampoFormulario("Tipo de consulta", comboTipoConsulta));
        formulario.add(new CampoFormulario("", btnBuscar)); // botón centrado

        add(titulo, BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
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
                        continue; // evita NullPointer
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

            // Consulta de Placas
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
                JOptionPane.showMessageDialog(this, resultados.toString(), "Consulta", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año de nacimiento debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar la consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
