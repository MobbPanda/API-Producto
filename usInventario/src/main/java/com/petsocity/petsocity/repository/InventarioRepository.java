package com.petsocity.petsocity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petsocity.petsocity.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer>{

    // Buscar inventario según el nombre del producto
    @Query("SELECT i FROM Inventario i " +
           "JOIN i.producto p " +
           "WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Inventario> buscarPorNombreProducto(@Param("nombre") String nombre);

    // Buscar inventario por categoría del producto
    @Query("SELECT i FROM Inventario i " +
           "JOIN i.producto p " +
           "JOIN p.categoria c " +
           "WHERE c.idCategoria = :categoriaId")
    List<Inventario> buscarPorCategoria(@Param("categoriaId") Integer categoriaId);

    // Obtener inventario por ID del producto (1:1)
    @Query("SELECT i FROM Inventario i WHERE i.producto.idProducto = :idProducto")
    Optional<Inventario> findByProductoId(@Param("idProducto") Integer idProducto);
}
