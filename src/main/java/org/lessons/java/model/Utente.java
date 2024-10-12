package org.lessons.java.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    private String nome;
    private String email;
    private String username;
    private String password;
    

    private boolean statoDisponibilita; // Solo per il ruolo Operatore

    @OneToMany(mappedBy = "operatore")
    private List<Ticket> ticketsAssegnati;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "utenti_ruoli",
        joinColumns = @JoinColumn(name = "id_utente"),
        inverseJoinColumns = @JoinColumn(name = "id_ruolo")
    )
    private Set<Ruolo> roles = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatoDisponibilita() {
		return statoDisponibilita;
	}

	public void setStatoDisponibilita(boolean statoDisponibilita) {
		this.statoDisponibilita = statoDisponibilita;
	}

	public List<Ticket> getTicketsAssegnati() {
		return ticketsAssegnati;
	}

	public void setTicketsAssegnati(List<Ticket> ticketsAssegnati) {
		this.ticketsAssegnati = ticketsAssegnati;
	}

	public Set<Ruolo> getRoles() {
		return roles;
	}

	public void setRoles(Set<Ruolo> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

    
}
