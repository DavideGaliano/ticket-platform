package org.lessons.java.controller;

import org.lessons.java.model.Ticket;
import org.lessons.java.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    // API per visualizzare l'elenco di tutti i ticket
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    // API per filtrare i ticket per categoria
    @GetMapping("/filterByCategory")
    public List<Ticket> getTicketsByCategory(@RequestParam("category") String category) {
        return ticketService.findByCategoria(category);
    }

    // API per filtrare i ticket per stato
    @GetMapping("/filterByStatus")
    public List<Ticket> getTicketsByStatus(@RequestParam("status") Ticket.TicketStatus status) {
        return ticketService.findByStato(status);
    }
}
