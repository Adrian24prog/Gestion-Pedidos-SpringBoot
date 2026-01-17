package com.adriandondarza.gestionpedidos.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad Cliente.
 * <p>
 * Esta clase implementa un diseño de "modelo aplanado", combinando atributos 
 * esenciales del perfil del cliente con datos de su información fiscal. 
 * Se utiliza principalmente para listados y vistas de consulta donde se requiere 
 * una visión unificada del sujeto sin necesidad de manejar múltiples objetos.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    /**
     * Número de Identificación Fiscal o Código de Identificación Fiscal.
     * Actúa como el identificador único del cliente en el sistema.
     */
    private String nifCif;

    /**
     * Nombre completo o razón social del cliente.
     */
    private String nombre;

    /**
     * Dirección de correo electrónico única para notificaciones y contacto.
     */
    private String email;

    /**
     * Fecha oficial en la que el cliente fue dado de alta en la plataforma.
     */
    private LocalDate fechaRegistro;
    
    /**
     * Domicilio legal vinculado a la información fiscal del cliente.
     */
    private String direccionFiscal;

    /**
     * Número telefónico de contacto asociado al registro fiscal.
     */
    private String telefono;
}