/*
 * Automovil.java
 *
 */
package com.itson.sistema_integral_de_tramites_vehiculares.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
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

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "marca_vehiculo")
    private String marca;

    @Column(name = "linea_vehiculo")
    private String linea;

    @Column(name = "color_vehiculo")
    private String color;

    @Column(name = "modelo_vehiculo")
    private Long modelo;

    @OneToMany(mappedBy = "automovil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Placa> placas = new ArrayList<>();

}
