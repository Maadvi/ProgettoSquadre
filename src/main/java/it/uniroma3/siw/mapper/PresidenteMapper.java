package it.uniroma3.siw.mapper;

import it.uniroma3.siw.data.PresidenteData;
import it.uniroma3.siw.model.Presidente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PresidenteMapper {

    PresidenteData fromPresidente(Presidente Presidente);

    List<PresidenteData> fromPresidente(List<Presidente> Presidente);

    Presidente fromPresidenteData(PresidenteData Presidente);

    List<Presidente> fromPresidenteData(List<PresidenteData> Presidente);
}
