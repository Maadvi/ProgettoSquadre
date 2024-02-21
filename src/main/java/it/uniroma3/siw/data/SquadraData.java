package it.uniroma3.siw.data;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Presidente;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class SquadraData {

    private Long id;

    @NotNull()
    @NotBlank
    private String nome;

    @NotNull()
    @NotBlank
    private String indirizzoSede;

    @NotNull()
    @NotBlank
    private Long annoFondazione;

    private PresidenteData presidente;

    private List<GiocatoreData> tesserati;
}
