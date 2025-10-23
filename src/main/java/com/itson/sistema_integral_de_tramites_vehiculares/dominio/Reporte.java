/*
 * Placa.java
 *
 */
package com.itson.sistema_integral_de_tramites_vehiculares.dominio;

import com.itson.sistema_integral_de_tramites_vehiculares.util.Periodo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Miguel Ángel Sánchez Sotelo
 */
@Entity
@Table(name = "reportes")
public class Reporte implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_generacion")
    private LocalDate fechaGeneracion;

    @Column(name = "periodo_reporte")
    private Periodo periodo;

    @ElementCollection(targetClass = TipoTramite.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "tramite_tipo", joinColumns = @JoinColumn(name = "tramite_id"))
    @Column(name = "tipo")
    private List<TipoTramite> tiposTramites = new ArrayList<>();

}
