package com.adriandondarza.gestionpedidos.springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a un cliente dentro del sistema de gestión.
 * <p>
 * Esta clase se mapea con la tabla {@code clientes} en Oracle. Utiliza una estrategia 
 * de clave primaria compartida con la entidad {@link InfoFiscal} mediante {@link MapsId}, 
 * asegurando una integridad total en la relación uno a uno.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
public class Cliente {

    /**
     * Número de Identificación Fiscal (NIF) o CIF del cliente.
     * Actúa como Identificador Primario y se sincroniza con la tabla de Información Fiscal.
     */
    @Id
    @Column(name = "nif_cif", length = 20)
    private String nifCif;

    /**
     * Nombre completo, razón social o denominación del cliente.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Dirección de correo electrónico única para el cliente.
     * Dispone de una restricción de unicidad (UNIQUE) a nivel de base de datos.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Fecha oficial en la que el cliente realizó su alta en el sistema.
     */
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    /**
     * Vínculo bidireccional con la información fiscal del cliente.
     * <p>
     * Utiliza {@link MapsId} para indicar que la clave primaria de esta entidad 
     * es derivada de la entidad {@link InfoFiscal}.
     * </p>
     */
    @OneToOne
    @MapsId 
    @JoinColumn(name = "nif_cif")
    private InfoFiscal informacionFiscal;

    /**
     * Listado de órdenes de compra asociadas históricamente a este cliente.
     * La carga se realiza de forma perezosa (LAZY) para optimizar el rendimiento.
     */
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Compra> compras = new ArrayList<>();

    /**
     * Método de ciclo de vida de JPA ejecutado antes de la eliminación de un cliente.
     * <p>
     * Su función es romper el vínculo con las compras existentes (poniendo el campo 
     * cliente a null en la tabla de compras) para evitar errores de integridad 
     * referencial y permitir el borrado del registro del cliente manteniendo 
     * el histórico de ventas de forma anónima.
     * </p>
     */
    @PreRemove
    private void desvincularCompras() {
        if (compras != null) {
            for (Compra compra : compras) {
                compra.setCliente(null);
            }
        }
    }
}