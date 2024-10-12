package org.lessons.java.service;

import java.util.List;

import org.lessons.java.model.Ticket;
import org.lessons.java.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
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

}
