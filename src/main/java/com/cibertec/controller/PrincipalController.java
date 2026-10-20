package com.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {

    // Las rutas están protegidas por los interceptores:
    // sin sesión se redirige al login.
    @GetMapping({ "/", "/inicio" })
    public String cargarInicio() {
        return "inicio";
    }

    @GetMapping("/principal")
    public String cargarPrincipal() {
        return "Principal";
    }
}
