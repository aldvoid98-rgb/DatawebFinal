package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.CategoriaMaquinaria;


@Repository
public interface ICategoriaMaquinariaRepository extends JpaRepository<CategoriaMaquinaria, Integer>{
	// todas las funciones básicas del CRUD
	

}



