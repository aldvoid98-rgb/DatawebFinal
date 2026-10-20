package com.cibertec.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.model.Contrasena;
import com.cibertec.model.Proveedores;
import com.cibertec.repository.IContrasenaRepository;
import com.cibertec.repository.ICategoriaProveedoresRepository;
import com.cibertec.repository.IProveedoresRepository;
import com.cibertec.repository.IRolRepository;

@Controller
public class ProveedoresController {

	@Autowired
	private IContrasenaRepository repoCuenta;

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

		model.addAttribute("lstProveedores", repoProv.findByEstado(1));
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

	// Eliminación lógica: estado = 0 y se desactiva su cuenta de acceso.
	// Solo ADMINISTRADOR (lo exige RoleAccessInterceptor en el servidor).
	@PostMapping("/eliminarProv/{id}")
	public String eliminarProveedor(@PathVariable Integer id, RedirectAttributes redirect) {
		try {
			Proveedores p = repoProv.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("No existe el proveedor: " + id));
			if (!Integer.valueOf(1).equals(p.getEstado())) {
				throw new IllegalStateException("El proveedor ya estaba eliminado");
			}

			p.setEstado(0);
			repoProv.save(p);

			// El proveedor eliminado ya no puede iniciar sesión
			Optional<Contrasena> cuenta = repoCuenta.findByIdProveedor(id);
			if (cuenta.isPresent()) {
				Contrasena c = cuenta.get();
				c.setEstado(Boolean.FALSE);
				repoCuenta.save(c);
			}
			redirect.addFlashAttribute("mensaje", "Eliminado Correctamente");
			redirect.addFlashAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			redirect.addFlashAttribute("mensaje", "Eliminacion incorrecta");
			redirect.addFlashAttribute("cssmensaje", "alert alert-danger");
		}
		return "redirect:/listaprov";
	}
}
