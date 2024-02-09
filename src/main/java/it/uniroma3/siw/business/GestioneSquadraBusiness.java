package it.uniroma3.siw.business;

import it.uniroma3.siw.data.GiocatoreData;
import it.uniroma3.siw.data.PresidenteData;
import it.uniroma3.siw.data.SquadraData;
import it.uniroma3.siw.mapper.GiocatoreMapper;
import it.uniroma3.siw.mapper.PresidenteMapper;
import it.uniroma3.siw.mapper.SquadraMapper;
import it.uniroma3.siw.mapper.UserMapper;
import it.uniroma3.siw.model.*;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.PresidenteService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Slf4j
public class GestioneSquadraBusiness {

    @Autowired
    private SquadraService squadraService;

    @Autowired
    private GiocatoreMapper giocatoreMapper;

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private PresidenteMapper presidenteMapper;

    @Autowired
    private SquadraMapper squadraMapper;

    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public String showSquadra(Model model, Long idSquadra){
        Squadra squadra = squadraService.findSquadraById(idSquadra);
        model.addAttribute("squadra", squadraMapper.fromSquadra(squadra));
        return "squadra.html";
    }
    public String showRegisterForm (Model model) {
        List<User> users = userService.getAllUsers();
        users = users.stream().filter(t->t.getFlgPresidente().equals(true) && t.getPresidente()==null).toList();

        List<Squadra> squadre = squadraService.findAll();

        List<Presidente> presidenti = presidenteService.findAllPresidente();

        //i presidenti liberi senza squadra
        presidenti = presidenti.stream().filter(t->squadraService.findByPresidente(t)==null || squadraService.findByPresidente(t).getId()==null ).toList();


        model.addAttribute("presidenti", presidenteMapper.fromPresidente(presidenti));

        model.addAttribute("squadraData", new SquadraData());
        return "admin/formNewSquadra.html";
    }

    public String saveSquadra (Model model, SquadraData squadraData) {
        Long id;
        try{
            id = squadraService.saveSquadra(squadraMapper.fromSquadraData(squadraData)).getId();
        }catch (Exception e){
            log.error(ExceptionUtils.getMessage(e));
            model.addAttribute("errore" , "errore nel salvatggio della squadra");
            return "admin/formNewSquadra.html";


        }
        //todo qui non setta il presidente dall apaginaq html della form ti dovrei star passando l'id controlla in debug che cazzo vuole
        return "admin/formNewSquadra.html"/*showSquadra(model, id) */;
    }



    public String consultazione(Model model){
        model.addAttribute("squadre" , squadraMapper.fromSquadra(squadraService.findAll()));

        return "consultazioneSquadre.html";

    }

    public String inseritmentoSquadra(Model model, SquadraData squadraData, PresidenteData presidenteData, Long idUserPres){

        try{
            Squadra squadra =  squadraMapper.fromSquadraData(squadraData);

            Presidente presidente = presidenteMapper.fromPresidenteData(presidenteData);
            User user = userService.getUser(idUserPres);
            presidente.setUser(user);
            presidenteService.savePresidente(presidente);
            user.setPresidente(presidente);
            squadra.setPresidente(presidente);
            squadraService.saveSquadra(squadra);

            model.addAttribute("squadra" , squadraMapper.fromSquadra(squadra) );

            model.addAttribute("messaggio" , "Squadra inserita correttamente" );

            return showSquadra(model, squadra.getId());

        }  catch (Exception e){
            model.addAttribute("messaggio", "Errore nell'inserimento della squadraData");

            log.error(ExceptionUtils.getMessage(e));


            return "formSquadra.html";
        }


    }

    public String aggiornamentoSquadra(Model model, SquadraData squadra, PresidenteData presidenteData){
        try{
            Squadra squadra1 =  squadraMapper.fromSquadraData(squadra);

            if(presidenteData != null){
                Presidente presidente = presidenteMapper.fromPresidenteData(presidenteData);
                presidenteService.savePresidente(presidente);

                if(squadra1.getPresidente()!=null)
                    presidenteService.deletePresidente(squadra1.getPresidente());

                squadra1.setPresidente(presidente);

                model.addAttribute("update_presidente" , "Presidente della squadra aggiornato correttamente" );


            }

            squadraService.saveSquadra(squadra1);

            model.addAttribute("squadra" , squadraMapper.fromSquadra(squadra1) );

            model.addAttribute("messaggio" , "Squadra aggiornata correttamente" );

            return showSquadra(model, squadra1.getId());

        }  catch (Exception e){
            model.addAttribute("messaggio", "Errore nell'aggiornamento della squadra");

            log.error(ExceptionUtils.getMessage(e));

            return "formSquadra.html";
        }


    }

    public String addGiocatore(Model model, SquadraData squadraData, GiocatoreData giocatoreData){
        try {
            Squadra squadra1 = squadraMapper.fromSquadraData(squadraData);

            Giocatore giocatore = giocatoreMapper.fromGiocatoreData(giocatoreData);
            giocatore.setSquadra(squadra1);
            giocatoreService.saveGiocatore(giocatore);

            List<Giocatore> tesserati = squadra1.getTesserati();
            tesserati.add(giocatore);
            squadra1.setTesserati(tesserati);
            squadraService.saveSquadra(squadra1);

            model.addAttribute("squadra", squadraMapper.fromSquadra(squadra1));

            model.addAttribute("giocatore", giocatoreMapper.fromGiocatore(giocatore));

            model.addAttribute("messaggio", "Giocatore aggiunto alla squadra correttamente");

            return "aggiungiGiocatore.html";
        } catch (Exception e){
            model.addAttribute("messaggio", "Errore nell'inserimento del giocatore");

            log.error(ExceptionUtils.getMessage(e));


            return "aggiungiGiocatore.html";
        }

    }

    public String removeGiocatore(Model model, Long idGiocatore, Long idSquadra){
        try {
            Squadra squadra = squadraService.findSquadraById(idSquadra);
            Giocatore giocatore =giocatoreService.findGiocatoreById(idGiocatore);

            List<Giocatore> tesserati = squadra.getTesserati();
            tesserati.remove(giocatore);
            squadra.setTesserati(tesserati);
            squadraService.saveSquadra(squadra);


            model.addAttribute("messaggio", "Giocatore rimosso alla squadra correttamente");

            return "rimuoviGiocatore.html";

        } catch (Exception e){
            model.addAttribute("messaggio", "Errore nell'inserimento del giocatore");

            log.error(ExceptionUtils.getMessage(e));


            return "rimuoviGiocatore.html";
        }

    }

}
