package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petsocity.petsocity.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    // Buscar categoria por nombre
    @Query("SELECT c FROM Categoria c " +
           "WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Categoria> buscarPorNombre(@Param("nombre") String nombre);

    // Ver si existe una categoría con el nombre exacto (true / false)
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
           "FROM Categoria c " +
           "WHERE LOWER(c.nombre) = LOWER(:nombre)")
    boolean existePorNombre(@Param("nombre") String nombre);
    
}
