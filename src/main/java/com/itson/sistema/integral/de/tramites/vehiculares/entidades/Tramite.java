package com.itson.sistema.integral.de.tramites.vehiculares.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tramites")
public class Tramite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_tramite", nullable = false)
    private LocalDateTime fechaTramite;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tramite", nullable = false)
    private TipoTramite tipoTramite;

    @Column(name = "costo", nullable = false)
    private Double costo;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    public Tramite() {
    }

    public Tramite(Long id, LocalDateTime fechaTramite, TipoTramite tipoTramite, Double costo, Persona persona) {
        this.id = id;
        this.fechaTramite = fechaTramite;
        this.tipoTramite = tipoTramite;
        this.costo = costo;
        this.persona = persona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaTramite() {
        return fechaTramite;
    }

    public void setFechaTramite(LocalDateTime fechaTramite) {
        this.fechaTramite = fechaTramite;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
