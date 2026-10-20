package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Empleados;


@Repository
public interface IEmpleadosRepository extends JpaRepository<Empleados, Integer> {
	//Todas las funciones del Crud

	// Eliminación lógica: estado 1 = activo, 0 = eliminado
	List<Empleados> findByEstado(Integer estado);
}
