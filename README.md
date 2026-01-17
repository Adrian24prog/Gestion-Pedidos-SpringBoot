# 📦 Gestión de Pedidos - Spring Boot Library

Backend profesional estructurado como **librería Java** con persistencia en **Oracle**. Diseñado para ser consumido por aplicaciones de escritorio mediante una arquitectura limpia de capas.

---

### 🚀 Características
* **Diseño de Librería:** Empaquetado como *Plain JAR* para evitar conflictos de dependencias.
* **Acceso Restringido:** El cliente solo interactúa con la capa de `Controllers` y `DTOs`.
* **Seguridad Oracle:**
    * `UNIQUE Constraints`: NIF, Teléfono y Email protegidos contra duplicados.
    * `Triggers`: Bloqueo de borrado físico en artículos y compras (obliga al borrado lógico).
* **Validaciones:** Control estricto de formato de NIF y stock en la capa de servicios.

---

### 🛠️ Tecnologías
* **Java 21 & Spring Boot 3.2.0** (Data JPA).
* **Docker:** Contenedorización de la base de datos para entornos aislados.
* **Oracle DB:** Motor de base de datos relacional.
* **Herramientas:** Maven & Lombok.

---

### 📂 Instalación y Configuración

1. **Levantar Base de Datos (Docker):**
   ```bash
   docker run -d --name pedidos_oracle -p 1521:1521 -e ORACLE_PASSWORD=Abcd1234$ -e ORACLE_PDB=FREEPDB1 -v usuarios_oracle_data:/opt/oracle/oradata gvenzl/oracle-free:full-faststart
