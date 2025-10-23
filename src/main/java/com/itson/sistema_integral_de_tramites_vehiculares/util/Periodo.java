package com.itson.sistema_integral_de_tramites_vehiculares.util;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;

@Embeddable
public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Periodo() {
    }

    public Periodo(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public boolean contiene(LocalDate fecha) {
        return (fecha.equals(fechaInicio) || fecha.isAfter(fechaInicio))
                && (fecha.equals(fechaFin) || fecha.isBefore(fechaFin));
    }

}
