package it.uniroma3.siw.service.impl;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.repository.PresidenteRepository;
import it.uniroma3.siw.service.PresidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PresidenteServiceImpl implements PresidenteService {
    @Autowired
    private PresidenteRepository presidenteRepository;


    @Override
    public Presidente savePresidente(Presidente presidente) {
        return presidenteRepository.save(presidente);
    }

    @Override
    public void deletePresidente(Presidente presidente) {
        presidenteRepository.delete(presidente);
    }

    @Override
    public Presidente findPresidenteById(Long id) {
        return presidenteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Presidente> findAllPresidente() {
        return (List<Presidente>) presidenteRepository.findAll();
    }
}
