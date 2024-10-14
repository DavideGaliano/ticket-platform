package org.lessons.java.service;

import java.util.List;

import org.lessons.java.model.Utente;
import org.lessons.java.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    
    
    public Utente findById(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }
    
    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    // Metodo per trovare gli utenti con ruolo Operatore e con statoDisponibilita true
    public List<Utente> findAvailableOperators() {
        return utenteRepository.findAvailableOperators();
    }
    
    public Utente save(Utente utente) {
    	return utenteRepository.save(utente);
    }
    
 // Verifica se un username esiste già
    public boolean existsByUsername(String username) {
        return utenteRepository.existsByUsername(username);
    }

    // Verifica se un'email esiste già
    public boolean existsByEmail(String email) {
        return utenteRepository.existsByEmail(email);
    }
}
