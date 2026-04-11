package org.eduardomango.practicaspringweb.model.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eduardomango.practicaspringweb.model.entities.User.UserEntity;
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
    public ResponseEntity<List<UserEntity>> getall(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserEntity> post(@Valid @RequestBody UserEntity user){
        service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> put(@PathVariable long id,@Valid @RequestBody UserEntity user){
        service.update(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        service.delete(service.findById(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
