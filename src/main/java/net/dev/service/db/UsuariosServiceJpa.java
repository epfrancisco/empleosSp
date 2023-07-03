package net.dev.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.dev.model.Usuario;
import net.dev.repository.UsuariosRepository;
import net.dev.service.IUsuariosService;

@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService {
	
	@Autowired
	private UsuariosRepository ur;


	public void guardar(Usuario usuario) {
		ur.save(usuario);

	}

	
	public void eliminar(int id) {
		ur.deleteById(id);

	}


	public List<Usuario> buscarTodos() {
		List<Usuario> lista=ur.findAll();
		return lista;
	}


	
	public Usuario buscarPorUserName(String username) {
		
		return ur.findByUsername(username);
	}

}
