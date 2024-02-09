package it.uniroma3.siw.service.impl;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.service.GiocatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GiocatoreServiceImpl implements GiocatoreService {

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    @Override
    public Giocatore saveGiocatore(Giocatore giocatore) {
        return giocatoreRepository.save(giocatore);
    }

    @Override
    public void deleteGiocatore(Giocatore giocatore) {
        giocatoreRepository.delete(giocatore);

    }

    @Override
    public Giocatore findGiocatoreById(Long id) {
        return giocatoreRepository.findById(id).orElse(null);
    }

    @Override
    public List<Giocatore> findGiocatoreBySquadra(Squadra squadra) {
        return giocatoreRepository.findBySquadra(squadra);
    }

    @Override
    public List<Giocatore> findAll() {
        return (List<Giocatore>) giocatoreRepository.findAll();
    }
}
