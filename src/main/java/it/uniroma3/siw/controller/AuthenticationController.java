package it.uniroma3.siw.controller;

import it.uniroma3.siw.business.AuthenticationBusiness;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;


@Controller
public class AuthenticationController {

	@Autowired
	private AuthenticationBusiness authenticationBusiness;
	
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		return authenticationBusiness.showRegisterForm(model);
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return authenticationBusiness.showLoginForm(model);
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		return authenticationBusiness.index(model);
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
		return authenticationBusiness.defaultAfterLogin(model);
    }

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) throws IOException {

		return authenticationBusiness.registerUser(user, userBindingResult, credentials, credentialsBindingResult, model);
    }
	

	
}