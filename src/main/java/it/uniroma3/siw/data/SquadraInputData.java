package it.uniroma3.siw.data;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class SquadraInputData {


    @NotNull()
    @NotBlank
    private String nome;

    @NotNull()
    @NotBlank
    private String indirizzoSede;

    @NotNull()
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate annoFondazione;

    private UserData presidente;


    private String codiceFiscalePresidente;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascita;

    private String luogoNascita;

}
