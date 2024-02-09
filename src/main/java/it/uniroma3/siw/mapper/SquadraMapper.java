package it.uniroma3.siw.mapper;

import it.uniroma3.siw.data.SquadraData;
import it.uniroma3.siw.model.Squadra;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SquadraMapper {

    Squadra fromSquadraData(SquadraData squadraData);

    SquadraData fromSquadra(Squadra squadra);

    List<SquadraData> fromSquadra(List<Squadra> squadra);
}
