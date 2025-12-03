package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.repository.CategoriaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Obtener todas las categorías
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    // Obtener categoria por ID
    public Optional<Categoria> obtenerPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    // Buscar categorias por nombre (usa el @Query del repository)
    public List<Categoria> buscarPorNombre(String nombre) {
        return categoriaRepository.buscarPorNombre(nombre);
    }

    // Crear categoría nueva (evitando duplicados por nombre)
    public Categoria crearCategoria(Categoria categoria) {

        boolean existe = categoriaRepository.existePorNombre(categoria.getNombre());

        if (existe) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }

        // Si no especificas 'activa', la dejamos true por defecto
        if (categoria.getActiva() == null) {
            categoria.setActiva(true);
        }

        return categoriaRepository.save(categoria);
    }

    // Actualizar una categoria existente
    public Categoria actualizarCategoria(Integer id, Categoria nuevaCategoria) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(nuevaCategoria.getNombre());
                    if (nuevaCategoria.getActiva() != null) {
                        categoria.setActiva(nuevaCategoria.getActiva());
                    }
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    // Eliminar categoría (fisicamente de la BD)
    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

}
