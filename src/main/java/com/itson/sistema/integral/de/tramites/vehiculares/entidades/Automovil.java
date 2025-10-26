package com.itson.sistema.integral.de.tramites.vehiculares.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ...
 *
 *
 * @author Miguel Ángel Sánchez Sotelo
 */
@Entity
@Table(name = "automoviles")
public class Automovil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "linea")
    private String linea;

    @Column(name = "color")
    private String color;

    @Column(name = "modelo")
    private Integer modelo;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

}
