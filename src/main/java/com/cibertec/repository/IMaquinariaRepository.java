package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Maquinaria;

@Repository
public interface IMaquinariaRepository extends JpaRepository<Maquinaria, Integer> {
	// todas las funciones básicas del CRUD

	// Eliminación lógica: estado 1 = activo, 0 = eliminado
	List<Maquinaria> findByEstado(Integer estado);
}
