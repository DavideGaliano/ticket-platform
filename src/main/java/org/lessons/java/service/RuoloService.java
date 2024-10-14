package org.lessons.java.service;

import java.util.List;

import org.lessons.java.model.Ruolo;
import org.lessons.java.repo.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {

    @Autowired
    private RuoloRepository ruoloRepository;

    // Trova un ruolo per nome
    public Ruolo findByName(String name) {
        return ruoloRepository.findByName(name);
    }
    
 // Restituisce tutti i ruoli
    public List<Ruolo> findAll() {
        return ruoloRepository.findAll();
    }
}
