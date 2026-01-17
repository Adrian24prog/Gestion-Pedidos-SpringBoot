package com.adriandondarza.gestionpedidos.springboot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Objeto de Transferencia de Datos (DTO) para la Información Fiscal.
 * <p>
 * Esta clase se utiliza para capturar y transportar los datos legales y de contacto 
 * necesarios para la facturación. En la lógica de negocio actual, actúa como el 
 * DTO principal para el registro de nuevos sujetos, permitiendo la creación 
 * simultánea de la entidad fiscal y el perfil de cliente asociado.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoFiscalDTO {

    /**
     * Número de Identificación Fiscal o Código de Identificación Fiscal.
     * Se utiliza como clave primaria compartida para identificar al sujeto.
     */
    private String nifCif;

    /**
     * Domicilio legal o dirección de facturación registrada ante las autoridades.
     */
    private String direccionFiscal;

    /**
     * Número telefónico de contacto asociado a la información legal del registro.
     */
    private String telefono;

    /**
     * Nombre comercial o personal del cliente. 
     * Campo opcional utilizado para facilitar el alta del perfil de cliente 
     * de forma atómica durante el registro fiscal.
     */
    private String nombreCliente; 
}