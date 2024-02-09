package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {
public List<Giocatore> findByNome(String nome);
	
public boolean existsByNomeAndCognome(String nome , String cognome);

public List<Giocatore> findBySquadra(Squadra squadra);
	
}
