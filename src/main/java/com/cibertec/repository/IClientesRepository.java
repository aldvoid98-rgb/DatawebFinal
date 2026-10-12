package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Clientes;

public interface IClientesRepository extends JpaRepository<Clientes, Integer> {
	// Todas las funciones del crud

}
