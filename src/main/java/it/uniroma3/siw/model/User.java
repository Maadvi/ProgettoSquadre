package it.uniroma3.siw.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
@Data
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotNull()
	@NotBlank
	private String name;

	@NotNull()
	@NotBlank
	private String surname;
	
	@NotNull()
	@NotBlank
	private String email;

	private Boolean flgPresidente;

	@OneToOne
	private Presidente presidente;


}