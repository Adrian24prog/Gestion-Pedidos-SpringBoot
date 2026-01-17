package com.adriandondarza.gestionpedidos.springboot.model;

/**
 * Enumeración que define los posibles estados de una orden de compra en el sistema.
 * <p>
 * Estos estados controlan el flujo lógico del pedido desde su creación hasta su 
 * finalización o cancelación, permitiendo realizar el seguimiento logístico.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
public enum EstadoCompra {

    /**
     * El pedido ha sido registrado en el sistema pero aún no ha sido procesado 
     * para su envío. Es el estado inicial por defecto.
     */
    PENDIENTE,

    /**
     * El pedido ha salido del almacén y se encuentra en tránsito hacia la 
     * dirección de entrega proporcionada por el cliente.
     */
    ENVIADO,

    /**
     * El pedido ha sido recibido satisfactoriamente por el cliente. 
     * Representa el estado final de una transacción exitosa.
     */
    ENTREGADO,

    /**
     * El pedido ha sido cancelado, ya sea por solicitud del cliente o por 
     * problemas en el procesamiento (ej. falta de pago o stock).
     */
    ANULADA
}