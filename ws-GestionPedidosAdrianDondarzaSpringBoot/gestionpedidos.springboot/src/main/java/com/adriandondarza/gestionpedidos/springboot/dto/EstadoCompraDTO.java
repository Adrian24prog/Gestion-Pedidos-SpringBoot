package com.adriandondarza.gestionpedidos.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos (DTO) para la gestión de estados de compra.
 * <p>
 * Esta clase se utiliza para simplificar la comunicación de los posibles estados 
 * por los que puede pasar una orden (ej. PENDIENTE, ENVIADO, CANCELADO) entre 
 * el servidor y la aplicación cliente, facilitando su visualización en selectores o etiquetas.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCompraDTO {

    /**
     * Nombre descriptivo del estado de la compra.
     * Representa el valor textual que identifica la situación actual del pedido.
     */
    private String nombre;
}