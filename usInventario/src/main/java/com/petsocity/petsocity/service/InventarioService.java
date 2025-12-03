package com.petsocity.petsocity.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Inventario;
import com.petsocity.petsocity.model.Producto;
import com.petsocity.petsocity.repository.InventarioRepository;
import com.petsocity.petsocity.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public InventarioService(InventarioRepository inventarioRepository,
                             ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }

    // Obtener todos los registros de inventario
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    // Obtener inventario por ID de inventario
    public Inventario obtenerPorId(Integer id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }

    // Buscar inventario por nombre del producto (usa @Query del repository)
    public List<Inventario> buscarPorNombreProducto(String nombre) {
        return inventarioRepository.buscarPorNombreProducto(nombre);
    }

    // Obtener inventario por categoria del producto
    public List<Inventario> obtenerPorCategoriaId(Integer idCategoria) {
        return inventarioRepository.buscarPorCategoria(idCategoria);
    }

    // Obtener inventario por ID de producto
    public Inventario obtenerPorProducto(Integer idProducto) {
        return inventarioRepository.findByProductoId(idProducto)
                .orElseThrow(() -> new RuntimeException(
                        "No existe inventario asociado al producto con ID: " + idProducto));
    }

    // Crear o actualizar inventario para un producto específico
    public Inventario crearOActualizarInventario(Integer idProducto,
                                                 Integer stockActual,
                                                 Integer stockMinimo) {

        // 1) Validar que el producto exista
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));

        // 2) Buscar si ya hay inventario para ese producto
        Inventario inventario = inventarioRepository.findByProductoId(idProducto)
                .orElseGet(() -> {
                    Inventario nuevo = new Inventario();
                    nuevo.setProducto(producto);
                    return nuevo;
                });

        // 3) Actualizar datos de inventario
        inventario.setStockActual(stockActual);
        inventario.setStockMinimo(stockMinimo);
        inventario.setFechaActualizacion(LocalDateTime.now());

        // 4) Guardar
        return inventarioRepository.save(inventario);
    }

    // Eliminar inventario por ID
    public void eliminarInventario(Integer id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("Inventario no encontrado con ID: " + id);
        }
        inventarioRepository.deleteById(id);
    }

}
