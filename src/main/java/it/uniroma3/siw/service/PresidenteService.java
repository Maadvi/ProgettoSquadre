package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Presidente;

import java.util.List;


public interface PresidenteService {

    Presidente savePresidente(Presidente presidente);
    void deletePresidente(Presidente presidente);

    Presidente findPresidenteById(Long id);

    List<Presidente> findAllPresidente();
}
