package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.CategoriaProveedores;


@Repository
public interface ICategoriaProveedoresRepository extends JpaRepository<CategoriaProveedores, Integer> {
	// todas las funciones básicas del CRUD
}
