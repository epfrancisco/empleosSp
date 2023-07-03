package net.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.dev.model.Categoria;
import net.dev.service.ICategoriasService;

@Controller
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private ICategoriasService csi;

	// @GetMapping("/index")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String mostrarIndex(Model model) {
		List<Categoria> listado = csi.buscarTodas();
		model.addAttribute("categoria", listado);

		return "categorias/listCategorias";

	}

	@RequestMapping(value = "/indexPaginate", method = RequestMethod.GET)
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> listado = csi.buscarTodas(page);
		model.addAttribute("categoria", listado);

		return "categorias/listCategorias";

	}

	// @GetMapping("/create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String crear(Categoria categoria) {

		return "categorias/formCategorias";

	}

	// @PostMapping("/save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String guardar(Categoria ca, BindingResult result, RedirectAttributes rd) {
		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "categorias/formCategoria";
		}

		csi.guardar(ca);
		rd.addFlashAttribute("msj", "CATEGORIA GUARDADA");

		return "redirect:/categorias/index";

	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int id, RedirectAttributes attr) {
		csi.eliminar(id);
		attr.addFlashAttribute("msj", "Categoria eliminada");
		return "redirect:/categorias/index";

	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int id, Model model) {
		Categoria c = csi.buscarPorId(id);
		model.addAttribute("categoria", c);
		return "/categorias/formCategorias";

	}

}
