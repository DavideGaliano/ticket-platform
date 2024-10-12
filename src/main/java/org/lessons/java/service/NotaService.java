package org.lessons.java.service;

import java.util.List;

import org.lessons.java.model.Nota;
import org.lessons.java.repo.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaService {
	
	@Autowired
	private NotaRepository notaRepository;
	
	public List<Nota> findAll() {
		return notaRepository.findAll();
	}
	
	public List<Nota> findAllById(List<Long> id) {
		return notaRepository.findAllById(id);
	}
	
	public Nota save(Nota nota) {
		return notaRepository.save(nota);
	}
	
	public void deleteById(Long id) {
		notaRepository.deleteById(id);
	}

}
