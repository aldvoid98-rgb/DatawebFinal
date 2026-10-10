package com.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

	@GetMapping("/formulario")
	public String cargarLogin() {
		// aquí viene el proceso o lógica
		return "login";
	}

}
