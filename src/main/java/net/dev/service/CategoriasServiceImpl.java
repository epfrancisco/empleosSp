package net.dev.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.dev.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService{
	
	List<Categoria> lista= null;
	
	public CategoriasServiceImpl() {
		lista= new LinkedList<Categoria>();
		
		Categoria c1 = new Categoria();
		c1.setId(1);
		c1.setNombre("Contabilidad");
		c1.setDescripcion("Trabajos de Contabilidad");
		
		Categoria c2 = new Categoria();
		c2.setId(2);
		c2.setNombre("Ventas");
		c2.setDescripcion("Trabajos de Ventas");
		
		Categoria c3 = new Categoria();
		c3.setId(3);
		c3.setNombre("Comunicaciones");
		c3.setDescripcion("Trabajos de Comunicaciones");
		
		Categoria c4 = new Categoria();
		c4.setId(4);
		c4.setNombre("Arquitectura");
		c4.setDescripcion("Trabajos de Arquitectura");
		
		Categoria c5 = new Categoria();
		c5.setId(5);
		c5.setNombre("Educacion");
		c5.setDescripcion("Trabajos de Educacion");
		
		lista.add(c1);
		lista.add(c2);
		lista.add(c3);
		lista.add(c4);
		lista.add(c5);
		
	}	

	
	public void guardar(Categoria categoria) {
		
		lista.add(categoria);
		
	}

	
	public List<Categoria> buscarTodas() {
		
		return lista;
	}

	
	public Categoria buscarPorId(Integer idCategoria) {
		for (Categoria categoria : lista) {
			if(categoria.getId()==idCategoria);
			return categoria;
		}
		return null;
	}


	public void eliminar(Integer id) {
		
		
		
	}


	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
