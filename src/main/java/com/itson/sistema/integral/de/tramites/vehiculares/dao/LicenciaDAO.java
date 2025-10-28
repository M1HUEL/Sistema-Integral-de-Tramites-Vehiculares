package com.itson.sistema.integral.de.tramites.vehiculares.dao;

import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Licencia;
import java.util.List;

public interface LicenciaDAO {

    List<Licencia> buscarTodos();

    Licencia buscarPorId(Long id);

    List<Licencia> buscarPorPersonaId(Long personaId);

    Licencia crear(Licencia licencia);

    boolean actualizar(Licencia licencia);

    boolean eliminar(Long id);

}
