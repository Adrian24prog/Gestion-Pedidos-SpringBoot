package com.adriandondarza.gestionpedidos.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos (DTO) para la confirmación y visualización de compras.
 * <p>
 * Esta clase se utiliza para enviar la información de una orden procesada hacia la interfaz 
 * de usuario. A diferencia del DTO de registro, este incluye datos generados por el sistema 
 * como el identificador único, el estado actual y el nombre del cliente asociado.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraRespuestaDTO {

    /**
     * Identificador único de la compra generado por la base de datos.
     */
    private Integer id;

    /**
     * Marca temporal exacta en la que se registró la transacción en el sistema.
     */
    private LocalDateTime fechaRealizada;

    /**
     * Representación textual del estado actual del pedido (ej. PENDIENTE, ENVIADO, ENTREGADO).
     * Se utiliza un String para facilitar la integración con componentes de la interfaz de usuario.
     */
    private String estado;

    /**
     * Ubicación física donde se ha gestionado o se gestionará la entrega de los artículos.
     */
    private String direccionEnvio;

    /**
     * Importe monetario total de la compra, calculado tras procesar todas las líneas de detalle.
     */
    private BigDecimal precioTotal;

    /**
     * Nombre comercial o personal del cliente que realizó el pedido. 
     * Este campo facilita la visualización rápida en listados sin necesidad de consultas adicionales.
     */
    private String clienteNombre;
}