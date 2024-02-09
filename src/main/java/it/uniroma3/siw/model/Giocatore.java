package it.uniroma3.siw.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class Giocatore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
	@NotNull()
	@NotBlank
	private String nome;
	
	@NotNull()
	@NotBlank
	private String cognome;

	private String luogoNascita;
	  

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;

	@ManyToOne
	private Squadra squadra;

	
}
