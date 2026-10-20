package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.model.CategoriaClientes;


@Repository
public interface ICategoriaClientesRepository  extends JpaRepository<CategoriaClientes, Integer>{
	// todas las funciones básicas del CRUD
	

}