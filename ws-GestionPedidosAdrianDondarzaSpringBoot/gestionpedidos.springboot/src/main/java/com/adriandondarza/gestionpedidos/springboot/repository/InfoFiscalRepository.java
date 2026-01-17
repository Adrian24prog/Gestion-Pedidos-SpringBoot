package com.adriandondarza.gestionpedidos.springboot.repository;

import com.adriandondarza.gestionpedidos.springboot.model.InfoFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio encargado de gestionar la persistencia de la entidad {@link InfoFiscal}.
 * <p>
 * Proporciona acceso directo a la tabla de información fiscal en Oracle. Debido a 
 * la arquitectura de clave compartida, este repositorio es el punto de gestión 
 * principal para las operaciones de borrado y registro que deben propagarse 
 * hacia la entidad Cliente.
 * </p>
 *
 * @author Adrian Dondarza
 * @version 1.0
 * @since 2026-01-14
 */
@Repository
public interface InfoFiscalRepository extends JpaRepository<InfoFiscal, String> {
    // Al extender de JpaRepository, hereda automáticamente todos los métodos CRUD
    // necesarios para la gestión fiscal utilizando el NIF como clave primaria.
}