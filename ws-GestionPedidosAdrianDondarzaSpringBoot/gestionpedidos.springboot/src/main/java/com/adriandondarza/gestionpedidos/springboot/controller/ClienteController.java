package com.adriandondarza.gestionpedidos.springboot.controller;

import com.adriandondarza.gestionpedidos.springboot.dto.ClienteDTO;
import com.adriandondarza.gestionpedidos.springboot.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de consulta relacionadas con los clientes.
 * <p>
 * Tras la reestructuración del sistema, este controlador se especializa en la lectura 
 * y listado de información básica de clientes, delegando la gestión fiscal 
 * al controlador correspondiente.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Localiza y devuelve la información de un cliente a partir de su Número de Identificación Fiscal.
     *
     * @param nif El NIF/CIF del cliente a consultar.
     * @return {@link ClienteDTO} con los datos del cliente encontrado.
     * @throws RuntimeException si el cliente no existe en la base de datos.
     */
    public ClienteDTO buscarPorNif(String nif) {
        return clienteService.obtenerPorNif(nif);
    }

    /**
     * Obtiene una lista con todos los clientes registrados en el sistema.
     * <p>
     * Este método es ideal para poblar tablas de visualización en la interfaz de usuario.
     * </p>
     *
     * @return {@link List} de {@link ClienteDTO} con la información de todos los clientes.
     */
    public List<ClienteDTO> listarClientes() {
        return clienteService.obtenerTodos();
    }
}