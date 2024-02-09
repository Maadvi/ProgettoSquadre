package it.uniroma3.siw.service.impl;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SquadraServiceImpl implements SquadraService {
    @Autowired
    private SquadraRepository squadraRepository;

    @Override
    public Squadra saveSquadra(Squadra squadra) {
        return squadraRepository.save(squadra);
    }

    @Override
    public void deleteSquadra(Squadra squadra) {
        squadraRepository.delete(squadra);
    }

    @Override
    public Squadra findSquadraById(Long id) {
        return squadraRepository.findById(id).orElse(null);
    }

    @Override
    public Squadra findByPresidente(Presidente presidente) {
        return squadraRepository.findByPresidente(presidente);
    }

    @Override
    public List<Squadra> findAll() {
        return (List<Squadra>) squadraRepository.findAll();
    }
}
