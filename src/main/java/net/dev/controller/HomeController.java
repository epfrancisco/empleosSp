package net.dev.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.dev.model.Perfil;
import net.dev.model.Usuario;
import net.dev.model.Vacante;
import net.dev.service.ICategoriasService;
import net.dev.service.IUsuariosService;
//import net.dev.model.Vacante;
import net.dev.service.IVacantesService;

@Controller
public class HomeController {

	@Autowired
	private IVacantesService serviceVacantes;

	@Autowired
	private IUsuariosService isu;

	@Autowired
	private ICategoriasService isc;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String mostrarHome(Model model) {

		return "home";

	}

	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession sesion) {

		String username = auth.getName();
		System.out.println("nombre usuario: " + username);

		for (GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("Rol: " + rol.getAuthority());
		}

		if (sesion.getAttribute("usuario") == null) {
			Usuario u = isu.buscarPorUserName(username);
			u.setPassword(null);
			System.out.println("Usuario encontrado: " + u);
			sesion.setAttribute("usuario", u);
		}

		return "redirect:/";
	}

	@ModelAttribute
	public void setGenericos(Model model) {

		Vacante vacanteSearch = new Vacante();

		vacanteSearch.reset();

		model.addAttribute("categoria", isc.buscarTodas());

		model.addAttribute("vacante", serviceVacantes.buscarDestacadas());

		model.addAttribute("search", vacanteSearch);

	}

	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando por: " + vacante);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion",
				ExampleMatcher.GenericPropertyMatchers.contains());
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacante", lista);
		return "home";
	}

	// InitBinder para que el Data Binder settee los String vacios como nulos

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@GetMapping("/bcryp/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " Encriptado en Bcryp: " + passwordEncoder.encode(texto);

	}

	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

		logoutHandler.logout(request, null, null);

		return "redirect:/login";
	}

	@GetMapping("/signup")
	public String registrarUsuario(Usuario usuario) {
		return "/usuarios/formRegistro";
	}

	@PostMapping("/signup")
	public String guardarUsuario(Usuario usuario, BindingResult result, RedirectAttributes attr,
			@RequestParam("username") String username, @RequestParam("email") String email, Model model) {

		if (ExisteUsername(username)) {
			attr.addFlashAttribute("msj", "El nombre de usuario ya Existe");
			return "redirect:/signup";
		} else if (ExisteEmail(email)) {
			attr.addFlashAttribute("msj", "El correo electronico ya Existe");
			return "redirect:/signup";
		}

		String pwplano = usuario.getPassword();
		String pwEnc = passwordEncoder.encode(pwplano);
		usuario.setPassword(pwEnc);
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregar(perfil);
		isu.guardar(usuario);
		attr.addFlashAttribute("msj", "Usuario registrado correctamete!");
		return "redirect:/usuarios/index";
	}

//	METODOS PARA EVITAR REGISTRAR UN USUARIO CON CORREO O NOMBRE DE USUARIO YA REGISTRADO
	private boolean ExisteUsername(String username) {
		List<Usuario> lista = isu.buscarTodos();
		for (Usuario u : lista) {
			if ((u.getUsername()).equals(username)) {
				return true;
			}
		}
		return false;
	}

	private boolean ExisteEmail(String email) {
		List<Usuario> lista = isu.buscarTodos();
		for (Usuario u : lista) {
			if ((u.getEmail()).equals(email)) {
				return true;
			}
		}
		return false;
	}

}
