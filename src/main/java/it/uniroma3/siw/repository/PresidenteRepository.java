package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Presidente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresidenteRepository extends CrudRepository<Presidente, Long> {
    public List<Presidente> findByNome(String nome);

    public boolean existsByNomeAndCognome(String nome , String cognome);


}