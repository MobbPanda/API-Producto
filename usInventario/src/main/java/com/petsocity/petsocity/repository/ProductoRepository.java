package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petsocity.petsocity.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Listar todos los productos que esten activos
    @Query("SELECT p FROM Producto p WHERE p.activo = true")
    List<Producto> buscarActivos();

    // Buscar productos por nombre (solo activos)
    @Query("SELECT p FROM Producto p " +
           "WHERE p.activo = true " +
           "AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    // Listar productos por categoría (solo activos)
    @Query("SELECT p FROM Producto p " +
           "JOIN p.categoria c " +
           "WHERE c.idCategoria = :categoriaId " +
           "AND p.activo = true")
    List<Producto> buscarPorCategoria(@Param("categoriaId") Integer categoriaId);

    // Ver si existe un producto con ese nombre (ignora mayúsculas)
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
           "FROM Producto p " +
           "WHERE LOWER(p.nombre) = LOWER(:nombre)")
    boolean existePorNombre(@Param("nombre") String nombre);
    
}
