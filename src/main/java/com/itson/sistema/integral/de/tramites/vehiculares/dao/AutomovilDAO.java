package com.itson.sistema.integral.de.tramites.vehiculares.dao;

import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Automovil;
import java.util.List;

public interface AutomovilDAO {

    List<Automovil> buscarTodos();

    Automovil buscarPorId(Long id);

    Automovil buscarPorNumeroSerie(String numeroSerie);

    Automovil crear(Automovil automovil);

    boolean actualizar(Automovil automovil);

    boolean eliminar(Long id);

}
