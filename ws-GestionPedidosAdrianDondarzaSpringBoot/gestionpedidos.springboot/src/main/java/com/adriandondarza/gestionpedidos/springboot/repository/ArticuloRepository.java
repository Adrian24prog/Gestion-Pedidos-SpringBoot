package com.adriandondarza.gestionpedidos.springboot.repository;

import com.adriandondarza.gestionpedidos.springboot.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio encargado de gestionar la persistencia de la entidad {@link Articulo}.
 * <p>
 * Proporciona acceso a la tabla de artículos en Oracle, incluyendo métodos para 
 * validar la existencia de nombres duplicados y filtrar por estado de actividad.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.1
 * @since 2026-01-14
 */
@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {
    
    /**
     * Verifica si ya existe un artículo con un nombre específico, ignorando mayúsculas y minúsculas.
     * <p>
     * Se utiliza para prevenir la duplicidad de productos en el catálogo.
     * </p>
     *
     * @param nombre El nombre del artículo a comprobar.
     * @return {@code true} si el nombre ya existe, {@code false} en caso contrario.
     */
    boolean existsByNombreIgnoreCase(String nombre);
    
    /**
     * Recupera todos los artículos que se encuentran en estado activo.
     *
     * @return {@link List} de {@link Articulo} disponibles para la venta.
     */
    List<Articulo> findByActivoTrue();
    
    /**
     * Realiza una búsqueda de artículos cuyo nombre contenga una cadena específica.
     *
     * @param nombre Cadena de texto a buscar.
     * @return {@link List} de {@link Articulo} que coinciden con el criterio.
     */
    List<Articulo> findByNombreContainingIgnoreCase(String nombre);
}