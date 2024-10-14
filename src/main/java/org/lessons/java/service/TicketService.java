package org.lessons.java.service;

import java.util.List;


import org.lessons.java.model.Ticket;
import org.lessons.java.model.Utente;
import org.lessons.java.repo.TicketRepository;
import org.lessons.java.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}
	
	public Ticket findById(Long id) {
		return ticketRepository.findById(id).get();
	}
	
	public List<Ticket> findByTitolo(String titolo) {
		return ticketRepository.findByTitoloContainingIgnoreCase(titolo);
	}
	
	public Ticket save(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	
	public void deleteById(Long id) {
		ticketRepository.deleteById(id);
	}
	
	 public Utente findUserByUsername(String username) {
	        return utenteRepository.findByUsername(username);
	    }
	 
	 public List<Ticket> getTicketsByOperatore(Utente user) {
	        return ticketRepository.findByOperatore(user);
	    }
	 
	// Trova i ticket assegnati a un operatore con stato "In corso" o "Da fare"
	    public List<Ticket> findTicketsByOperatoreAndStato(Long operatoreId) {
	        return ticketRepository.findByOperatoreIdAndStatoIn(operatoreId, List.of(Ticket.TicketStatus.IN_CORSO, Ticket.TicketStatus.DA_FARE));
	    }
	    
	    // Filtra i ticket per categoria
	    public List<Ticket> findByCategoria(String categoria) {
	        return ticketRepository.findByCategoria_Nome(categoria);
	    }

	    // Filtra i ticket per stato
	    public List<Ticket> findByStato(Ticket.TicketStatus stato) {
	        return ticketRepository.findByStato(stato);
	    }

}
