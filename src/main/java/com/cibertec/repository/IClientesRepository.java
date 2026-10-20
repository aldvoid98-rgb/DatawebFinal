package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Clientes;


@Repository
public interface IClientesRepository extends JpaRepository<Clientes, Integer> {
	// Todas las funciones del crud

	// Eliminación lógica: estado 1 = activo, 0 = eliminado
	List<Clientes> findByEstado(Integer estado);
}
