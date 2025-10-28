package com.itson.sistema.integral.de.tramites.vehiculares.dao;

import com.itson.sistema.integral.de.tramites.vehiculares.dominio.Tramite;
import com.itson.sistema.integral.de.tramites.vehiculares.dominio.TipoTramite;
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
