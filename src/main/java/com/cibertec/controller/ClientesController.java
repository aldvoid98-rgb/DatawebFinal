package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cibertec.model.Clientes;
import com.cibertec.repository.ICategoriaClientesRepository;
import com.cibertec.repository.IClientesRepository;

@Controller
public class ClientesController {

	@Autowired
	private ICategoriaClientesRepository repoCatClient;

	@Autowired
	private IClientesRepository repoClient;

	// Cargar el formulario de clientes
	@GetMapping("/cargaClientes")
	public String cargarCrudClientes(Model modelClient) {

		modelClient.addAttribute("lstCategoriaClientes", repoCatClient.findAll());
		modelClient.addAttribute("clientes", new Clientes());
		return "crudclientes";
	}

	// Registrar o actualizar un cliente
	@PostMapping("/regisclientes")
	public String grabarClientes(Model modelClient, @ModelAttribute("clientes") Clientes clientes) {
		try {
			repoClient.save(clientes);
			modelClient.addAttribute("mensaje", clientes.getIdCliente() == null ? "Cliente registrado correctamente"
					: "Cliente actualizado correctamente");
			modelClient.addAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			modelClient.addAttribute("mensaje", "Error al registrar o actualizar el cliente. " + e.getMessage());
			modelClient.addAttribute("cssmensaje", "alert alert-danger");
		}

		// Recargar categorías y limpiar el formulario
		modelClient.addAttribute("lstCategoriaClientes", repoCatClient.findAll());
		modelClient.addAttribute("clientes", new Clientes());
		return "crudclientes";
	}

	// Listar clientes
	@GetMapping("/listadoclientes")
	public String listarClientes(Model modelClient) {
		modelClient.addAttribute("lstClientes", repoClient.findAll());
		return "listaclientes";
	}

	// Buscar un cliente para editarlo
	@GetMapping("/editarCliente/{id}")
	public String editarCliente(Model modelClient, @PathVariable Integer id) {

		Clientes cliente = repoClient.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No existe un cliente con el ID: " + id));
		
		modelClient.addAttribute("clientes", cliente);
		modelClient.addAttribute("lstCategoriaClientes", repoCatClient.findAll());

		return "crudclientes";
	}
}
