package it.uniroma3.siw.data;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PresidenteData {
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

    private UserData userData;
}
