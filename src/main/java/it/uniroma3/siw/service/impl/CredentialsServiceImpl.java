package it.uniroma3.siw.service.impl;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CredentialsServiceImpl implements CredentialsService {
	
   @Autowired
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	public Credentials getCredentials(Long id) {
		return credentialsRepository.findById(id).orElse(null);

	}

	public Credentials getCredentials(String username) {
		return credentialsRepository.findByUsername(username).orElse(null);
	}
		
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

	public Credentials savePresidenteCredentials(Credentials credentials) {
		credentials.setRole(Credentials.PRESIDENTE);
		return this.credentialsRepository.save(credentials);
	}
    
}