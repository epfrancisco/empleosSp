package net.dev.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.dev.model.Vacante;
import net.dev.repository.VacantesRepository;
import net.dev.service.IVacantesService;

@Service
@Primary
public class VacantesServiceJpa implements IVacantesService {

	@Autowired
	private VacantesRepository vr;
	
	public List<Vacante> buscarTodas() {
		
		return vr.findAll();
	}

	
	public Vacante buscarPorId(Integer id) {
		Optional<Vacante> op=  vr.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}

	
	public void guardar(Vacante vacante) {
		vr.save(vacante);

	}


	
	public List<Vacante> buscarDestacadas() {
		return vr.findByDestacadoAndEstatusOrderByIdDesc(1, "APROBADA");
	}



	public void eliminar(Integer id) {
		vr.deleteById(id);
		
	}


	
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		
		return vr.findAll(example);
	}



	public Page<Vacante> buscarTodas(Pageable page) {
		
		return vr.findAll(page);
	}

}
