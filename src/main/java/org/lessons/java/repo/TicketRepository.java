package org.lessons.java.repo;


import java.util.List;

import org.lessons.java.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	List<Ticket> findByTitoloContainingIgnoreCase(String titolo);
	
}