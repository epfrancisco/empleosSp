package net.dev.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.dev.model.Vacante;


@Service
public class VacantesServiceImpl implements IVacantesService{
	
	private List<Vacante> lista = null;

	
	public VacantesServiceImpl() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<Vacante>();
		try {
			Vacante va1 = new Vacante();
			va1.setId(1);
			va1.setFecha(sdf.parse("08-02-2019"));
			va1.setNombre("Ingeniero de Comunicaciones");
			va1.setDescripcion("Solicitamos ingeniero de soporte intranet");
			va1.setSalario(20000.0);
			va1.setDestacado(1);
			va1.setImagen("ing.png");

			Vacante va2 = new Vacante();
			va2.setId(2);
			va2.setFecha(sdf.parse("09-02-2019"));
			va2.setNombre("Arquitecto");
			va2.setDescripcion("Solicitamos Arquitecto");
			va2.setSalario(1400.0);
			va2.setDestacado(0);
			va2.setImagen("arq.png");

			Vacante va3 = new Vacante();
			va3.setId(3);
			va3.setFecha(sdf.parse("10-02-2019"));
			va3.setNombre("Hoster");
			va3.setDescripcion("Solicitamos Hoster");
			va3.setSalario(1200.0);
			va3.setDestacado(1);
			
			Vacante va4 = new Vacante();
			va4.setId(4);
			va4.setFecha(sdf.parse("08-02-2019"));
			va4.setNombre("Ingeniero de Sistemas");
			va4.setDescripcion("Solicitamos ingeniero de sistemas");
			va4.setSalario(250000.0);
			va4.setDestacado(1);
			va4.setImagen("ing.png");

			lista.add(va1);
			lista.add(va2);
			lista.add(va3);
			lista.add(va4);
			
		} catch (ParseException e) {
			System.out.print("Error :" + e.getMessage());
		}
	}
	
	public List<Vacante> buscarTodas() {
		
		return lista;
	}

	
	public Vacante buscarPorId(Integer id) {
		for (Vacante v:lista) {
			if(v.getId()==id) {
				return v;
			}
		}
		return null;
		
	}

	
	public void guardar(Vacante vacante) {
		lista.add(vacante);
		
	}

	
	public List<Vacante> buscarDestacadas() {
		
		return null;
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}
