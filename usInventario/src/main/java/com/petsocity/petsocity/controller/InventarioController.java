package com.petsocity.petsocity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.petsocity.petsocity.model.Inventario;
import com.petsocity.petsocity.service.InventarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService){
        this.inventarioService = inventarioService;
    }

    // Obtener todos los inventarios
    @GetMapping("")
    public List<Inventario> obtenerTodos() {
        return inventarioService.obtenerTodos();
    }

    // Obtener inventario por ID de inventario
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable("id") Integer id) {
        try {
            Inventario inv = inventarioService.obtenerPorId(id);
            return ResponseEntity.ok(inv);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener inventario por ID de producto
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Inventario> obtenerPorProducto(@PathVariable("idProducto") Integer idProducto) {
        try {
            Inventario inventario = inventarioService.obtenerPorProducto(idProducto);
            return ResponseEntity.ok(inventario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener inventarios por categoria del producto
    @GetMapping("/categoria/{idCategoria}")
    public List<Inventario> obtenerPorCategoria(@PathVariable("idCategoria") Integer idCategoria) {
        return inventarioService.obtenerPorCategoriaId(idCategoria);
    }

    // Crear o actualizar inventario para un producto
    @PutMapping("/producto/{idProducto}")
    public ResponseEntity<Inventario> crearOActualizar(
            @PathVariable("idProducto") Integer idProducto,
            @RequestBody Map<String, Integer> datos) {

        Integer stockActual = datos.get("stockActual");
        Integer stockMinimo = datos.get("stockMinimo");

        Inventario inv = inventarioService.crearOActualizarInventario(idProducto, stockActual, stockMinimo);

        return ResponseEntity.ok(inv);
    }

    // Eliminar inventario
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarInventario(@PathVariable("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            inventarioService.eliminarInventario(id);
            response.put("mensaje", "Inventario eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
