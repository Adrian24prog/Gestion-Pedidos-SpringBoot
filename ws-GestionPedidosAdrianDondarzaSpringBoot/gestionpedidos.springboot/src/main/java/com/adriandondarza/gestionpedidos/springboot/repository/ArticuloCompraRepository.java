package com.adriandondarza.gestionpedidos.springboot.repository;

import com.adriandondarza.gestionpedidos.springboot.model.ArticuloCompra;
import com.adriandondarza.gestionpedidos.springboot.model.ArticuloCompraId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de la persistencia de la entidad {@link ArticuloCompra}.
 * <p>
 * Proporciona los métodos necesarios para gestionar las líneas de detalle de cada compra,
 * permitiendo el almacenamiento de la relación entre artículos y pedidos.
 * Al extender de {@link JpaRepository}, hereda todas las operaciones CRUD estándar.
 * </p>
 * <p>
 * Nota: Este repositorio utiliza {@link ArticuloCompraId} como tipo de clave primaria,
 * permitiendo la búsqueda y manipulación basada en la clave compuesta de la relación.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Repository
public interface ArticuloCompraRepository extends JpaRepository<ArticuloCompra, ArticuloCompraId> {
    // JpaRepository hereda automáticamente métodos como save(), deleteById(), y findById().
}