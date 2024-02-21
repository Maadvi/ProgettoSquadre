package it.uniroma3.siw.controller;

import it.uniroma3.siw.business.GestioneSquadraBusiness;
import it.uniroma3.siw.business.PresidenteBusiness;
import it.uniroma3.siw.data.PresidenteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/presidente")
public class PresidenteController {
    @Autowired
    private PresidenteBusiness presidenteBusiness;

    @PostMapping("/admin/savePresidente")
    public String saveSquadra(Model model, @ModelAttribute("presidenteData") PresidenteData presidenteData){
        return presidenteBusiness.savePresidente(model, presidenteData);
    }

    @GetMapping("/admin/formNewPresidente")
    public String formNewPresidente(Model model){
        return presidenteBusiness.showRegisterForm(model);
    }

    @GetMapping("/{idPresidente}")
    public String getPresidente(Model model, @PathVariable Long idPresidente){
        return presidenteBusiness.getPresidente(model, idPresidente);
    }

}
