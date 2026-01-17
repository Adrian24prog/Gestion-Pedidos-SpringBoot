📦 Gestión de Pedidos - Spring Boot Library
Backend profesional estructurado como librería Java con persistencia en Oracle. Diseñado para ser consumido por aplicaciones de escritorio mediante una arquitectura limpia de capas.

🚀 Características
Diseño de Librería: Empaquetado como Plain JAR para evitar conflictos de dependencias.

Acceso Restringido: El cliente solo interactúa con Controllers y DTOs.

Seguridad Oracle:

UNIQUE Constraints: NIF, Teléfono y Email protegidos contra duplicados.

Triggers: Bloqueo de borrado físico en artículos y compras (obliga al borrado lógico).

Validaciones: Control de formato de NIF y stock en la capa de servicios.

🛠️ Tecnologías
Java 21 & Spring Boot 3.2.0 (Data JPA).

Docker: Contenedorización de la base de datos para entornos aislados.

Oracle DB: Motor de base de datos relacional.

Maven & Lombok.
