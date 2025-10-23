/*
 * LicenciaDAO.java
 *
 */
package com.itson.sistema_integral_de_tramites_vehiculares.dao;

import com.itson.sistema_integral_de_tramites_vehiculares.dominio.Licencia;
import java.util.List;

/**
 *
 * @author Miguel Ángel Sánchez Sotelo
 */
public interface LicenciaDAO {

    List<Licencia> getAll();

    Licencia get(Long id);

    Licencia update(Licencia licencia);

    boolean delete(Long id);

}
