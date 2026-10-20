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

	    @Column(name = "nomClient")
	    private String nomCliente;

	    @Column(name = "apeClient")
	    private String apeCliente;

	    @Column(name = "dniClient")
	    private String dni;

	    @Column(name = "telClient")
	    private String telefono;

	    @Column(name = "correClient")
	    private String correo;

	    @Column(name = "direClient")
	    private String direccion;

	    @Column(name = "estado")
	    private Integer estado = 1;

	    @Column(name = "id_categoria_cliente")
	    private Integer idCatClient;

	    @ManyToOne
	    @JoinColumn(name = "id_categoria_cliente", insertable = false, updatable = false)
	    private CategoriaClientes objCatClient;

	
}
