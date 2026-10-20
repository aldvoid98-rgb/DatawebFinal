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
import com.cibertec.model.Empleados;
import com.cibertec.repository.IContrasenaRepository;
import com.cibertec.repository.ICategoriaEmpleadosRepository;
import com.cibertec.repository.IEmpleadosRepository;
import com.cibertec.repository.IRolRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmpleadosController {

	@Autowired
	private IContrasenaRepository repoCuenta;

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

		modelEmpl.addAttribute("lstEmpleados", repoEmpl.findByEstado(1));

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

	// Eliminación lógica: estado = 0 y se desactiva su cuenta de acceso.
	// Solo ADMINISTRADOR (lo exige RoleAccessInterceptor en el servidor).
	@PostMapping("/eliminarEmpleado/{id}")
	public String eliminarEmpleado(@PathVariable Integer id, HttpSession session,
			RedirectAttributes redirect) {
		try {
			Empleados e = repoEmpl.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("No existe el empleado: " + id));
			if (!Integer.valueOf(1).equals(e.getEstado())) {
				throw new IllegalStateException("El empleado ya estaba eliminado");
			}

			Optional<Contrasena> cuenta = repoCuenta.findByIdEmpleado(id);

			// Nadie puede eliminar su propia cuenta
			String usuarioActual = (String) session.getAttribute("usuario");
			if (cuenta.isPresent() && usuarioActual != null
					&& usuarioActual.equalsIgnoreCase(cuenta.get().getUsuario())) {
				throw new IllegalStateException("No puedes eliminar tu propia cuenta");
			}

			e.setEstado(0);
			repoEmpl.save(e);

			// El empleado eliminado ya no puede iniciar sesión
			if (cuenta.isPresent()) {
				Contrasena c = cuenta.get();
				c.setEstado(Boolean.FALSE);
				repoCuenta.save(c);
			}
			redirect.addFlashAttribute("mensaje", "Eliminado Correctamente");
			redirect.addFlashAttribute("cssmensaje", "alert alert-success");
		} catch (Exception ex) {
			redirect.addFlashAttribute("mensaje", "Eliminacion incorrecta");
			redirect.addFlashAttribute("cssmensaje", "alert alert-danger");
		}
		return "redirect:/listadoEmpleados";
	}
}
