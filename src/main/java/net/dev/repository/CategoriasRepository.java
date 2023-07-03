package net.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.dev.model.Categoria;
//public interface CategoriasRepository extends CrudRepository<Categoria, Integer> 
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	
	

}
