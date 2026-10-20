package com.cibertec.config;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Control de acceso por rol, aplicado en el SERVIDOR.
 *
 * Funciona con lista blanca: lo que no está permitido expresamente
 * para un rol queda bloqueado (403).
 *
 * ADMINISTRADOR     -> acceso completo (consultar, registrar, editar, eliminar, reportes).
 * JEFE DE LOGISTICA -> solo consulta: inicio, listados de todas las entidades
 *                      y todos los reportes. Sin registrar, editar ni eliminar.
 * PROVEEDOR         -> solo inicio, listado de maquinaria y reporte de maquinaria.
 */
@Component
public class RoleAccessInterceptor implements HandlerInterceptor {

    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String JEFE_LOGISTICA = "JEFE DE LOGISTICA";
    public static final String PROVEEDOR = "PROVEEDOR";

    // Páginas de inicio: las pueden ver los tres roles
    private static final Set<String> RUTAS_INICIO =
            Set.of("/", "/inicio", "/principal");

    // Listados de consulta de todas las entidades (administrador y jefe)
    private static final Set<String> RUTAS_LISTADOS = Set.of(
            "/listado",
            "/listadoclientes",
            "/listadoEmpleados",
            "/listaprov");

    // /reportes, /reportes/{tipo}, /reportes/{tipo}/ver, /reportes/{tipo}/descargar
    private static final Pattern REPORTES_TODOS =
            Pattern.compile("^/reportes(/[a-z-]+(/(ver|descargar))?)?$");

    // Lo único que puede ver un proveedor en reportes
    private static final Pattern REPORTES_MAQUINARIA =
            Pattern.compile("^/reportes/maquinaria(/(ver|descargar))?$");

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        // Debe existir una sesión iniciada
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/formulario");
            return false;
        }

        Object valorRol = session.getAttribute("rol");

        if (valorRol == null) {
            return denegar(response);
        }

        String rol = valorRol.toString().trim().toUpperCase(Locale.ROOT);
        String ruta = normalizarRuta(request);
        String metodo = request.getMethod();

        boolean lectura = "GET".equalsIgnoreCase(metodo)
                || "HEAD".equalsIgnoreCase(metodo);

        // ADMINISTRADOR: acceso completo
        if (ADMINISTRADOR.equals(rol)) {
            return true;
        }

        // JEFE DE LOGISTICA: solo consulta
        if (JEFE_LOGISTICA.equals(rol)) {
            if (lectura && (RUTAS_INICIO.contains(ruta)
                    || RUTAS_LISTADOS.contains(ruta)
                    || REPORTES_TODOS.matcher(ruta).matches())) {
                return true;
            }
            return denegar(response);
        }

        // PROVEEDOR: inicio, listado de maquinaria y reporte de maquinaria
        if (PROVEEDOR.equals(rol)) {
            if (lectura && (RUTAS_INICIO.contains(ruta)
                    || "/listado".equals(ruta)
                    || REPORTES_MAQUINARIA.matcher(ruta).matches())) {
                return true;
            }
            return denegar(response);
        }

        // Cualquier otro rol queda bloqueado
        return denegar(response);
    }

    private String normalizarRuta(HttpServletRequest request) {

        String ruta = request.getRequestURI()
                .substring(request.getContextPath().length());

        int puntoYComa = ruta.indexOf(';');
        if (puntoYComa >= 0) {
            ruta = ruta.substring(0, puntoYComa);
        }

        if (ruta.length() > 1 && ruta.endsWith("/")) {
            ruta = ruta.substring(0, ruta.length() - 1);
        }

        return ruta.isEmpty() ? "/" : ruta;
    }

    private boolean denegar(HttpServletResponse response) throws Exception {
        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "Tu rol no tiene permisos para realizar esta operación.");
        return false;
    }
}
