package com.cibertec.controller;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.model.Contrasena;
import com.cibertec.model.Empleados;
import com.cibertec.model.Proveedores;
import com.cibertec.model.Rol;
import com.cibertec.repository.IContrasenaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Inicio de sesión para los tres roles:
 * ADMINISTRADOR y JEFE DE LOGISTICA (cuentas de empleados) y
 * PROVEEDOR (cuentas de proveedores).
 */
@Controller
public class LoginController {

    @Autowired
    private IContrasenaRepository contraRepo;

    @GetMapping("/formulario")
    public String cargarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam("username") String usuario,
            @RequestParam("password") String contrasena,
            HttpServletRequest request,
            HttpSession session,
            Model model) {

        Contrasena cuenta = contraRepo
                .findByUsuario(usuario.trim())
                .orElse(null);

        if (cuenta != null
                && Boolean.TRUE.equals(cuenta.getEstado())
                && contrasena.equals(cuenta.getContrasena())) {

            String rol = null;
            String nombre = null;

            Empleados empleado = cuenta.getObjEmpleado();
            Proveedores proveedor = cuenta.getObjProveedor();

            if (empleado != null) {
                // Cuenta de empleado: administrador o jefe de logística
                if (Integer.valueOf(1).equals(empleado.getEstado())
                        && rolActivo(empleado.getObjRol())) {
                    rol = empleado.getObjRol().getNomRol();
                    nombre = empleado.getNomEmpl() + " " + empleado.getApeEmpl();
                }
            } else if (proveedor != null) {
                // Cuenta de proveedor
                if (Integer.valueOf(1).equals(proveedor.getEstado())
                        && rolActivo(proveedor.getObjRol())) {
                    rol = proveedor.getObjRol().getNomRol();
                    nombre = proveedor.getRazonSocial();
                }
            }

            if (rol != null) {

                // Evita la fijación de sesión
                request.changeSessionId();

                session.setAttribute("usuario", cuenta.getUsuario());
                session.setAttribute("rol",
                        rol.trim().toUpperCase(Locale.ROOT));
                session.setAttribute("nombreEmpleado", nombre);

                registrarUltimoAcceso(cuenta);

                // Redirección para que F5 no reenvíe el formulario
                return "redirect:/inicio";
            }
        }

        model.addAttribute("error",
                "Usuario o contraseña incorrectos, o cuenta inactiva.");

        return "login";
    }

    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/formulario";
    }

    private boolean rolActivo(Rol rol) {
        return rol != null
                && rol.getNomRol() != null
                && Boolean.TRUE.equals(rol.getEstado());
    }

    // Guardar la fecha del último acceso no debe impedir el ingreso
    private void registrarUltimoAcceso(Contrasena cuenta) {
        try {
            cuenta.setUltimoAcceso(LocalDateTime.now());
            contraRepo.save(cuenta);
        } catch (Exception e) {
            // se ignora
        }
    }
}
