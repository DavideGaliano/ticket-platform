package org.lessons.java.controller;

import java.util.List;

import org.lessons.java.model.Ticket;
import org.lessons.java.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	
	
	//READ
	
	 @GetMapping("/admin/dashboard")
	    public String adminIndex(@RequestParam(name = "search", required = false) String search, Model model) {
	     List<Ticket> tickets;
	     if (search != null && !search.isEmpty()) {
	    	 tickets = ticketService.findByTitolo(search); //recupera il ticket dal titolo
	     } else {
	    	 tickets = ticketService.findAll(); //recupera tutti i ticket
	     }
	     
	     model.addAttribute("tickets", tickets);
	     model.addAttribute("search", search); //mantiene il valore di ricerca nel modello
		 
		 return "tickets/admin/dashboard";
	    }
	 
	 @GetMapping("/admin/{id}")
		public String show(@PathVariable("id") Long id, Model model) {
	        Ticket ticket = ticketService.findById(id);
	        model.addAttribute("ticket", ticket);
	        model.addAttribute("operatore", ticket.getOperatore());
	        model.addAttribute("note", ticket.getNote());
	        return "tickets/admin/show";
	    }

	    @GetMapping("/operators/dashboard")
	    public String operatorDashboard() {
	        return "tickets/operators/dashboard";
	    }

}
