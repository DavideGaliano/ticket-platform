package org.lessons.java.controller;

import org.lessons.java.model.Utente;
import org.lessons.java.model.Ruolo;
import org.lessons.java.service.UtenteService;
import org.lessons.java.service.RuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostra il form di registrazione
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utente", new Utente());

        // Aggiungi tutti i ruoli disponibili al modello per il form
        List<Ruolo> allRoles = ruoloService.findAll();
        model.addAttribute("allRoles", allRoles);

        return "users/create";
    }

    // Gestisce la registrazione di un nuovo utente
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("utente") Utente utente, BindingResult result,RedirectAttributes attributes, Model model) {

        // Verifica se l'username o l'email esistono già
        if (utenteService.existsByUsername(utente.getUsername()) || utenteService.existsByEmail(utente.getEmail())) {
            model.addAttribute("errorMessage", "Username o email già esistenti.");
            return "register";
        }

        // Crittografa la password
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        // Salva l'utente
        utenteService.save(utente);
        
        attributes.addFlashAttribute("successMessage", "Utente con username  " + utente.getUsername() + "  è stato registrato");

        return "redirect:/tickets/admin/dashboard"; 
    }
}
