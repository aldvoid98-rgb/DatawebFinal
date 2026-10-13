package com.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @GetMapping
    public String mostrarDashboard() {
        return "reportes/index";
    }

    @GetMapping("/maquinaria")
    public String mostrarMaquinaria() {
        return "reportes/maquinaria";
    }

    @GetMapping("/empleados")
    public String mostrarEmpleados() {
        return "reportes/empleados";
    }

    @GetMapping("/clientes")
    public String mostrarClientes() {
        return "reportes/clientes";
    }

    @GetMapping("/proveedores")
    public String mostrarProveedores() {
        return "reportes/proveedores";
    }
}
