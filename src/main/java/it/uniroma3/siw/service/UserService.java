package it.uniroma3.siw.service;

import it.uniroma3.siw.model.User;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface UserService {
    public User getUser(Long id);

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    public User saveUser(User user);

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    public List<User> getAllUsers();
}
