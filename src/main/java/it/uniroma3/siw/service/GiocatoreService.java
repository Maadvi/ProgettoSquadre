package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;

import java.util.List;

public interface GiocatoreService {
    Giocatore saveGiocatore(Giocatore giocatore);
    void deleteGiocatore(Giocatore giocatore);

    Giocatore findGiocatoreById(Long id);

    List<Giocatore> findGiocatoreBySquadra(Squadra squadra);

    List<Giocatore> findAll();


}
