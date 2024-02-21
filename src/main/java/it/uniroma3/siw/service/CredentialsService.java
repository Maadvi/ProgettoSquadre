package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credentials;

public interface CredentialsService {


    public Credentials getCredentials(Long id);


    public Credentials getCredentials(String username);


    public Credentials saveCredentials(Credentials credentials);

    public Credentials savePresidenteCredentials(Credentials credentials);

}
