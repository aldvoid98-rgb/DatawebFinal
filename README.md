# DataWeb – Sistema de gestión de maquinaria para imprenta

Proyecto Spring Boot (Java 17, Thymeleaf, JPA, MySQL, JasperReports).

## Puesta en marcha
1. Ejecuta `BD_DATAWEB_CON_LOGIN_PARA_PROVEDOR_FINAL.txt` en MySQL 8.0.16 o superior (crea la base `DATAWEB` con sus datos).
2. Revisa usuario y contraseña de MySQL en `src/main/resources/application.properties`.
3. Ejecuta con **JDK 17** (no solo JRE; JasperReports compila los reportes al generarlos):
   `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`) o *Run As → Spring Boot App* en Spring Tools.
4. Abre `http://localhost:8080/formulario`.

## Cuentas de prueba
| Rol | Usuario | Contraseña |
|---|---|---|
| Administrador | admin@dataweb.com | Admin123 |
| Jefe de logística | jefe.logistica@dataweb.com | Logistica123 |
| Proveedor | ventas@grafic.com (y los otros 7 proveedores) | Proveedor123 |

Son credenciales de prueba: cámbialas antes de usar el sistema en producción.

## Permisos por rol (aplicados en el servidor)
Los controla `RoleAccessInterceptor` (lista blanca por ruta y método HTTP); ocultar botones en el navbar es solo cosmético.

| | Administrador | Jefe de logística | Proveedor |
|---|---|---|---|
| Inicio | ✔ | ✔ | ✔ |
| Listados de maquinaria, clientes, empleados, proveedores | ✔ | ✔ (solo consulta) | solo **maquinaria** |
| Registrar / editar / eliminar | ✔ | ✘ | ✘ |
| Reportes | todos | todos (consulta) | solo **maquinaria** |

Cualquier ruta no permitida responde **403** y las rutas sin sesión redirigen al login.

## Reportes
- `/reportes` lista de reportes · `/reportes/{tipo}` los **visualiza** en pantalla (PDF incrustado) · `/reportes/{tipo}/descargar?formato=pdf|xlsx` los descarga.
- Tipos principales: `maquinaria`, `clientes`, `empleados`, `proveedores` (solo registros activos).
- Causa del error anterior: `ReporteService` hacía un cast a `JRDesignSection` sobre la cabecera de columnas (que es `null`), por lo que el reporte siempre fallaba. Ahora usa `design.setColumnHeader(...)`.
- `jasperreports.properties` evita errores por fuentes faltantes.

## Eliminación lógica
El botón **Eliminar** (solo administrador, con confirmación) cambia `estado` de 1 a 0; no se borra nada de la base de datos.
Mensaje verde `Eliminado Correctamente` si sale bien y rojo `Eliminacion incorrecta` si falla.
Al eliminar un empleado o proveedor también se desactiva su cuenta de acceso. Un usuario no puede eliminar su propia cuenta.
Como el registro sigue existiendo, su DNI / RUC / correo siguen ocupados (campos únicos).
