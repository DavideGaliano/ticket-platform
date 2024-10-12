package org.lessons.java.service;

import java.util.List;

import org.lessons.java.model.Categoria;
import org.lessons.java.repo.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public void deleteById(Long id) {
		categoriaRepository.deleteById(id);
	}

}
