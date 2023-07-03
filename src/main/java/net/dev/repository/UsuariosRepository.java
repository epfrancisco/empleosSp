package net.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.dev.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsername(String username);

}
