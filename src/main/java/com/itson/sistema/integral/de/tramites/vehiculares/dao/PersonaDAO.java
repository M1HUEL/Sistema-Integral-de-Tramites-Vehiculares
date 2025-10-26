package com.itson.sistema.integral.de.tramites.vehiculares.dao;

import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Persona;
import java.util.List;

public interface PersonaDAO {

    List<Persona> buscarTodos();

    Persona buscarPorId(Long id);

    Persona buscarPorRFC();

    Persona crear(Persona persona);

    boolean actualizar(Long id);

    boolean eliminar(Long id, Persona persona);

}
