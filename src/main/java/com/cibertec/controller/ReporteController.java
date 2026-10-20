package com.cibertec.controller;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class ReporteController {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private DataSource dataSource;

	// Mostrar la página HTML de reportes
	@GetMapping("/reportes")
	public String reportes(Model model) {

		Map<String, String> tipos = new LinkedHashMap<>();

		tipos.put("maquinaria", "Maquinaria");
		tipos.put("clientes", "Clientes");
		tipos.put("empleados", "Empleados");
		tipos.put("proveedores", "Proveedores");

		model.addAttribute("tipos", tipos);

		return "reportes/index";
	}

	// Descargar el reporte de cada entidad en PDF
	@GetMapping("/reportes/{tipo}/descargar")
	public void descargarReporte(@PathVariable String tipo, HttpServletResponse response) {

		String archivo = "";

		if (tipo.equals("maquinaria")) {
			archivo = "reporte_maquinaria.jasper";
		} else if (tipo.equals("clientes")) {
			archivo = "reporte_clientes.jasper";
		} else if (tipo.equals("empleados")) {
			archivo = "reporte_empleados.jasper";
		} else if (tipo.equals("proveedores")) {
			archivo = "reporte_proveedores.jasper";
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"reporte_" + tipo + ".pdf\"");

		try {
			// Cargar el archivo Jasper desde resources
			String ruta = resourceLoader.getResource("classpath:templates/reportes/" + archivo).getURI().getPath();

			// Obtener la conexión a la base de datos
			Connection conexion = dataSource.getConnection();

			try {
				// Generar el reporte
				JasperPrint jasperPrint = JasperFillManager.fillReport(ruta, null, conexion);

				// Exportar el reporte a PDF
				OutputStream outStream = response.getOutputStream();

				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

				outStream.flush();

			} finally {
				conexion.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}