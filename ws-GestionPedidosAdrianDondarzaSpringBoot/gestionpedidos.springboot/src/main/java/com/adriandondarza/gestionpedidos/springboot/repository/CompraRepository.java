package com.adriandondarza.gestionpedidos.springboot.repository;

import com.adriandondarza.gestionpedidos.springboot.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio encargado de gestionar la persistencia y consultas complejas de la entidad {@link Compra}.
 * <p>
 * Este componente permite el acceso a los registros de ventas en Oracle, implementando 
 * tanto consultas derivadas por nombre de método como consultas personalizadas en JPQL 
 * para la optimización del rendimiento mediante la carga temprana de relaciones.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    /**
     * Recupera el histórico completo de pedidos realizados por un cliente específico 
     * utilizando su identificador fiscal.
     *
     * @param nifCif El Número de Identificación Fiscal del cliente.
     * @return {@link List} de {@link Compra} asociadas al cliente proporcionado.
     */
    List<Compra> findByClienteNifCif(String nifCif);

    /**
     * Busca una compra por su identificador único realizando una carga inmediata 
     * (Eager Loading) del cliente asociado.
     * <p>
     * Utiliza la técnica {@code JOIN FETCH} para mitigar el problema de rendimiento N+1, 
     * permitiendo obtener la cabecera del pedido y los datos del cliente en una 
     * única consulta SQL a la base de datos.
     * </p>
     *
     * @param id Identificador numérico de la compra.
     * @return Un {@link Optional} con la compra y su cliente cargado, o vacío si no existe.
     */
    @Query("SELECT c FROM Compra c JOIN FETCH c.cliente WHERE c.id = :id")
    Optional<Compra> findByIdWithCliente(@Param("id") Integer id);
}