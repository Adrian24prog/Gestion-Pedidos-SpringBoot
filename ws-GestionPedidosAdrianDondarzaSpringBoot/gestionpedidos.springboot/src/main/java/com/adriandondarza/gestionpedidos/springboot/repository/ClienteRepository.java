package com.adriandondarza.gestionpedidos.springboot.repository;

import com.adriandondarza.gestionpedidos.springboot.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio encargado de gestionar la persistencia de la entidad {@link Cliente}.
 * <p>
 * Proporciona acceso a la tabla de clientes en Oracle, incluyendo métodos para 
 * la validación de credenciales y la comprobación de duplicados antes de la 
 * inserción de nuevos registros.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    
    /**
     * Busca un cliente en la base de datos a través de su dirección de correo electrónico.
     * <p>
     * Este método es esencial para procesos de autenticación o recuperación de 
     * perfiles mediante un identificador alternativo al NIF.
     * </p>
     *
     * @param email Dirección de correo electrónico del cliente.
     * @return Un {@link Optional} que contiene al cliente si es encontrado, o vacío en caso contrario.
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Verifica si ya existe un registro asociado a una dirección de correo electrónico específica.
     * <p>
     * Se utiliza como mecanismo de validación previa en la capa de servicio para 
     * evitar violaciones de restricciones de unicidad (UNIQUE) en la base de datos.
     * </p>
     *
     * @param email Correo electrónico a comprobar.
     * @return {@code true} si el email ya está registrado, {@code false} en caso contrario.
     */
    boolean existsByEmail(String email);
}