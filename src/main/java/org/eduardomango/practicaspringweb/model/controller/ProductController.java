package org.eduardomango.practicaspringweb.model.controller;

import lombok.RequiredArgsConstructor;
import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.exceptions.EntityDuplicatedException;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> list(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> get(@PathVariable long id){
        try {
            return ResponseEntity.ok(service.findById(id));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductEntity> post(@RequestBody ProductEntity product){
        try {
            service.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (EntityDuplicatedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }/// Uso el conflict porque es el codigo http para elementos duplicados
    }


    //todo preguntar si se puede copiar en el json un parametro del path
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> put(@PathVariable long id, @RequestBody ProductEntity product){
        try {
            service.update(product);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        try {
            service.delete(service.findById(id));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
