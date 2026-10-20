package com.cibertec.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Cuenta de acceso. Pertenece a un EMPLEADO (administrador, jefe de logística,
 * etc.) o a un PROVEEDOR, nunca a ambos (ver CHECK chk_contrasena_propietario).
 */
@Entity
@Table(name = "tb_contrasena")
@Data
public class Contrasena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrasena")
    private Integer idContrasena;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @Column(name = "estado")
    private Boolean estado;

    // Relación con el empleado (si la cuenta es de un empleado)
    @ManyToOne
    @JoinColumn(
        name = "id_empleado",
        insertable = false,
        updatable = false
    )
    private Empleados objEmpleado;

    // Relación con el proveedor (si la cuenta es de un proveedor)
    @ManyToOne
    @JoinColumn(
        name = "id_proveedor",
        insertable = false,
        updatable = false
    )
    private Proveedores objProveedor;
}
