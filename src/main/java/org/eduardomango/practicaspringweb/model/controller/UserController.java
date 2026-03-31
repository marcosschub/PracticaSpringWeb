package org.eduardomango.practicaspringweb.model.controller;

import lombok.RequiredArgsConstructor;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.EntityDuplicatedException;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
import org.eduardomango.practicaspringweb.model.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserEntity>> list(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable long id){
        try {
            return ResponseEntity.ok(service.findById(id));
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserEntity> post(@RequestBody UserEntity user){
        try {
            service.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (EntityDuplicatedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> put(@PathVariable long id, @RequestBody UserEntity user){
        try {
            service.update(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(UserEntity user){
        try {
            service.delete(user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
