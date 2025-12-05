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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.petsocity.petsocity.model.Producto;
import com.petsocity.petsocity.service.ProductoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Obtener todos los productos activos
    @GetMapping("")
    public List<Producto> listarActivos() {
        return productoService.obtenerActivos();
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable("id") Integer id) {
        try {
            Producto producto = productoService.obtenerPorId(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar producto por nombre
    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    // Buscar productos según categoria
    @GetMapping("/categoria/{idCategoria}")
    public List<Producto> buscarPorCategoria(@PathVariable("idCategoria") Integer idCategoria) {
        return productoService.buscarPorCategoria(idCategoria);
    }

    // Crear producto
    @PostMapping("")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Producto creado = productoService.crearProducto(producto);
            response.put("mensaje", "Producto creado correctamente.");
            response.put("producto", creado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable("id") Integer id,
                                                @RequestBody Producto producto) {

        Map<String, Object> response = new HashMap<>();

        try {
            Producto actualizado = productoService.actualizarProducto(id, producto);
            response.put("mensaje", "Producto actualizado correctamente.");
            response.put("producto", actualizado);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Desactivar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> desactivarProducto(@PathVariable("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            productoService.desactivarProducto(id);
            response.put("mensaje", "Producto desactivado correctamente.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
}
