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
@Table(name = "tb_maquinaria")
@Data
public class Maquinaria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_maquinaria")
	private Integer id_maquinaria;

	@Column(name = "nomMaqui")
	private String nomMaqui;

	@Column(name = "marca")
	private String marca;

	@Column(name = "potenciaKw")
	private Double potenciaKw;

	@Column(name = "precio")
	private Double precio;

	// Eliminación lógica: 1 = registro activo, 0 = eliminado
	@Column(name = "estado")
	private Integer estado = 1;

	// Situación operativa de la máquina: 1 = Activo, 2 = En mantenimiento, 3 = Inactivo
	@Column(name = "situacion")
	private Integer situacion = 1;
	
	@Column(name = "id_categoria_maquinaria")
	private Integer idCatMaqui;

	@ManyToOne
	@JoinColumn(name = "id_categoria_maquinaria", insertable = false, updatable = false)
	private CategoriaMaquinaria objCatMaqui;

}
