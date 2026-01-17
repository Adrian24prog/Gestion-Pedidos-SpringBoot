package com.adriandondarza.gestionpedidos.springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad que representa la información legal y de contacto fiscal de un sujeto.
 * <p>
 * En la arquitectura actual, esta clase funciona como la entidad principal (padre) 
 * de la relación uno a uno con {@link Cliente}. La clave primaria {@code nifCif} 
 * es compartida entre ambas tablas para garantizar una integridad referencial absoluta.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Entity
@Table(name = "informacion_fiscal")
@Data
@NoArgsConstructor
public class InfoFiscal {

    /**
     * Identificador único basado en el Número de Identificación Fiscal.
     * Es la clave primaria que se propaga a la entidad Cliente mediante MapsId.
     */
    @Id
    @Column(name = "nif_cif", length = 20)
    private String nifCif;

    /**
     * Domicilio legal registrado para fines de facturación y notificaciones oficiales.
     */
    @Column(name = "direccion_fiscal", nullable = false)
    private String direccionFiscal;

    /**
     * Número telefónico de contacto asociado a la identidad fiscal.
     */
    @Column(nullable = false)
    private String telefono;

    /**
     * Relación bidireccional uno a uno con la entidad {@link Cliente}.
     * <p>
     * Se define con {@code cascade = CascadeType.ALL} para permitir que al persistir 
     * o eliminar la información fiscal, el perfil de cliente asociado se gestione 
     * de forma automática. {@code orphanRemoval = true} garantiza que no existan 
     * clientes sin su correspondiente respaldo fiscal.
     * </p>
     */
    @OneToOne(mappedBy = "informacionFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Cliente cliente;

    /**
     * Constructor especializado para la creación rápida de registros fiscales.
     * * @param nifCif Identificador fiscal único.
     * @param direccionFiscal Domicilio legal.
     * @param telefono Teléfono de contacto.
     */
    public InfoFiscal(String nifCif, String direccionFiscal, String telefono) {
        this.nifCif = nifCif;
        this.direccionFiscal = direccionFiscal;
        this.telefono = telefono;
    }
}