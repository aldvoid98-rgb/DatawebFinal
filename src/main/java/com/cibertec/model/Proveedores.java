package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_proveedores")
@Data
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "telProv")
    private String telefono;

    @Column(name = "correProv")
    private String correo;

    @Column(name = "direProv")
    private String direccion;

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "estado")
    private Integer estado = 1;

    @Column(name = "id_categoria_proveedor")
    private Integer idCatProv;

    @ManyToOne
    @JoinColumn(
        name = "id_categoria_proveedor",
        insertable = false,
        updatable = false
    )
    private CategoriaProveedores objCatProv;

    @ManyToOne
    @JoinColumn(
        name = "id_rol",
        insertable = false,
        updatable = false
    )
    private Rol objRol;
}
