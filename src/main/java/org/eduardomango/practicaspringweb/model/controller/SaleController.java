package org.eduardomango.practicaspringweb.model.controller;

import lombok.RequiredArgsConstructor;
import org.eduardomango.practicaspringweb.model.DTO.SalesDTO;
import org.eduardomango.practicaspringweb.model.entities.SaleEntity;
import org.eduardomango.practicaspringweb.model.exceptions.EntityDuplicatedException;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.exceptions.SaleNotFoundException;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
import org.eduardomango.practicaspringweb.model.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @GetMapping
    public ResponseEntity<List<SaleEntity>> list(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleEntity> get(@PathVariable long id){
        try {
            return ResponseEntity.ok(service.findById(id));
        }catch (SaleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
}

    @PostMapping
    public ResponseEntity<SaleEntity> post(@RequestBody SalesDTO salesDTO){
        try {
            service.save(salesDTO.getIdSale(), salesDTO.getIdProduct(),salesDTO.getIdClient(),salesDTO.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (EntityDuplicatedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (UserNotFoundException | ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleEntity> put( @PathVariable long id,@RequestBody SaleEntity sale){
        try {
            service.update(sale);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (SaleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(SaleEntity sale){
        try {
            service.delete(sale);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (SaleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
