package com.adriandondarza.gestionpedidos.springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Entidad que representa un artículo o producto dentro del sistema.
 * <p>
 * Esta clase está mapeada a la tabla {@code articulos} en la base de datos. 
 * Contiene la información maestra de los productos, incluyendo su disponibilidad 
 * de inventario y estado de comercialización.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Entity
@Table(name = "articulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    /**
     * Identificador único del artículo.
     * Mapeado como una columna de identidad (auto-incremental) en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre descriptivo del producto.
     * Limitado a 100 caracteres y de carácter obligatorio.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Descripción detallada del artículo.
     * Mapeado como un objeto {@code CLOB} para permitir textos extensos.
     */
    @Column(nullable = false, columnDefinition = "CLOB")
    private String descripcion;

    /**
     * Precio unitario vigente del artículo.
     * Definido con una precisión de 10 dígitos y 2 decimales.
     */
    @Column(name = "precio_actual", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioActual;

    /**
     * Cantidad de existencias disponibles en el almacén.
     */
    @Column(nullable = false)
    private Integer stock;

    /**
     * Flag de estado del artículo.
     * Indica si el artículo está habilitado para la venta. Por defecto es {@code true}.
     * Se utiliza para el borrado lógico.
     */
    @Column(nullable = false)
    private Boolean activo = true;
}