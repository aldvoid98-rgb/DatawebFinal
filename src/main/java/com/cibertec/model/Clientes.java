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
@Table(name = "tb_clientes")
@Data
public class Clientes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Integer idCliente;

	@Column(name="nomCliente")
	private String nomCliente;

	@Column(name="apeCliente")
	private String apeCliente;

	@Column(name="dni")
	private String dni;

	@Column(name="telefono")
	private String telefono;

	@Column(name="correo")
	private String correo;

	@Column(name="direccion")
	private String direccion;

	@Column(name="estado")
	private Integer estado = 1;
	
	@ManyToOne
	@JoinColumn(name = "idCatClient", insertable = false, updatable = false)
	private CategoriaClientes objCatClient;

}
