package org.lessons.java.repo;


import java.util.List;

import org.lessons.java.model.Ticket;
import org.lessons.java.model.Ticket.TicketStatus;
import org.lessons.java.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	List<Ticket> findByTitoloContainingIgnoreCase(String titolo);
	
	List<Ticket> findByOperatore(Utente operatore);
	
	// Trova i ticket assegnati a un operatore con stato "In corso" o "Da fare"
    List<Ticket> findByOperatoreIdAndStatoIn(Long operatoreId, List<Ticket.TicketStatus> stati);
	
 // Trova tutti i ticket con una determinata categoria
    List<Ticket> findByCategoria_Nome(String nomeCategoria);

    // Trova tutti i ticket con uno stato specifico
    List<Ticket> findByStato(TicketStatus stato);
}