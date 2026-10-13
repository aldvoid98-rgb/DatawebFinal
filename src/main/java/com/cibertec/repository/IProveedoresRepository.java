package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Proveedores;


@Repository
public interface IProveedoresRepository extends JpaRepository<Proveedores, Integer> {
	//Todas las funciones del Crud

}
