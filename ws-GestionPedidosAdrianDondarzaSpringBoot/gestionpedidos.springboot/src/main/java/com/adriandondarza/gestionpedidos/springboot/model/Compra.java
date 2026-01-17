package com.adriandondarza.gestionpedidos.springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una orden de compra o pedido en el sistema.
 * <p>
 * Esta clase mapea la tabla {@code compras} y actúa como la cabecera del pedido, 
 * centralizando la información logística, el estado de la transacción y la 
 * relación con el cliente y los artículos adquiridos.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Entity
@Table(name = "compras")
@Data
@NoArgsConstructor
public class Compra {

    /**
     * Identificador único de la compra.
     * Generado automáticamente por la base de datos mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Fecha y hora exacta en la que se registró la compra.
     */
    @Column(name = "fecha_realizada", nullable = false)
    private LocalDateTime fechaRealizada;

    /**
     * Estado logístico y administrativo de la compra.
     * Almacenado en la base de datos como una cadena de texto (String) basada en el Enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoCompra estado;

    /**
     * Dirección física de destino para el envío de los productos.
     */
    @Column(name = "direccion_envio", length = 255)
    private String direccionEnvio;

    /**
     * Importe total acumulado de la compra.
     * Calculado como la suma de los precios de todas las líneas de artículos asociadas.
     */
    @Column(name = "precio_total", precision = 10, scale = 2)
    private BigDecimal precioTotal;

    /**
     * Referencia al cliente que ha realizado el pedido.
     * La carga se realiza de forma diferida (LAZY) para mejorar el rendimiento de las consultas.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_nif")
    private Cliente cliente;

    /**
     * Listado detallado de los artículos que componen el pedido.
     * <p>
     * Utiliza {@code CascadeType.ALL} para que cualquier operación sobre la compra (persistir, eliminar) 
     * se replique automáticamente en sus líneas de detalle. {@code orphanRemoval} asegura 
     * que si una línea se elimina de la lista, también se borre de la base de datos.
     * </p>
     */
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticuloCompra> lineasArticulos = new ArrayList<>();
}