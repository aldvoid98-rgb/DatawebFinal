package com.cibertec.model;

import java.time.LocalDate;

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
@Table(name = "tb_empleados")
@Data
public class Empleados {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empleado")
	private Integer id_empleado;

	@Column(name = "nomEmpl")
	private String nomEmpl;

	@Column(name = "apeEmpl")
	private String apeEmpl;

	@Column(name = "dniEmpl")
	private String dniEmpl;

	@Column(name = "cargo")
	private String cargo;

	@Column(name = "telEmpl")
	private String telEmpl;

	@Column(name = "correEmpl")
	private String correEmpl;

	@Column(name = "fecha_ingreso")
	private LocalDate fecha_ingreso;

	@Column(name = "estado")
	private Integer estado = 1;

	// Clave foránea de la categoría
	@Column(name = "id_categoria_empleado")
	private Integer idCatEmple;

	@ManyToOne
	@JoinColumn(name = "id_categoria_empleado", insertable = false, updatable = false)
	private CategoriaEmpleados objCatEmple;

	// Clave foránea del rol
	@Column(name = "id_rol")
	private Integer id_rol;

	@ManyToOne
	@JoinColumn(name = "id_rol", insertable = false, updatable = false)
	private Rol objRol;

}
