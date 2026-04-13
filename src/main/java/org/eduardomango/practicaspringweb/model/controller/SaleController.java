package org.eduardomango.practicaspringweb.model.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eduardomango.practicaspringweb.model.entities.Sale.SalesDTO;
import org.eduardomango.practicaspringweb.model.entities.Sale.SaleEntity;
import org.eduardomango.practicaspringweb.model.exceptions.*;
import org.eduardomango.practicaspringweb.model.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @GetMapping
    public ResponseEntity<List<SaleEntity>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleEntity> get(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SaleEntity> post(@Valid @RequestBody SalesDTO salesDTO) {
        service.save(salesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleEntity> put(@PathVariable long id, @Valid @RequestBody SaleEntity sale) {
        service.update(sale);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(service.findById(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
