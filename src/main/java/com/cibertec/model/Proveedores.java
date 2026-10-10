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
@Table(name="tb_proveedores")
@Data
public class Proveedores {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_proveedor")
	private Integer idProveedor;
	
	@Column(name="razon_social")
    private String razonSocial;

	@Column(name="ruc")
    private String ruc;

	@Column(name="contacto")
    private String contacto;

	@Column(name="telefono")
    private String telefono;

	@Column(name="correo")
    private String correo;

	@Column(name="direccion")
    private String direccion;

	@Column(name="id_rol")
    private String id_rol;

	@Column(name="estado")
    private Integer estado = 1;
	
	@ManyToOne
	@JoinColumn(name = "idCatEmple", insertable = false, updatable = false)
	private CategoriaEmpleados objCatEmple;

}
