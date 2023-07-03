package net.dev.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.dev.model.Vacante;
import net.dev.service.ICategoriasService;
import net.dev.service.IVacantesService;
import net.dev.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class vacantesController {

	@Autowired
	private IVacantesService serviceVacantes;

	@Autowired
	private ICategoriasService ics;

	@GetMapping("/index")
	public String mostrarIndex(Model model) {

		List<Vacante> lista = serviceVacantes.buscarTodas();

		model.addAttribute("vacante", lista);
		;
		return "vacantes/listVacante";
	}

	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacante", lista);
		return "vacantes/listVacante";
	}

	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		// model.addAttribute("categoria", ics.buscarTodas());
		return "vacantes/formVacante";
	}

//	@PostMapping("/save")
//	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
//			@RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha, @RequestParam("destacado") int destacado, 
//			@RequestParam("salario") double salario, @RequestParam("detalles") String detalle) {
//		
//		System.out.println("Nombre: "+ nombre);
//		System.out.println("descripcion: "+ descripcion);
//		System.out.println("estatus: "+ estatus);
//		System.out.println("fecha: "+ fecha);
//		System.out.println("destacado: "+ destacado);
//		System.out.println("salario: "+ salario);
//		System.out.println("detalle:" + detalle); 
//		
//		return "vacantes/listVacante";
//	}

	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("OCURRIO UN ERROR: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}

		if (!multiPart.isEmpty()) {
			// String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}

		serviceVacantes.guardar(vacante);
		attributes.addFlashAttribute("msj", "Registro Guardado");
		System.out.println("Vacante: " + vacante);
		return "redirect:/vacantes/index";
	}

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {

		Vacante v = serviceVacantes.buscarPorId(idVacante);

		System.out.println("ID VACANTE: " + v);
		model.addAttribute("vacante", v);
		return "detalle";
		// BUSCAR LOS DETALLES DE LA VACANTE EN LA BASE DE DATOS
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, Model model, RedirectAttributes attri) {

		System.out.println("Barrando vacante con id: " + idVacante);
		serviceVacantes.eliminar(idVacante);

		attri.addFlashAttribute("msj", "La vacante fue eliminada");

		return "redirect:/vacantes/index";

	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {

		Vacante v = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", v);
		// setGenericos(model);
		return "vacantes/formVacante";

	}

	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categoria", ics.buscarTodas());
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}