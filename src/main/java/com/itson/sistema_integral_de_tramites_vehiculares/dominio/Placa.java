/*
 * Placa.java
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
@Table(name = "placas")
public class Placa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_placa")
    private String numeroPlaca;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_recepcion")
    private LocalDate fechaRecepcion;

    @ManyToOne
    @JoinColumn(name = "automovil_id")
    private Automovil automovil;
}
