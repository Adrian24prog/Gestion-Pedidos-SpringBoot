package com.adriandondarza.gestionpedidos.springboot.service;

import com.adriandondarza.gestionpedidos.springboot.dto.InfoFiscalDTO;
import com.adriandondarza.gestionpedidos.springboot.model.Cliente;
import com.adriandondarza.gestionpedidos.springboot.model.InfoFiscal;
import com.adriandondarza.gestionpedidos.springboot.repository.InfoFiscalRepository;
import com.adriandondarza.gestionpedidos.springboot.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

/**
 * Servicio de gestión de la identidad fiscal y legal de los usuarios del sistema.
 * <p>
 * Este servicio actúa como el punto de entrada principal para el registro de nuevos 
 * clientes, coordinando la creación de la infraestructura fiscal y la generación 
 * automática de perfiles de usuario asociados mediante una relación de clave compartida.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.1
 * @since 2026-01-14
 */
@Service
public class InfoFiscalService {

    @Autowired
    private InfoFiscalRepository infoFiscalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Registra de forma atómica la información fiscal y el perfil de cliente asociado.
     * <p>
     * El proceso garantiza la integridad de los datos mediante:
     * <ul>
     * <li>Validación de formato de NIF (8 números y 1 letra) y Teléfono (9 números).</li>
     * <li>Verificación de existencia del NIF/CIF en la tabla fiscal.</li>
     * <li>Comprobación de disponibilidad del email generado sistemáticamente.</li>
     * </ul>
     * </p>
     *
     * @param dto Objeto de transferencia con los datos fiscales y el nombre del cliente.
     * @return {@link InfoFiscalDTO} con los datos procesados.
     * @throws RuntimeException Si los formatos son inválidos, el NIF ya existe o el email está en uso.
     */
    @Transactional
    public InfoFiscalDTO registrarInfoFiscal(InfoFiscalDTO dto) {
        
        // 1. VALIDACIÓN DE FORMATO (JAVA PURO)
        
        // Comprobar NIF: 8 dígitos y 1 letra (Mayúscula o minúscula)
        if (dto.getNifCif() == null || !dto.getNifCif().matches("^[0-9]{8}[A-Za-z]$")) {
            throw new RuntimeException("Validación: El NIF debe tener 8 números y una letra final (ej. 12345678A).");
        }

        // Comprobar Teléfono: Exactamente 9 dígitos numéricos
        if (dto.getTelefono() == null || !dto.getTelefono().matches("^[0-9]{9}$")) {
            throw new RuntimeException("Validación: El teléfono debe contener exactamente 9 números.");
        }

        // 2. CONTROL DE NIF DUPLICADO EN BASE DE DATOS
        if (infoFiscalRepository.existsById(dto.getNifCif())) {
            throw new RuntimeException("Error: Ya existe un registro con el NIF " + dto.getNifCif());
        }

        // 3. CONTROL DE EMAIL DUPLICADO
        String emailGenerado = dto.getNifCif().toLowerCase() + "@mail.com";
        if (clienteRepository.existsByEmail(emailGenerado)) {
            throw new RuntimeException("Error: El email " + emailGenerado + " ya está registrado en el sistema.");
        }

        // 4. CREACIÓN Y VINCULACIÓN DE ENTIDADES
        InfoFiscal info = new InfoFiscal(dto.getNifCif(), dto.getDireccionFiscal(), dto.getTelefono());
        
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombreCliente());
        cliente.setEmail(emailGenerado);
        cliente.setFechaRegistro(LocalDate.now());

        // Establecimiento del vínculo bidireccional
        info.setCliente(cliente);
        cliente.setInformacionFiscal(info);

        // La persistencia del padre (InfoFiscal) arrastra al hijo (Cliente) por CascadeType.ALL
        infoFiscalRepository.saveAndFlush(info);

        return dto;
    }

    /**
     * Elimina el registro fiscal y, por cascada, el perfil de cliente vinculado.
     * <p>
     * Esta operación es irreversible y debe usarse con precaución, ya que la 
     * integridad referencial de la base de datos eliminará toda la información 
     * legal del sujeto identificado por el NIF proporcionado.
     * </p>
     *
     * @param nif Identificador fiscal único del registro a eliminar.
     * @throws RuntimeException Si el NIF no corresponde a ningún registro existente.
     */
    @Transactional
    public void eliminarInfoFiscal(String nif) {
        if (!infoFiscalRepository.existsById(nif)) {
            throw new RuntimeException("Error: No se puede eliminar. No existe información fiscal con NIF: " + nif);
        }
        infoFiscalRepository.deleteById(nif);
    }
}