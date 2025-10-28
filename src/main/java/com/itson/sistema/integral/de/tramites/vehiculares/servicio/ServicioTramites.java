package com.itson.sistema.integral.de.tramites.vehiculares.servicio;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.PersonaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.LicenciaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PersonaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Persona;

public class ServicioTramites {

    private final PersonaDAO personaDAO = new PersonaDAOImpl();
    private final LicenciaDAO licenciaDAO = new LicenciaDAOImpl();

    public Licencia tramitarLicencia(Persona personaData, Licencia licencia) throws Exception {
        Persona existente = personaDAO.buscarPorRFC(personaData.getRfc());

        if (existente != null) {
            existente.setNombreCompleto(personaData.getNombreCompleto());
            existente.setFechaNacimiento(personaData.getFechaNacimiento());
            existente.setTelefono(personaData.getTelefono());
            existente.setPais(personaData.getPais());

            licencia.setPersona(existente);
        } else {
            licencia.setPersona(personaDAO.crear(personaData));
        }

        return licenciaDAO.crear(licencia);
    }
}
