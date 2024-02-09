package it.uniroma3.siw.mapper;

import it.uniroma3.siw.data.GiocatoreData;
import it.uniroma3.siw.model.Giocatore;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GiocatoreMapper {
    GiocatoreData fromGiocatore(Giocatore giocatore);

    List<GiocatoreData> fromGiocatore(List<Giocatore> giocatore);

    Giocatore fromGiocatoreData(GiocatoreData giocatore);

    List<Giocatore> fromGiocatoreData(List<GiocatoreData> giocatore);
}
