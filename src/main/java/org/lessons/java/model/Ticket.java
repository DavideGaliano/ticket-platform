package org.lessons.java.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
	@NotEmpty
	@Size(min=2, max=255)
    private String titolo;
    
    @NotNull
	@Size(min=2,max=1000)
	@Column(length=1000)
    private String descrizione;
    private LocalDateTime dataCreazione;
    
    @Enumerated(EnumType.STRING)
    private TicketStatus stato; // 'Da fare', 'In corso', 'Completato'
    
 // Definizione dell'enum
    public enum TicketStatus {
        DA_FARE("Da fare"),
        IN_CORSO("In corso"),
        COMPLETATO("Completato");

        private final String displayValue;

        TicketStatus(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    @ManyToOne
    @JoinColumn(name = "id_operatore")
    @JsonManagedReference
    private Utente operatore;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    @JsonManagedReference
    private Categoria categoria;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Nota> note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Utente getOperatore() {
		return operatore;
	}

	public void setOperatore(Utente operatore) {
		this.operatore = operatore;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Nota> getNote() {
		return note;
	}

	public void setNote(List<Nota> note) {
		this.note = note;
	}

	public TicketStatus getStato() {
		return stato;
	}

	public void setStato(TicketStatus stato) {
		this.stato = stato;
	}

    
}
