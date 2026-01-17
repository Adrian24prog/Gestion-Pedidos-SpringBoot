package com.adriandondarza.gestionpedidos.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) para el registro de nuevas compras.
 * <p>
 * Esta clase se utiliza para encapsular la información necesaria enviada desde la 
 * interfaz de usuario al realizar un pedido, incluyendo la identificación del cliente, 
 * los datos logísticos de entrega y el desglose de los artículos solicitados.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraRegistroDTO {

    /**
     * Número de Identificación Fiscal del cliente que realiza la compra.
     * Se utiliza para vincular la orden con un registro existente en la base de datos.
     */
    private String clienteNif;

    /**
     * Dirección física completa donde se debe realizar la entrega del pedido.
     */
    private String direccionEnvio;

    /**
     * Listado detallado de los artículos y cantidades que componen la compra.
     * Cada elemento de la lista representa una línea de detalle en el pedido final.
     */
    private List<LineaCompraDTO> lineas; 
}