package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cibertec.model.Empleados;
import com.cibertec.repository.ICategoriaEmpleadosRepository;
import com.cibertec.repository.IEmpleadosRepository;
import com.cibertec.repository.IRolRepository;

@Controller
public class EmpleadosController {

	@Autowired
	private IEmpleadosRepository repoEmpl;

	@Autowired
	private ICategoriaEmpleadosRepository repoCatEmpl;

	@Autowired
	private IRolRepository repoRol;

	// Cargar formulario de empleados
	@GetMapping("/cargaEmpleados")
	public String cargarCrudEmpleados(Model modelEmpl) {

		cargarCombos(modelEmpl);
		modelEmpl.addAttribute("empleados", new Empleados());

		return "crudempleados";
	}

	// Registrar o actualizar empleados
	@PostMapping("/regisempleado")
	public String grabarEmpleado(Model modelEmpl, @ModelAttribute("empleados") Empleados empleados) {

		try {
			repoEmpl.save(empleados);

			modelEmpl.addAttribute("mensaje", empleados.getId_empleado() == null ? "Empleado registrado correctamente"
					: "Empleado actualizado correctamente");

			modelEmpl.addAttribute("cssmensaje", "alert alert-success");

		} catch (Exception e) {

			modelEmpl.addAttribute("mensaje",
					"Error al guardar el empleado. Verifique que el DNI y el correo no estén duplicados.");

			modelEmpl.addAttribute("cssmensaje", "alert alert-danger");
		}

		cargarCombos(modelEmpl);
		modelEmpl.addAttribute("empleados", new Empleados());

		return "crudempleados";
	}

	// Listar empleados
	@GetMapping("/listadoEmpleados")
	public String listarEmpleados(Model modelEmpl) {

		modelEmpl.addAttribute("lstEmpleados", repoEmpl.findAll());

		return "listaempleados";
	}

	// Editar empleado
	@GetMapping("/editarEmpleado/{id}")
	public String editarEmpleado(@PathVariable Integer id, Model modelEmpl) {

		Empleados empleado = repoEmpl.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado: " + id));

		cargarCombos(modelEmpl);
		modelEmpl.addAttribute("empleados", empleado);

		return "crudempleados";
	}

	// Cargar las categorías y los roles
	private void cargarCombos(Model modelEmpl) {

		modelEmpl.addAttribute("lstCategoriaEmpleados", repoCatEmpl.findAll());

		modelEmpl.addAttribute("lstRoles", repoRol.findAll());
	}
}