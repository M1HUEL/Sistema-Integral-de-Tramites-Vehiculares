/*
 * Licencia.java
 *
 */
package com.itson.sistema_integral_de_tramites_vehiculares.dominio;

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

/**
 *
 * @author Miguel Ángel Sánchez Sotelo
 */
@Entity
@Table(name = "licencias")
public class Licencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_expedicion")
    private LocalDate fechaExpedicion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "costo")
    private Double costo;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    public Licencia() {
    }

}
