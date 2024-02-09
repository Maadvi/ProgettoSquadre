package it.uniroma3.siw.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class Presidente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull()
    @NotBlank
    private String nome;

    @NotNull()
    @NotBlank
    private String cognome;

    private String codiceFiscale;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascita;

    private String luogoNascita;

    @OneToOne
    private User user;



}
