package it.uniroma3.siw.controller;

import it.uniroma3.siw.business.GestioneSquadraBusiness;
import it.uniroma3.siw.business.PresidenteBusiness;
import it.uniroma3.siw.data.PresidenteData;
import it.uniroma3.siw.data.SquadraData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gestioneSquadre")
public class GestioneSquadraController {
    @Autowired
    private GestioneSquadraBusiness gestioneSquadraBusiness;

    @GetMapping("/consultazione")
    public String consultazione(Model model) {
        return gestioneSquadraBusiness.consultazione(model);

    }

    @PostMapping("/admin/saveSquadra")
    public String saveSquadra(Model model, @ModelAttribute("squadraData") SquadraData squadraData){
        return gestioneSquadraBusiness.saveSquadra(model, squadraData);
    }

    @GetMapping("/admin/formNewSquadra")
    public String formNewSquadra(Model model){
        return gestioneSquadraBusiness.showRegisterForm(model);
    }

    @GetMapping("/presidente/addGiocatoreToSquadra/{idSquadra}")
    public String addGiocatoreToSquadra(Model model, @PathVariable Long idSquadra){
        return gestioneSquadraBusiness.addGiocatoreToSquadra(model,idSquadra);
    }

    @GetMapping("/presidente/addGiocatore/{idGiocatore}/{idSquadra}")
    public String addGiocatore(Model model, @PathVariable Long idGiocatore, @PathVariable Long idSquadra){
        return gestioneSquadraBusiness.addGiocatore(model,idSquadra, idGiocatore);
    }
    @GetMapping("/presidente/removeGiocatore/{idGiocatore}/{idSquadra}")
    public String removeGiocatore(Model model, @PathVariable Long idGiocatore, @PathVariable Long idSquadra){
        return gestioneSquadraBusiness.removeGiocatore(model,  idGiocatore, idSquadra );
    }

    @GetMapping("/squadra/{idSquadra}")
    public String removeGiocatore(Model model, @PathVariable Long idSquadra){
        return gestioneSquadraBusiness.showSquadra(model, idSquadra );
    }

}
