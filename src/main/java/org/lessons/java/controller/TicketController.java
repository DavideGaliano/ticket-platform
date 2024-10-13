package org.lessons.java.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.lessons.java.model.Nota;
import org.lessons.java.model.Ticket;
import org.lessons.java.model.Utente;
import org.lessons.java.service.NotaService;
import org.lessons.java.service.TicketService;
import org.lessons.java.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketService ticketService;

	@Autowired
	UtenteService utenteService;

	@Autowired
	NotaService notaService;

	// READ

	@GetMapping("/admin/dashboard")
	public String adminIndex(@RequestParam(name = "search", required = false) String search, Model model) {
		List<Ticket> tickets;
		if (search != null && !search.isEmpty()) {
			tickets = ticketService.findByTitolo(search); // recupera il ticket dal titolo
		} else {
			tickets = ticketService.findAll(); // recupera tutti i ticket
		}

		model.addAttribute("tickets", tickets);
		model.addAttribute("search", search); // mantiene il valore di ricerca nel modello

		return "tickets/admin/dashboard";
	}

	@GetMapping("/admin/{id}")
	public String showAdmin(@PathVariable("id") Long id, Model model) {
		Ticket ticket = ticketService.findById(id);
		model.addAttribute("ticket", ticket);
		model.addAttribute("operatore", ticket.getOperatore());
		model.addAttribute("note", ticket.getNote());
		return "tickets/admin/show";
	}

	@GetMapping("/operators/dashboard")
	public String operatorDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		// identifico l'user
		Utente user = utenteService.findByUsername(userDetails.getUsername());
		List<Ticket> tickets = ticketService.getTicketsByOperatore(user);
		model.addAttribute("tickets", tickets);
		model.addAttribute("operatore", user);
		return "tickets/operators/dashboard";
	}

	@GetMapping("/operators/{id}")
	public String showOperators(@PathVariable("id") Long id, Model model) {
		Ticket ticket = ticketService.findById(id);
		model.addAttribute("ticket", ticket);
		model.addAttribute("operatore", ticket.getOperatore());
		model.addAttribute("note", ticket.getNote());
		return "tickets/operators/show";
	}

	// CREATE

	@GetMapping("/admin/create")
	public String showCreateForm(Model model) {

		List<Utente> availableOperators = utenteService.findAvailableOperators();

		model.addAttribute("ticket", new Ticket());
		// Passa l'enum TicketStatus come attributo
		model.addAttribute("ticketStatuses", Ticket.TicketStatus.values());
		model.addAttribute("operators", availableOperators);
		return "tickets/admin/create";
	}

	@PostMapping("/admin/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/tickets/admin/create";
		}

		// Imposta la data di creazione
		formTicket.setDataCreazione(LocalDateTime.now());

		ticketService.save(formTicket);

		return "redirect:/tickets/admin/dashboard";
	}

	@GetMapping("/admin/nota/{id}")
	public String showNotaForm(@PathVariable("id") Long id, Model model) {
		Ticket ticket = ticketService.findById(id);

		model.addAttribute("ticket", ticket);
		model.addAttribute("nota", new Nota());

		return "tickets/admin/nota";
	}
	
	@GetMapping("/operators/nota/{id}")
	public String showNotaFormOperators(@PathVariable("id") Long id, Model model) {
		Ticket ticket = ticketService.findById(id);

		model.addAttribute("ticket", ticket);
		model.addAttribute("nota", new Nota());

		return "tickets/operators/nota";
	}

	@PostMapping("/admin/nota/{id}")
	public String addNote(@PathVariable("id") Long id, @ModelAttribute("nota") Nota nota,
			@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes attributes, Model model) {

		// Recupera l'utente loggato

		Utente autore = utenteService.findByUsername(userDetails.getUsername());

		// Recupera il ticket associato
		Ticket ticket = ticketService.findById(id);

		// Imposta il ticket e l'autore
		nota.setTicket(ticket);
		nota.setAutore(autore);
		nota.setDataCreazione(LocalDateTime.now());

		// Forza la creazione di una nuova nota, impostando l'ID a null
		nota.setId(null);

		// Salva la nota
		notaService.save(nota);
		
		attributes.addFlashAttribute("successMessage", "Nota del ticket " + ticket.getId() + " è stata creata");

		return "redirect:/tickets/admin/dashboard";
	}
	
	@PostMapping("/operators/nota/{id}")
	public String addNoteOperators(@PathVariable("id") Long id, @ModelAttribute("nota") Nota nota,
			@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes attributes, Model model) {

		// Recupera l'utente loggato

		Utente autore = utenteService.findByUsername(userDetails.getUsername());

		// Recupera il ticket associato
		Ticket ticket = ticketService.findById(id);

		// Imposta il ticket e l'autore
		nota.setTicket(ticket);
		nota.setAutore(autore);
		nota.setDataCreazione(LocalDateTime.now());

		// Forza la creazione di una nuova nota, impostando l'ID a null
		nota.setId(null);

		// Salva la nota
		notaService.save(nota);
		
		attributes.addFlashAttribute("successMessage", "Nota del ticket " + ticket.getId() + " è stata creata");

		return "redirect:/tickets/operators/dashboard";
	}

	// UPDATE

	@GetMapping("/admin/edit/{id}")
	public String editTicket(@PathVariable("id") Long id, Model model) {
		Ticket ticket = ticketService.findById(id);

		// Recupera gli utenti con ruolo Operatore e disponibili (statoDisponibilita =
		// true)
		List<Utente> availableOperators = utenteService.findAvailableOperators();

		model.addAttribute("ticket", ticket);
		model.addAttribute("operators", availableOperators); // Passa gli utenti con ruolo Operatore disponibili
		return "tickets/admin/edit";
	}

	@PostMapping("/admin/edit/{id}")
	public String updateTicket(@PathVariable("id") Long id, @ModelAttribute("ticket") Ticket updatedTicket,
			Model model) {
		// Trova il ticket esistente
		Ticket existingTicket = ticketService.findById(id);

		if (existingTicket == null) {
			model.addAttribute("errorMessage", "Ticket non trovato");
			return "tickets/admin/dashboard";
		}

		// Trova l'operatore selezionato e aggiorna il ticket
		Utente selectedOperator = utenteService.findById(updatedTicket.getOperatore().getId());

		if (selectedOperator == null || !selectedOperator.isStatoDisponibilita()) {
			model.addAttribute("errorMessage", "Operatore non valido o non disponibile");
			return "tickets/admin/edit/{id}";
		}

		// Aggiorna i campi del ticket
		existingTicket.setTitolo(updatedTicket.getTitolo());
		existingTicket.setDescrizione(updatedTicket.getDescrizione());
		existingTicket.setOperatore(selectedOperator); // Imposta il nuovo operatore

		// Salva il ticket aggiornato
		ticketService.save(existingTicket);

		// Redireziona alla dashboard
		return "redirect:/tickets/admin/dashboard";
	}
	
	// Mostra il form per modificare i dati dell'operatore
    @GetMapping("/operators/edit")
    public String showEditForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Recupera l'operatore loggato
        Utente operatore = utenteService.findByUsername(userDetails.getUsername());

        model.addAttribute("operatore", operatore);
        return "tickets/operators/edit";
    }
    
 // Gestisce il salvataggio dei dati aggiornati dell'operatore
    @PostMapping("/operators/edit")
    public String updateOperator(@ModelAttribute("operatore") Utente updatedOperatore,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        // Recupera l'operatore loggato
        Utente operatore = utenteService.findByUsername(userDetails.getUsername());

        // Recupera i ticket assegnati all'operatore che sono in stato "In corso" o "Da fare"
        List<Ticket> activeTickets = ticketService.findTicketsByOperatoreAndStato(operatore.getId());

        // Se ci sono ticket in stato "In corso" o "Da fare", l'operatore non può impostare lo statoDisponibilita su false
        if (!activeTickets.isEmpty() && updatedOperatore.isStatoDisponibilita() == false) {
            model.addAttribute("errorMessage", "Non puoi impostare lo stato a 'Non disponibile' finché hai ticket in corso o da fare.");
            return "tickets/operators/edit";
        }

        // Aggiorna i dati dell'operatore (eccetto lo username)
        operatore.setNome(updatedOperatore.getNome());
        operatore.setEmail(updatedOperatore.getEmail());
        operatore.setStatoDisponibilita(updatedOperatore.isStatoDisponibilita());

        // Salva i dati aggiornati
        utenteService.save(operatore);

        return "redirect:/tickets/operators/dashboard";
    }
    
    // Mostra il form per modificare lo stato di un ticket
    @GetMapping("/operators/stato/{id}")
    public String showEditTicketForm(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     Model model) {
        Utente operatore = utenteService.findByUsername(userDetails.getUsername());
        Ticket ticket = ticketService.findById(id);

        // Verifica che il ticket appartenga all'operatore loggato
        if (!ticket.getOperatore().getId().equals(operatore.getId())) {
            return "error/403";  // Se il ticket non è assegnato a questo operatore, ritorna un errore
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("ticketStatuses", Ticket.TicketStatus.values());  // Passa gli stati del ticket
        return "tickets/operators/stato";
    }
    
 // Gestisce l'aggiornamento dello stato del ticket
    @PostMapping("/operators/stato/{id}")
    public String updateTicketStatus(@PathVariable("id") Long id,
                                     @ModelAttribute("ticket") Ticket updatedTicket,
                                     @AuthenticationPrincipal UserDetails userDetails,RedirectAttributes attributes,
                                     Model model) {
        Utente operatore = utenteService.findByUsername(userDetails.getUsername());
        Ticket ticket = ticketService.findById(id);

        // Verifica che il ticket appartenga all'operatore loggato
        if (!ticket.getOperatore().getId().equals(operatore.getId())) {
            return "error/403";  // Se il ticket non è assegnato a questo operatore, ritorna un errore
        }

        // Aggiorna lo stato del ticket
        ticket.setStato(updatedTicket.getStato());

        // Salva il ticket aggiornato
        ticketService.save(ticket);
        
        attributes.addFlashAttribute("successMessage", "Ticket con id " + id + " ha modificato lo stato");

        return "redirect:/tickets/operators/dashboard";
    }

	// DELETE

	@PostMapping("/admin/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		ticketService.deleteById(id);

		attributes.addFlashAttribute("successMessage", "Ticket con id " + id + " è stata eliminato");

		return "redirect:/tickets/admin/dashboard";
	}

}
