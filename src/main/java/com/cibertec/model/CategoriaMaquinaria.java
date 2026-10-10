package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity		
@Table(name = "tb_categoria_maquinaria")
@Data
public class CategoriaMaquinaria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria_maquinaria")
	private Integer idCatMaqui; // int not null primary key,
	
	@Column(name = "descrip_maqui")
	private String descripMaqui; // varchar(45)
	
	}




