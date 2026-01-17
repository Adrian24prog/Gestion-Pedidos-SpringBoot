package com.adriandondarza.gestionpedidos.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos (DTO) para las líneas de detalle de una compra.
 * <p>
 * Esta clase representa cada uno de los elementos individuales que componen un pedido.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.1
 * @since 2026-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineaCompraDTO {

    /**
     * Identificador único del artículo asociado a esta línea de pedido.
     */
    private Integer articuloId;

    /**
     * Nombre descriptivo del artículo.
     */
    private String articuloNombre;

    /**
     * Número de unidades solicitadas.
     */
    private Integer cantidad;

    /**
     * Precio unitario aplicado en el momento de realizar la compra.
     */
    private BigDecimal precioAplicado;

    /**
     * Constructor específico para la creación de pedidos desde el Front-end.
     * <p>
     * Permite instanciar una línea enviando solo el ID del artículo y la cantidad,
     * que es lo único que el cliente conoce antes de que el servidor procese el precio.
     * </p>
     * * @param articuloId ID del producto.
     * @param cantidad Unidades deseadas.
     */
    public LineaCompraDTO(Integer articuloId, Integer cantidad) {
        this.articuloId = articuloId;
        this.cantidad = cantidad;
    }
}