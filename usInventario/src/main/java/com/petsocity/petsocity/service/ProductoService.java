package com.petsocity.petsocity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.model.Producto;
import com.petsocity.petsocity.repository.CategoriaRepository;
import com.petsocity.petsocity.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Obtener todos los productos activos
    public List<Producto> obtenerActivos() {
        return productoRepository.buscarActivos();
    }

    // Obtener producto por ID
    public Producto obtenerPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    // Buscar productos por nombre
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre);
    }

    // Buscar productos por categoria
    public List<Producto> buscarPorCategoria(Integer idCategoria) {

        // Valida que la categoria exista
        categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategoria));

        return productoRepository.buscarPorCategoria(idCategoria);
    }

    // Crear nuevo producto
    public Producto crearProducto(Producto producto) {

        // 1) Validar categoria existente
        Integer idCat = producto.getCategoria().getIdCategoria();

        Categoria categoria = categoriaRepository.findById(idCat)
                .orElseThrow(() -> new RuntimeException("Categoría no existe con ID: " + idCat));

        // 2) Evitar nombres duplicados
        boolean existe = productoRepository.existePorNombre(producto.getNombre());
        if (existe) {
            throw new RuntimeException("Ya existe un producto con el nombre: " + producto.getNombre());
        }

        // 3) Asignar categoria
        producto.setCategoria(categoria);

        // 4) Activar producto por defecto
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }

        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Producto actualizarProducto(Integer id, Producto datos) {

        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Nombre
        if (datos.getNombre() != null) {
            existente.setNombre(datos.getNombre());
        }

        // Descripción
        if (datos.getDescripcion() != null) {
            existente.setDescripcion(datos.getDescripcion());
        }

        // Precio
        if (datos.getPrecio() != null) {
            existente.setPrecio(datos.getPrecio());
        }

        // Estado (activo o inactivo)
        if (datos.getActivo() != null) {
            existente.setActivo(datos.getActivo());
        }

        // Imagen Url
        if (datos.getImagenUrl() != null) {
        existente.setImagenUrl(datos.getImagenUrl());
        }

        // Categoria
        if (datos.getCategoria() != null && datos.getCategoria().getIdCategoria() != null) {

            Integer idCat = datos.getCategoria().getIdCategoria();

            Categoria categoria = categoriaRepository.findById(idCat)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCat));

            existente.setCategoria(categoria);
        }

        return productoRepository.save(existente);
    }

    // Desactivar producto (no borrar)
    public void desactivarProducto(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setActivo(false);

        productoRepository.save(producto);
    }
}
