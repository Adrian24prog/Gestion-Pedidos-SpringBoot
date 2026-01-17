package com.adriandondarza.gestionpedidos.springboot.service;

import com.adriandondarza.gestionpedidos.springboot.dto.*;
import com.adriandondarza.gestionpedidos.springboot.model.*;
import com.adriandondarza.gestionpedidos.springboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de orquestación para la gestión de pedidos y transacciones comerciales.
 * <p>
 * Esta clase centraliza la lógica de negocio más crítica del sistema, incluyendo:
 * <ul>
 * <li>Validación de identidad de clientes y existencia de productos.</li>
 * <li>Control de inventario (Stock) en tiempo real.</li>
 * <li>Cálculo de importes totales y "congelación" de precios históricos.</li>
 * <li>Gestión de estados del ciclo de vida del pedido.</li>
 * </ul>
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Service
public class CompraService {

    @Autowired private CompraRepository compraRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ArticuloRepository articuloRepository;

    /**
     * Procesa y registra una nueva orden de compra de forma atómica.
     * <p>
     * El método realiza las siguientes operaciones bajo una única transacción:
     * 1. Verifica la existencia del cliente.
     * 2. Valida la disponibilidad de stock para cada artículo solicitado.
     * 3. Descuenta las unidades del inventario.
     * 4. Calcula el subtotal por línea usando el precio actual (que quedará congelado).
     * 5. Persiste la cabecera y el detalle de la compra mediante cascada.
     * </p>
     *
     * @param dto Objeto con la información del cliente, dirección y artículos.
     * @return {@link CompraRespuestaDTO} con el resumen del pedido procesado.
     * @throws RuntimeException Si el cliente no existe, un artículo no se encuentra o no hay stock suficiente.
     */
    @Transactional
    public CompraRespuestaDTO procesarCompra(CompraRegistroDTO dto) {
        // 1. Validar Cliente
        Cliente cliente = clienteRepository.findById(dto.getClienteNif())
                .orElseThrow(() -> new RuntimeException("Cliente no identificado"));

        // 2. Crear cabecera de Compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setFechaRealizada(LocalDateTime.now());
        compra.setDireccionEnvio(dto.getDireccionEnvio());
        compra.setEstado(EstadoCompra.PENDIENTE);

        BigDecimal totalCompra = BigDecimal.ZERO;

        // 3. Procesar líneas (ArticuloCompra)
        for (LineaCompraDTO lineaDto : dto.getLineas()) {
            Articulo articulo = articuloRepository.findById(lineaDto.getArticuloId())
                    .orElseThrow(() -> new RuntimeException("Articulo ID " + lineaDto.getArticuloId() + " no existe"));

            // Validación de integridad de stock
            if (articulo.getStock() < lineaDto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + articulo.getNombre());
            }

            // Instanciamos la entidad de asociación con atributos adicionales
            ArticuloCompra detalle = new ArticuloCompra();
            detalle.setCompra(compra);
            detalle.setArticulo(articulo);
            detalle.setUnidades(lineaDto.getCantidad());
            detalle.setPrecioCompra(articulo.getPrecioActual()); // Captura del precio histórico

            // Actualizamos stock del artículo (Persistencia en caliente)
            articulo.setStock(articulo.getStock() - lineaDto.getCantidad());
            articuloRepository.save(articulo);

            // Vinculamos la línea con la cabecera
            compra.getLineasArticulos().add(detalle);
            
            // Cálculo financiero de la línea
            BigDecimal subtotal = articulo.getPrecioActual().multiply(new BigDecimal(lineaDto.getCantidad()));
            totalCompra = totalCompra.add(subtotal);
        }

        compra.setPrecioTotal(totalCompra);
        
        // El guardado de la compra persiste también las líneas debido a CascadeType.ALL
        Compra guardada = compraRepository.save(compra);

        return convertirADTO(guardada);
    }

    /**
     * Recupera el histórico global de todas las compras realizadas en el sistema.
     *
     * @return {@link List} de {@link CompraRespuestaDTO}.
     */
    @Transactional(readOnly = true)
    public List<CompraRespuestaDTO> obtenerTodas() {
        return compraRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    /**
     * Filtra y recupera los pedidos realizados por un cliente específico.
     *
     * @param nif Identificador fiscal del cliente.
     * @return {@link List} de compras asociadas al cliente.
     */
    @Transactional(readOnly = true)
    public List<CompraRespuestaDTO> obtenerComprasPorCliente(String nif) {
        return compraRepository.findByClienteNifCif(nif).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza el estado administrativo de un pedido existente.
     *
     * @param id Identificador de la compra.
     * @param nuevoEstado Nombre del nuevo estado (pendiente de validar contra el Enum).
     * @return {@link CompraRespuestaDTO} con el estado actualizado.
     * @throws IllegalArgumentException Si el estado proporcionado no es válido.
     */
    @Transactional
    public CompraRespuestaDTO cambiarEstado(Integer id, String nuevoEstado) {
        Compra c = compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        c.setEstado(EstadoCompra.valueOf(nuevoEstado.toUpperCase()));
        return convertirADTO(compraRepository.save(c));
    }

    /**
     * Transforma una entidad Compra en un DTO de respuesta para la capa de presentación.
     *
     * @param c Entidad {@link Compra}.
     * @return {@link CompraRespuestaDTO} con datos legibles para el usuario.
     */
    private CompraRespuestaDTO convertirADTO(Compra c) {
        return new CompraRespuestaDTO(
            c.getId(), c.getFechaRealizada(), c.getEstado().name(),
            c.getDireccionEnvio(), c.getPrecioTotal(), 
            c.getCliente() != null ? c.getCliente().getNombre() : "Cliente eliminado"
        );
    }
}