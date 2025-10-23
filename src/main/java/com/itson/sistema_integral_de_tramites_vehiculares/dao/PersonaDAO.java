/*
 * PersonaDAO.java
 *
 */
package com.itson.sistema_integral_de_tramites_vehiculares.dao;

import com.itson.sistema_integral_de_tramites_vehiculares.dominio.Persona;
import java.util.List;

/**
 *
 * @author Miguel Ángel Sánchez Sotelo
 */
public interface PersonaDAO {

    List<Persona> getAll();

    Persona get(Long id);

    Persona update(Persona persona);

    boolean delete(Long id);

}
