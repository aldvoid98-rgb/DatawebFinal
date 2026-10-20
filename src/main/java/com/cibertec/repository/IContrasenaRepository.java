package com.cibertec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Contrasena;

@Repository
public interface IContrasenaRepository extends JpaRepository<Contrasena, Integer> {

	Optional<Contrasena> findByUsuario(String usuario);

	Optional<Contrasena> findByIdEmpleado(Integer idEmpleado);

	Optional<Contrasena> findByIdProveedor(Integer idProveedor);
}
