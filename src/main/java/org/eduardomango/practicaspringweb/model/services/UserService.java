package org.eduardomango.practicaspringweb.model.services;


import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.EntityDuplicatedException;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IRepository<UserEntity> userRepository;

    public UserService(IRepository<UserEntity> userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
    public UserEntity findById(long id) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity findByUsername(String username){
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity findByEmail(String email){
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    public void save(UserEntity user) {
        boolean exists =
                userRepository.findAll().stream()
                        .anyMatch(x -> x.getId()==user.getId());
        if(exists)
            throw new EntityDuplicatedException("Ya se encuentra el usuario");
        userRepository.save(user);
    }

    public void delete(UserEntity user) {
        userRepository.delete(
                userRepository.findAll().stream()
                .filter(x-> x.getId()==user.getId())
                .findFirst().orElseThrow(UserNotFoundException::new));
    }

    public void update(UserEntity user) {
        userRepository.update(
                userRepository.findAll().stream()
                .filter(x->x.getId()==user.getId()).findFirst()
                .map(x->user)
                .orElseThrow(UserNotFoundException::new));
    }
}
