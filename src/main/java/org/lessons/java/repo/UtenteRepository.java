package org.lessons.java.repo;


import org.lessons.java.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
	Utente findByUsername(String username);
	
}