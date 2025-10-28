package com.itson.sistema.integral.de.tramites.vehiculares.servicios;

import com.itson.sistema.integral.de.tramites.vehiculares.dao.LicenciaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.PersonaDAO;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.LicenciaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.dao.impl.PersonaDAOImpl;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Licencia;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Persona;
import java.time.LocalDate;

public class ServicioLicencias {

    private final LicenciaDAO licenciaDAO = new LicenciaDAOImpl();
    private final PersonaDAO personaDAO = new PersonaDAOImpl();

    public double calcularMonto(String vigencia, boolean discapacitado) {
        double montoBase = switch (vigencia) {
            case "1 Year" ->
                600;
            case "2 Year" ->
                900;
            case "3 Year" ->
                1100;
            default ->
                0;
        };
        return discapacitado ? montoBase * 0.5 : montoBase;
    }

    public Licencia tramitarLicencia(Persona personaData, String vigencia, boolean discapacitado, double monto) {
        Persona persona = personaDAO.buscarPorRFC(personaData.getRfc());
        if (persona == null) {
            persona = personaDAO.crear(personaData);
        } else {
            actualizarPersona(persona, personaData);
            personaDAO.actualizar(persona.getId(), persona);
        }

        Licencia licencia = new Licencia();
        licencia.setPersona(persona);
        licencia.setVigenciaAnios(Integer.parseInt(vigencia.split(" ")[0]));
        licencia.setFechaExpedicion(LocalDate.now());
        licencia.setDiscapacitado(discapacitado);
        licencia.setMonto(monto);

        return licenciaDAO.crear(licencia);
    }

    private void actualizarPersona(Persona existente, Persona nuevosDatos) {
        existente.setNombreCompleto(nuevosDatos.getNombreCompleto());
        existente.setFechaNacimiento(nuevosDatos.getFechaNacimiento());
        existente.setTelefono(nuevosDatos.getTelefono());
        existente.setPais(nuevosDatos.getPais());
    }
}
