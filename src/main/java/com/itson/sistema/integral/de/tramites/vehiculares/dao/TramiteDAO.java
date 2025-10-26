package com.itson.sistema.integral.de.tramites.vehiculares.dao;

import com.itson.sistema.integral.de.tramites.vehiculares.entidades.Tramite;
import com.itson.sistema.integral.de.tramites.vehiculares.entidades.TipoTramite;
import java.time.LocalDateTime;
import java.util.List;

public interface TramiteDAO {

    Tramite crear(Tramite tramite);

    Tramite actualizar(Tramite tramite);

    boolean eliminar(Long id);

    Tramite buscarPorId(Long id);

    List<Tramite> buscarTodos();

    List<Tramite> buscarPorTipo(TipoTramite tipo);

    List<Tramite> buscarPorPersona(Long personaId);

    List<Tramite> buscarPorFecha(LocalDateTime inicio, LocalDateTime fin);

    List<Tramite> buscarPorFiltros(Long personaId, TipoTramite tipo, LocalDateTime inicio, LocalDateTime fin);
}
