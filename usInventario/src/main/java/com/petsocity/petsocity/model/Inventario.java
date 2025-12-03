package com.petsocity.petsocity.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "inventario")
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Integer idInventario;

    // Relación 1 es a 1 con producto
    @OneToOne
    @JoinColumn(name = "id_producto", nullable = false, unique = true)
    private Producto producto;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

}