package com.adriandondarza.gestionpedidos.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad Artículo.
 * <p>
 * Se utiliza para transportar la información de los productos del catálogo entre 
 * las distintas capas de la aplicación (Controller, Service, Front), evitando 
 * la exposición directa de la entidad JPA y permitiendo una gestión más flexible 
 * de los datos.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDTO {

    /**
     * Identificador único del artículo generado automáticamente por la base de datos.
     */
    private Integer id;

    /**
     * Nombre comercial del artículo.
     */
    private String nombre;

    /**
     * Descripción detallada de las características del producto.
     */
    private String descripcion;

    /**
     * Valor monetario unitario actual del artículo.
     */
    private BigDecimal precioActual;

    /**
     * Cantidad de unidades disponibles en el inventario.
     */
    private Integer stock;

    /**
     * Estado de disponibilidad del artículo. 
     * Representa si el producto está habilitado para la venta (Borrado lógico).
     */
    private Boolean activo;
}