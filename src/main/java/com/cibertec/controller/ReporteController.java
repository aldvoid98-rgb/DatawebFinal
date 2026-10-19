package com.cibertec.controller;

import java.io.OutputStream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	private DataSource dataSource;         // javax.sql

	@Autowired
	private ResourceLoader resourceLoader; // core.io

	// 1. REPORTE DE MAQUINARIAS
		@GetMapping("/maquinaria")
		public void reporteMaquinarias(HttpServletResponse response) {
			generarPdf("classpath:reports/reportemaquinarias.jasper", "reporte_maquinarias.pdf", response);
		}

		// 2. REPORTE DE CLIENTES
		@GetMapping("/clientes")
		public void reporteClientes(HttpServletResponse response) {
			generarPdf("classpath:reports/reporteclientes.jasper", "reporte_clientes.pdf", response);
		}

		// 3. REPORTE DE EMPLEADOS
		@GetMapping("/empleados")
		public void reporteEmpleados(HttpServletResponse response) {
			generarPdf("classpath:reports/reporteempleados.jasper", "reporte_empleados.pdf", response);
		}

		// 4. REPORTE DE PROVEEDORES
		@GetMapping("/proveedores")
		public void reporteProveedores(HttpServletResponse response) {
			generarPdf("classpath:reports/reporteproveedores.jasper", "reporte_proveedores.pdf", response);
		}

	// Método auxiliar reutilizable
		private void generarPdf(String rutaJasper, String nombrePdf, HttpServletResponse response) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + nombrePdf + "\"");
			response.setContentType("application/pdf");

			try {
				// 1. Cargar el archivo .jasper
				java.io.InputStream reportStream = resourceLoader.getResource(rutaJasper).getInputStream();
				
				// 2. Cargar la imagen usando el Classpath de Spring Boot (funciona en cualquier PC)
				java.io.InputStream logoStream = resourceLoader.getResource("classpath:static/img/LogoDataweb.jpg").getInputStream();

				// 3. Empaquetar la imagen en el mapa de parámetros
				java.util.Map<String, Object> parametros = new java.util.HashMap<>();
				parametros.put("LogoDataWeb", logoStream);

				// 4. Llenar el reporte enviando los parámetros
				JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parametros, dataSource.getConnection());
				
				// 5. Exportar a PDF
				OutputStream outStream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

    //@GetMapping
    //public String mostrarDashboard() {
      //  return "reportes/index";
    //}

    //@GetMapping("/maquinaria")
    //public String mostrarMaquinaria() {
      //  return "reportes/maquinaria";
    //}

    //@GetMapping("/empleados")
    //public String mostrarEmpleados() {
      //  return "reportes/empleados";
    //}

  //  @GetMapping("/clientes")
   // public String mostrarClientes() {
      //  return "reportes/clientes";
   // }

   // @GetMapping("/proveedores")
   // public String mostrarProveedores() {
     //   return "reportes/proveedores";
    //}

