package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cibertec.model.Proveedores;
import com.cibertec.repository.ICategoriaProveedoresRepository;
import com.cibertec.repository.IProveedoresRepository;
import com.cibertec.repository.IRolRepository;

@Controller
public class ProveedoresController {

	@Autowired
	private IProveedoresRepository repoProv;

	@Autowired
	private ICategoriaProveedoresRepository repoCatProv;

	@Autowired
	private IRolRepository repoRol;

	// Cargar formulario
	@GetMapping("/cargaProv")
	public String cargarCrudProveedores(Model modelProv) {

		modelProv.addAttribute("lstCategoriaProveedores", repoCatProv.findAll());
		modelProv.addAttribute("lstRoles", repoRol.findAll());
		modelProv.addAttribute("proveedores", new Proveedores());

		return "crudproveedores";
	}

	// Registrar o actualizar proveedor
	@PostMapping("/regisprov")
	public String grabarProveedores(Model modelProv, @ModelAttribute("proveedores") Proveedores proveedores) {

		try {
			repoProv.save(proveedores);

			modelProv.addAttribute("mensaje", proveedores.getIdProveedor() == null ? "Proveedor registrado correctamente"
					: "Proveedor actualizado correctamente");
			modelProv.addAttribute("cssmensaje", "alert alert-success");

		} catch (Exception e) {

			modelProv.addAttribute("mensaje", "Error al guardar el proveedor: " + e.getMessage());
			modelProv.addAttribute("cssmensaje", "alert alert-danger");
		}

		// Recargar combos y limpiar el formulario
		modelProv.addAttribute("lstCategoriaProveedores", repoCatProv.findAll());
		modelProv.addAttribute("lstRoles", repoRol.findAll());
		modelProv.addAttribute("proveedores", new Proveedores());

		return "crudproveedores";
	}

	// Listar proveedores
	@GetMapping("/listaprov")
	public String listarProveedores(Model model) {

		model.addAttribute("lstProveedores", repoProv.findAll());
		return "listaproveedores";
	}

	// Editar proveedor
	@GetMapping("/editarProv/{id}")
	public String editarProveedor(Model modelProv, @PathVariable Integer id) {

		Proveedores proveedor = repoProv.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No existe el proveedor con ID: " + id));

		modelProv.addAttribute("proveedores", proveedor);
		modelProv.addAttribute("lstCategoriaProveedores", repoCatProv.findAll());
		modelProv.addAttribute("lstRoles", repoRol.findAll());

		return "crudproveedores";
	}
}
