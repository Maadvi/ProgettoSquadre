package it.uniroma3.siw.controller;

import it.uniroma3.siw.business.GestioneSquadraBusiness;
import it.uniroma3.siw.business.PresidenteBusiness;
import it.uniroma3.siw.data.PresidenteData;
import it.uniroma3.siw.data.SquadraData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestioneSquadre")
public class GestioneSquadraController {
    @Autowired
    private GestioneSquadraBusiness gestioneSquadraBusiness;

    @GetMapping("/consultazione")
    public String consultazione(Model model) {
        return null;

    }

    @PostMapping("/admin/saveSquadra")
    public String saveSquadra(Model model, @ModelAttribute("squadraData") SquadraData squadraData){
        return gestioneSquadraBusiness.saveSquadra(model, squadraData);
    }

    @GetMapping("/admin/formNewSquadra")
    public String formNewSquadra(Model model){
        return gestioneSquadraBusiness.showRegisterForm(model);
    }
}
