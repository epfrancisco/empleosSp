package net.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.dev.service.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuariosService ius;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		
		model.addAttribute("usuario",ius.buscarTodos());
		return "usuarios/listUsuarios";
		
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int id, RedirectAttributes attr) {
		
		ius.eliminar(id);
		
		attr.addFlashAttribute("msj", "Usuario Eliminado");
		
		return "redirect:/usuarios/index";
		
	}
	

}
