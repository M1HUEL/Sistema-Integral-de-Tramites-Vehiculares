/*
 * PersonaDAO.java
 *
 */
package com.itson.sistema_integral_de_tramites_vehiculares.dao;

import com.itson.sistema_integral_de_tramites_vehiculares.dominio.Persona;
import java.util.List;

public interface PersonaDAO {

    List<Persona> getAll();

}
