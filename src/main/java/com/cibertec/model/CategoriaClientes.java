package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_categoria_cliente")
@Data
public class CategoriaClientes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_categoria_cliente")
	private Integer idCatClient; // int not null primary key,
	
	@Column(name="descrip_client")
	private String descripClient; // varchar(45)

}
