package com.petsocity.petsocity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.service.CategoriaService;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Obtener todas las categorias
    @GetMapping("")
    public List<Categoria> listarCategorias() {
        return categoriaService.obtenerTodas();
    }

    // Obtener categoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable("id") Integer id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva categoria
    @PostMapping("")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        Map<String, Object> response = new HashMap<>();

        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            response.put("error", "El nombre es obligatorio.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Categoria creada = categoriaService.crearCategoria(categoria);
            response.put("mensaje", "Categoría creada correctamente.");
            response.put("categoria", creada);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarCategoria(
            @PathVariable("id") Integer id,
            @RequestBody Categoria categoria) {

        Map<String, Object> response = new HashMap<>();

        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            response.put("error", "El nombre es obligatorio.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoria);
            response.put("mensaje", "Categoría actualizada correctamente.");
            response.put("categoria", categoriaActualizada);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarCategoria(@PathVariable("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            categoriaService.eliminarCategoria(id);
            response.put("mensaje", "Categoría eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
