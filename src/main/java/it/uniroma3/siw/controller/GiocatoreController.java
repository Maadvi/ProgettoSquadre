package it.uniroma3.siw.controller;

import it.uniroma3.siw.business.GestioneSquadraBusiness;
import it.uniroma3.siw.business.GiocatoreBusiness;
import it.uniroma3.siw.data.GiocatoreData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.MidiSystem;

@Controller
@RequestMapping("/giocatore")
public class GiocatoreController {
    @Autowired
    private GiocatoreBusiness giocatoreBusiness;

    @PostMapping("/presidente/savegiocatore")
    public String saveSquadra(Model model, @ModelAttribute("giocatoreData") GiocatoreData giocatoreData){
        return giocatoreBusiness.saveGiocatore(model, giocatoreData);
    }

    @GetMapping("/presidente/formNewgiocatore")
    public String formNewgiocatore(Model model){
        return giocatoreBusiness.showRegisterForm(model);
    }

    @GetMapping("/{id}")
    public String giocatore(Model model, @PathVariable Long id){
        return giocatoreBusiness.giocatore(model,id);
    }


    @GetMapping("")
    public String giocatori(Model model){
        return giocatoreBusiness.giocatori(model);
    }



}
