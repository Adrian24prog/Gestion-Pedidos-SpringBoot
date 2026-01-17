package com.adriandondarza.gestionpedidos.springboot.service;

import com.adriandondarza.gestionpedidos.springboot.dto.ClienteDTO;
import com.adriandondarza.gestionpedidos.springboot.model.Cliente;
import com.adriandondarza.gestionpedidos.springboot.model.InfoFiscal;
import com.adriandondarza.gestionpedidos.springboot.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio relativa a la gestión de clientes.
 * <p>
 * Centraliza las operaciones de alta, consulta y borrado de clientes, integrando 
 * validaciones de integridad (NIF y Email) y coordinando la persistencia de los 
 * datos personales junto con su información fiscal asociada.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Registra un nuevo cliente en el sistema tras validar sus credenciales únicas.
     * <p>
     * El proceso incluye la creación de la entidad {@link Cliente} y su correspondiente 
     * {@link InfoFiscal}, estableciendo el vínculo bidireccional necesario para 
     * la persistencia en cascada mediante Primary Key compartida.
     * </p>
     *
     * @param dto Datos del cliente y su información fiscal "aplanados" en un DTO.
     * @return {@link ClienteDTO} con los datos finalmente persistidos.
     * @throws RuntimeException Si el NIF o el Email ya se encuentran registrados.
     */
    @Transactional
    public ClienteDTO crearCliente(ClienteDTO dto) {
        // 1. VALIDACIÓN DE NIF
        if (clienteRepository.existsById(dto.getNifCif())) {
            throw new RuntimeException("Validación: El cliente con NIF " + dto.getNifCif() + " ya existe.");
        }

        // 2. VALIDACIÓN DE EMAIL
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Validación: El correo " + dto.getEmail() + " ya pertenece a otro cliente.");
        }

        Cliente cliente = new Cliente();
        cliente.setNifCif(dto.getNifCif());
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setFechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : LocalDate.now());

        InfoFiscal fiscal = new InfoFiscal();
        fiscal.setNifCif(dto.getNifCif()); 
        fiscal.setDireccionFiscal(dto.getDireccionFiscal());
        fiscal.setTelefono(dto.getTelefono());
        
        // Establecer vinculación bidireccional para el motor JPA
        fiscal.setCliente(cliente);
        cliente.setInformacionFiscal(fiscal);

        Cliente guardado = clienteRepository.save(cliente);
        return convertirADTO(guardado);
    }

    /**
     * Recupera el listado completo de clientes registrados.
     *
     * @return {@link List} de {@link ClienteDTO}.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un cliente por su identificador fiscal.
     *
     * @param nif El NIF/CIF del cliente.
     * @return {@link ClienteDTO} con la información del cliente.
     * @throws RuntimeException Si el cliente no existe.
     */
    @Transactional(readOnly = true)
    public ClienteDTO obtenerPorNif(String nif) {
        Cliente c = clienteRepository.findById(nif)
                .orElseThrow(() -> new RuntimeException("Consulta: Cliente no encontrado con NIF: " + nif));
        return convertirADTO(c);
    }

    /**
     * Elimina de forma física el registro del cliente en la base de datos.
     * <p>
     * Debido a la configuración de las entidades, esta operación desencadena 
     * la eliminación de la información fiscal asociada y la desvinculación 
     * (nullify) de las compras históricas para mantener la integridad.
     * </p>
     *
     * @param nif El identificador fiscal del cliente a eliminar.
     * @throws RuntimeException Si el cliente no existe.
     */
    @Transactional
    public void borrarFisico(String nif) {
        if (!clienteRepository.existsById(nif)) {
            throw new RuntimeException("Error: El cliente que intenta borrar no existe.");
        }
        clienteRepository.deleteById(nif);
    }

    /**
     * Transforma una entidad Cliente y su Información Fiscal asociada en un DTO aplanado.
     *
     * @param c Entidad {@link Cliente}.
     * @return {@link ClienteDTO} con los datos combinados para la vista.
     */
    private ClienteDTO convertirADTO(Cliente c) {
        return new ClienteDTO(
            c.getNifCif(), 
            c.getNombre(), 
            c.getEmail(), 
            c.getFechaRegistro(),
            c.getInformacionFiscal() != null ? c.getInformacionFiscal().getDireccionFiscal() : "N/A",
            c.getInformacionFiscal() != null ? c.getInformacionFiscal().getTelefono() : "N/A"
        );
    }
}