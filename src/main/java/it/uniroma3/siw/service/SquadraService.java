package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;

import java.util.List;

public interface SquadraService {
    Squadra saveSquadra(Squadra squadra);

    void deleteSquadra(Squadra squadra);

    Squadra findSquadraById(Long id);

    Squadra findByPresidente(Presidente presidente);

    List<Squadra> findAll();
}
