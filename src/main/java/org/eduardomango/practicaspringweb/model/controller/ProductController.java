package org.eduardomango.practicaspringweb.model.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eduardomango.practicaspringweb.model.entities.Product.ProductEntity;
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
            return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductEntity> post(@Valid @RequestBody ProductEntity product){
        service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> put(@PathVariable long id, @Valid @RequestBody ProductEntity product){
        service.update(product);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        service.delete(service.findById(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
