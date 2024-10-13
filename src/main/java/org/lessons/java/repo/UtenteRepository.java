package org.lessons.java.repo;


import java.util.List;

import org.lessons.java.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
	Utente findByUsername(String username);
	
	@Query("SELECT u FROM Utente u JOIN u.roles r WHERE r.name = 'ROLE_OPERATORE' AND u.statoDisponibilita = true")
    List<Utente> findAvailableOperators();
	
}