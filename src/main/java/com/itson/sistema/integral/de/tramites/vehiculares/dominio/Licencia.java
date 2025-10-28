package com.itson.sistema.integral.de.tramites.vehiculares.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "licencias")
public class Licencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_expedicion", nullable = false)
    private LocalDate fechaExpedicion;

    @Column(name = "vigencia_anios", nullable = false)
    private int vigenciaAnios;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "discapacitado", nullable = false)
    private boolean discapacitado;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    public Licencia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public int getVigenciaAnios() {
        return vigenciaAnios;
    }

    public void setVigenciaAnios(int vigenciaAnios) {
        this.vigenciaAnios = vigenciaAnios;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public boolean isDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(boolean discapacitado) {
        this.discapacitado = discapacitado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "Licencia{" + "id=" + id + ", fechaExpedicion=" + fechaExpedicion + ", vigenciaAnios=" + vigenciaAnios + ", monto=" + monto + ", discapacitado=" + discapacitado + ", persona=" + persona + '}';
    }

}
