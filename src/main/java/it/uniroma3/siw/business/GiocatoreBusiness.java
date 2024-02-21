package it.uniroma3.siw.business;

import it.uniroma3.siw.data.GiocatoreData;
import it.uniroma3.siw.data.PresidenteData;
import it.uniroma3.siw.data.UserData;
import it.uniroma3.siw.mapper.GiocatoreMapper;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.SquadraService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Slf4j
public class GiocatoreBusiness {
    @Autowired
    private GiocatoreMapper giocatoreMapper;

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private SquadraService squadraService;

    String giocatoriLiberi(Model model){
        List<Giocatore> giocatori = giocatoreService.findAll();

        giocatori = giocatori.stream().filter(val -> val.getSquadra()==null)
                .collect(Collectors.toList());

        model.addAttribute("giocatori_liberi", giocatoreMapper.fromGiocatore(giocatori));

        return "squadraModifica.html";

    }

    String giocatoriSquadra(Model model, Long idSquadra){
        List<GiocatoreData> giocatoriData = giocatoreMapper.fromGiocatore(giocatoreService.findGiocatoreBySquadra(squadraService.findSquadraById(idSquadra)));

        model.addAttribute("giocatori_liberi", giocatoriData);

        return "squadraModifica.html";

    }

    public String giocatore(Model model, Long idGiocatore){
        GiocatoreData giocatoriData = giocatoreMapper.fromGiocatore(giocatoreService.findGiocatoreById(idGiocatore));

        model.addAttribute("giocatore", giocatoriData);

        return "giocatore.html";
    }

    public String deleteGiocatore(Model model, Long idGiocatore){
        giocatoreService.deleteGiocatore(giocatoreService.findGiocatoreById(idGiocatore));

        model.addAttribute("messaggio", "giocatore eliminato correttamente");

        return giocatori(model);
    }

    public String saveGiocatore(Model model, GiocatoreData giocatoreData){
        return giocatore(model, giocatoreService.saveGiocatore(giocatoreMapper.fromGiocatoreData(giocatoreData)).getId());

    }


    public String giocatori(Model model){
        List<GiocatoreData> giocatoriData = giocatoreMapper.fromGiocatore(giocatoreService.findAll());

        model.addAttribute("giocatori", giocatoriData);

        return "giocatori.html";

    }

    public String showRegisterForm (Model model) {
        model.addAttribute("giocatore", new GiocatoreData());

        return "admin/formNewGiocatore.html";
    }


}
