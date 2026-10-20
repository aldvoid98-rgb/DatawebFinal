package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Proveedores;


@Repository
public interface IProveedoresRepository extends JpaRepository<Proveedores, Integer> {
	//Todas las funciones del Crud

	// Eliminación lógica: estado 1 = activo, 0 = eliminado
	List<Proveedores> findByEstado(Integer estado);
}
