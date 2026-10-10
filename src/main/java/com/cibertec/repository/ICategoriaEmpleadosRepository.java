package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.CategoriaEmpleados;


@Repository
public interface ICategoriaEmpleadosRepository extends JpaRepository<CategoriaEmpleados, Integer> {
	// todas las funciones básicas del CRUD
}
