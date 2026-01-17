package com.adriandondarza.gestionpedidos.springboot.service;

import com.adriandondarza.gestionpedidos.springboot.dto.ArticuloDTO;
import com.adriandondarza.gestionpedidos.springboot.model.Articulo;
import com.adriandondarza.gestionpedidos.springboot.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la gestión de la lógica de negocio para los artículos.
 * <p>
 * Centraliza las operaciones de catálogo, incluyendo la validación de nombres 
 * únicos para evitar redundancias en el inventario.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.1
 * @since 2026-01-14
 */
@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Transactional(readOnly = true)
    public List<ArticuloDTO> obtenerTodos() {
        return articuloRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ArticuloDTO> obtenerActivos() {
        return articuloRepository.findByActivoTrue().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticuloDTO obtenerPorId(Integer id) {
        Articulo art = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        return convertirADTO(art);
    }

    /**
     * Persiste un nuevo artículo tras verificar que su nombre no esté duplicado.
     * <p>
     * Si el artículo es nuevo (ID nulo), se comprueba la existencia del nombre en 
     * la base de datos de forma insensible a mayúsculas.
     * </p>
     *
     * @param dto Datos del artículo a guardar.
     * @return {@link ArticuloDTO} con el artículo persistido.
     * @throws RuntimeException Si el nombre del artículo ya existe en el sistema.
     */
    @Transactional
    public ArticuloDTO guardarArticulo(ArticuloDTO dto) {
        // Validación de duplicados por nombre (solo para registros nuevos)
        if (dto.getId() == null && articuloRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("Error: El artículo '" + dto.getNombre() + "' ya existe en el catálogo.");
        }

        Articulo art = new Articulo();
        if (dto.getId() != null) art.setId(dto.getId());
        
        art.setNombre(dto.getNombre());
        art.setDescripcion(dto.getDescripcion());
        art.setPrecioActual(dto.getPrecioActual());
        art.setStock(dto.getStock());
        art.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        
        return convertirADTO(articuloRepository.save(art));
    }

    @Transactional
    public void desactivarArticulo(Integer id) {
        Articulo art = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede desactivar: Artículo inexistente"));
        art.setActivo(false);
        articuloRepository.save(art);
    }

    private ArticuloDTO convertirADTO(Articulo art) {
        return new ArticuloDTO(art.getId(), art.getNombre(), art.getDescripcion(), 
                               art.getPrecioActual(), art.getStock(), art.getActivo());
    }
}