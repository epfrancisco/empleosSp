package net.dev.service;

import java.util.List;

import net.dev.model.Usuario;

public interface IUsuariosService {
	
	void guardar(Usuario usuario);
	
	void eliminar(int id);
	
	List<Usuario> buscarTodos();
	
	Usuario buscarPorUserName(String username);

}
