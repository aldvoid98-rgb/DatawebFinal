package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cibertec.model.Maquinaria;
import com.cibertec.repository.ICategoriaMaquinariaRepository;
import com.cibertec.repository.IMaquinariaRepository;

@Controller
public class MaquinariaController {

	@Autowired
	private ICategoriaMaquinariaRepository repoCatMaq;

	@Autowired
	private IMaquinariaRepository repoMaq;

	@GetMapping("/cargaMaqui")
	public String cargarCrudMaquinaria(Model modelMaqui) {
		// enviar listados a la página (atributos) para generar los combos y se envian como atributos
		modelMaqui.addAttribute("lstCategoriaMaquinaria", repoCatMaq.findAll());
		modelMaqui.addAttribute("maquinaria", new Maquinaria());
		return "crudmaquinaria";
	}

	// Para leer los datos del form, usaremos Post
	@PostMapping("/regismaqui")
	public String grabarMaquinaria(Model modelMaqui, @ModelAttribute Maquinaria maquinaria) {
		System.out.println(maquinaria);
		// con los datos, registrar la nueva maquinaria
		// envia mensajes en caso de exito o error
		try {
			repoMaq.save(maquinaria);
			modelMaqui.addAttribute("mensaje", "Maquinaria Registrada");
			modelMaqui.addAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			modelMaqui.addAttribute("mensaje", "Error al Registrar: " + e.getMessage());
			modelMaqui.addAttribute("cssmensaje", "alert alert-danger");
		}
		// volver a enviar el combo y limpiar el formulario
		modelMaqui.addAttribute("lstCategoriaMaquinaria", repoCatMaq.findAll());
		modelMaqui.addAttribute("maquinaria", new Maquinaria());
		return "crudmaquinaria";
	}

	// solicitud tipo GET /listado, mostrar una tabla con las maquinarias
	@GetMapping("/listado")
	public String listarMaquinaria(Model modelMaqui) {
		// obtener un listado de las maquinarias y se envia como atributo
		modelMaqui.addAttribute("lstMaquinaria", repoMaq.findAll());
		return "listamaquinaria";
	}

	// boton editar
	@GetMapping("/editar/{id}")
	public String editar(Model modelMaqui, @PathVariable Integer id) {
		// Obtener un objeto de tipo Maquinaria segun el id enviado
		Maquinaria m = repoMaq.findById(id).get();
		// envia el objeto como atributo a la pagina
		modelMaqui.addAttribute("maquinaria", m);
		// Generar el listado para enviarlo a la pagina
		modelMaqui.addAttribute("lstCategoriaMaquinaria", repoCatMaq.findAll());
		// Retorno
		return "crudmaquinaria";
	}

}