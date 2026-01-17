package com.adriandondarza.gestionpedidos.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Entidad de asociación que representa el detalle de cada artículo dentro de una compra.
 * <p>
 * Esta clase mapea la tabla {@code articulo_compra} y gestiona la relación de muchos a muchos 
 * entre {@link Articulo} y {@link Compra}. Implementa una clave primaria compuesta y 
 * almacena información histórica de la transacción para evitar que cambios posteriores 
 * en el catálogo alteren los registros de ventas pasadas.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Entity
@Table(name = "articulo_compra")
@Data
@NoArgsConstructor
public class ArticuloCompra {

    /**
     * Identificador compuesto que combina las claves primarias de Articulo y Compra.
     */
    @EmbeddedId
    private ArticuloCompraId id = new ArticuloCompraId();

    /**
     * Referencia al artículo incluido en esta línea de pedido.
     * <p>
     * Utiliza {@link MapsId} para vincular la entidad con el atributo correspondiente 
     * en la clave compuesta {@link ArticuloCompraId}.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articuloId")
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;

    /**
     * Referencia a la compra a la que pertenece esta línea de detalle.
     * <p>
     * Utiliza {@link MapsId} para vincular la entidad con el atributo correspondiente 
     * en la clave compuesta {@link ArticuloCompraId}.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compraId")
    @JoinColumn(name = "id_compra")
    private Compra compra;

    /**
     * Precio unitario del artículo en el momento exacto de la compra.
     * <p>
     * Este valor es crítico para la integridad contable, ya que "congela" el precio 
     * pactado, independientemente de si el {@link Articulo#getPrecioActual()} cambia en el futuro.
     * </p>
     */
    @Column(name = "precio_congelado", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioCompra;

    /**
     * Número de unidades adquiridas del artículo en esta transacción específica.
     */
    @Column(nullable = false)
    private Integer unidades;
}