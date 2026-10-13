package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_categoria_proveedor")
@Data
public class CategoriaProveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_proveedor")
    private Integer idCatProv;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descrip_prov")
    private String descripProv;
}


