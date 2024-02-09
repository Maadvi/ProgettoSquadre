package it.uniroma3.siw.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Squadra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull()
    @NotBlank
    private String nome;

    @NotNull()
    @NotBlank
    private String indirizzoSede;

    @NotNull()
    //@NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate annoFondazione;

    @ManyToOne
    private Presidente presidente;

    @OneToMany
    private List<Giocatore> Tesserati;

}
