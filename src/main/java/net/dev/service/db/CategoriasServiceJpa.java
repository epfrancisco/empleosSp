package net.dev.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.dev.model.Categoria;
import net.dev.repository.CategoriasRepository;
import net.dev.service.ICategoriasService;

@Service
@Primary
public class CategoriasServiceJpa implements ICategoriasService {

	@Autowired
	private CategoriasRepository cr;
	
	public void guardar(Categoria categoria) {
		cr.save(categoria);

	}

	
	public List<Categoria> buscarTodas() {
		
		return cr.findAll();
	}

	
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> op=  cr.findById(idCategoria);
		if(op.isPresent()) {
			return op.get();
		}
		
		
		return null;
		
		
		
		
	}


	
	public void eliminar(int id) {
		
		cr.deleteById(id);
		
	}


	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}


	
	public Page<Categoria> buscarTodas(Pageable page) {
		
		return cr.findAll(page);
	}

}
