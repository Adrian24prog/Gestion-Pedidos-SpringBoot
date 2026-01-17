package com.adriandondarza.gestionpedidos.springboot.controller;

import com.adriandondarza.gestionpedidos.springboot.dto.InfoFiscalDTO;
import com.adriandondarza.gestionpedidos.springboot.service.InfoFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Controlador principal para la gestión de la identidad fiscal y legal de los usuarios.
 * <p>
 * Debido a la arquitectura de base de datos implementada, este controlador actúa como 
 * la raíz para la creación y eliminación de sujetos, gestionando la relación de 
 * dependencia entre la información fiscal y la entidad Cliente.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Controller
public class InfoFiscalController {

    @Autowired
    private InfoFiscalService infoFiscalService;

    /**
     * Registra la información fiscal en el sistema y, de forma atómica, crea el perfil 
     * de Cliente asociado mediante operaciones en cascada.
     * <p>
     * Este es el punto de entrada recomendado para el alta de nuevos clientes para 
     * garantizar la integridad de la relación 1:1 establecida por la Primary Key compartida.
     * </p>
     *
     * @param infoFiscalDTO Objeto de transferencia de datos con NIF, dirección, teléfono y nombre del cliente.
     * @return {@link InfoFiscalDTO} con los datos confirmados tras la persistencia.
     */
    public InfoFiscalDTO registrarInfoFiscal(InfoFiscalDTO infoFiscalDTO) {
        return infoFiscalService.registrarInfoFiscal(infoFiscalDTO);
    }

    /**
     * Elimina la información fiscal del sistema. 
     * <p>
     * Al ser la entidad dueña de la relación, esta operación eliminará por cascada 
     * al Cliente asociado en la base de datos Oracle.
     * </p>
     *
     * @param nif El Número de Identificación Fiscal del registro a eliminar.
     */
    public void eliminarInfoFiscal(String nif) {
        infoFiscalService.eliminarInfoFiscal(nif);
    }
}