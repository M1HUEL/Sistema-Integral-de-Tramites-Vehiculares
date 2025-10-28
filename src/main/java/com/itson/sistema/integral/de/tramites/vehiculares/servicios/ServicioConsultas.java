package com.itson.sistema.integral.de.tramites.vehiculares.servicios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.PlacaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.LicenciaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PlacaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioConsultas {

    private final LicenciaDAO licenciaDAO = new LicenciaDAOImpl();
    private final PlacaDAO placaDAO = new PlacaDAOImpl();

    public List<Licencia> consultarLicencias(String rfc, String nombre, Integer anio) {
        return licenciaDAO.buscarTodos().stream()
                .filter(l -> l.getPersona() != null)
                .filter(l -> (rfc == null || l.getPersona().getRfc().toLowerCase().contains(rfc.toLowerCase())))
                .filter(l -> (nombre == null || l.getPersona().getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())))
                .filter(l -> (anio == null || l.getPersona().getFechaNacimiento().getYear() == anio))
                .collect(Collectors.toList());
    }

    public List<Placa> consultarPlacas(String rfc, String nombre, Integer anio) {
        return placaDAO.buscarTodos().stream()
                .filter(p -> p.getAutomovil() != null && p.getAutomovil().getPersona() != null)
                .filter(p -> (rfc == null || p.getAutomovil().getPersona().getRfc().toLowerCase().contains(rfc.toLowerCase())))
                .filter(p -> (nombre == null || p.getAutomovil().getPersona().getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())))
                .filter(p -> (anio == null || p.getAutomovil().getPersona().getFechaNacimiento().getYear() == anio))
                .collect(Collectors.toList());
    }
}
