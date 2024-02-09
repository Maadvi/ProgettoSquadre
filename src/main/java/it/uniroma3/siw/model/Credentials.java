package it.uniroma3.siw.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Credentials {

	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String PRESIDENTE = "PRESIDENTE";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotNull()
	@NotBlank
	private String username;
	
	@NotNull()
	@NotBlank
	private String password;
	
	private String role;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

}