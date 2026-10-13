package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_categoria_empleado")
@Data
public class CategoriaEmpleados {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_categoria_empleado")
	private Integer idCatEmple; // int not null primary key,
	
	@Column(name="descrip_empl")
	private String descripEmple; // varchar(45)

}
