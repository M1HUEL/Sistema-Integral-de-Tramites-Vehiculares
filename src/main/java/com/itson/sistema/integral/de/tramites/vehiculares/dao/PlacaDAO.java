package com.itson.sistema.integral.de.tramites.vehiculares.dao;

import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Placa;
import java.util.List;

public interface PlacaDAO {

    List<Placa> buscarTodos();

    Placa buscarPorId(Long id);

    Placa buscarPorNumeroPlaca(String numeroPlaca);

    List<Placa> buscarPorAutomovilId(Long automovilId);

    Placa crear(Placa placa);

    boolean actualizar(Placa placa);

    boolean eliminar(Long id);

}
