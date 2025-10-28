package com.itson.sistema.integral.de.tramites.vehiculares.dao;

import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Persona;
import java.util.List;

public interface PersonaDAO {

    List<Persona> buscarTodos();

    Persona buscarPorId(Long id);

    Persona buscarPorRFC(String rfc);

    Persona crear(Persona persona);

    boolean actualizar(Long id, Persona persona);

    boolean eliminar(Long id);

}
