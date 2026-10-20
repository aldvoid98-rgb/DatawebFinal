package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_rol")
@Data
public class Rol {


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_rol")
	    private Integer id_rol;

	    @Column(name = "nomRol")
	    private String nomRol;

	    @Column(name = "descripcion")
	    private String descripcion;

	    @Column(name = "estado")
	    private Boolean estado = true;
	
}
