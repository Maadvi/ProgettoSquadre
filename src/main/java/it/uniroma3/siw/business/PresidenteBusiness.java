package it.uniroma3.siw.business;

import it.uniroma3.siw.data.PresidenteData;
import it.uniroma3.siw.data.UserData;
import it.uniroma3.siw.mapper.PresidenteMapper;
import it.uniroma3.siw.mapper.UserMapper;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
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

import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Slf4j
public class PresidenteBusiness {
    @Autowired
    private PresidenteMapper presidenteMapper;

    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SquadraService squadraService;

    @Autowired
    private CredentialsService credentialsService;

    public String getPresidente(Model model, Long id){
        Presidente presidente = presidenteService.findPresidenteById(id);
        Squadra squadra = squadraService.findByPresidente(presidente);
        PresidenteData presidenteData = presidenteMapper.fromPresidente(presidente);
        if(squadra!=null && squadra.getId()!=null){
            presidenteData.setIdSquadra(String.valueOf(squadra.getId()));

        }
        model.addAttribute("presidente",presidenteData) ;

        return "presidente.html";
    }

    public String showRegisterForm (Model model) {
        try{
            List<User> users1 = userService.getAllUsers();
            users1 = users1.stream().filter(t->t.getFlgPresidente().equals(true) && t.getPresidente()==null).toList();

            List<UserData> users = userMapper.fromUser(users1);

            model.addAttribute("users", users);
            model.addAttribute("presidente", new PresidenteData());

            return "admin/formNewPresidente.html";

        }catch (Exception e){
            log.error(ExceptionUtils.getMessage(e));
        }

        return "admin/formNewPresidente.html";
    }

    public String savePresidente (Model model, PresidenteData presidenteData) {
        Long id;
        try{
            User user = userService.getUser(Long.valueOf(presidenteData.getNome()));

            Presidente presidente = presidenteMapper.fromPresidenteData(presidenteData);
            presidente.setUser(user);
            presidente.setNome(user.getName());
            presidente.setCognome(user.getSurname());
            user.setPresidente(presidente);
            userService.saveUser(user);
            id = presidenteService.savePresidente(presidente).getId();
            Credentials credentials=credentialsService.getCredentials(user.getSurname());
            credentialsService.savePresidenteCredentials(credentials);
        }catch (Exception e){
            log.error(ExceptionUtils.getMessage(e));
            model.addAttribute("errore" , "errore nel salvatggio del presidente");
            return showRegisterForm ( model);
        }
        model.addAttribute("errore" , "presidente salvato correttamente");
        return getPresidente ( model,id);
    }
}
