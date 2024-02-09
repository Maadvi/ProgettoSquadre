package it.uniroma3.siw.business;

import it.uniroma3.siw.data.PresidenteData;
import it.uniroma3.siw.data.SquadraData;
import it.uniroma3.siw.mapper.PresidenteMapper;
import it.uniroma3.siw.mapper.UserMapper;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PresidenteService;
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

    public List<User> presidentiDisp(Model model){
        List<User> users = userService.getAllUsers();
        users = users.stream().filter(t->t.getFlgPresidente().equals(true) && t.getPresidente()==null).toList();

        return users;
    }

    public String showRegisterForm (Model model) {
        List<User> users = userService.getAllUsers();
        users = users.stream().filter(t->t.getFlgPresidente().equals(true) && t.getPresidente()==null).toList();

        model.addAttribute("users", userMapper.fromUser(users));
        model.addAttribute("presidente", new PresidenteData());

        return "admin/formNewPresidente.html";
    }

    public String savePresidente (Model model, PresidenteData presidenteData) {
        Long id;
        try{

            id = presidenteService.savePresidente(presidenteMapper.fromPresidenteData(presidenteData)).getId();
        }catch (Exception e){
            log.error(ExceptionUtils.getMessage(e));
            model.addAttribute("errore" , "errore nel salvatggio del presidente");
            return "admin/formNewPresidente.html";


        }
        //todo qui non setta il presidente dall apaginaq html della form ti dovrei star passando l'id controlla in debug che cazzo vuole
        return "admin/formNewPresidente.html"/*showSquadra(model, id) */;
    }
}
