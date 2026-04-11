package org.eduardomango.practicaspringweb.model.services;

import org.eduardomango.practicaspringweb.model.entities.Sale.SalesDTO;
import org.eduardomango.practicaspringweb.model.entities.Sale.SaleEntity;
import org.eduardomango.practicaspringweb.model.exceptions.*;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {
    private final IRepository<SaleEntity> saleRepository;
    private final ProductService productService;
    private final UserService userService;

    public SaleService(IRepository<SaleEntity> saleRepository, ProductService productService, UserService userService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<SaleEntity> findAll(){return saleRepository.findAll();}

    public SaleEntity findById(long id){
        return saleRepository.findAll()
                .stream().filter(sale -> sale.getId() == id)
                .findFirst().orElseThrow(SaleNotFoundException::new);
    }



    public void save(SalesDTO sale) {
        try {
            saleRepository.save(
                SaleEntity.builder()
                .id(sale.getIdSale())
                .products(productService.findById(sale.getIdProduct()))
                .quantity(sale.getQuantity())
                .client(userService.findById(sale.getIdClient()))
                .saleDate(LocalDate.now())
                .build());
        } catch (ProductNotFoundException | UserNotFoundException e) {
            throw new InvalidArgumentsException(e.getMessage());
        }
    }

    public void delete(SaleEntity sale) {
        saleRepository.delete(saleRepository.findAll().stream()
                .filter(s -> s.getId().equals(sale.getId()))
                .findFirst().orElseThrow(SaleNotFoundException::new));
    }

    public void update(SaleEntity sale) {
        saleRepository.update(
                saleRepository.findAll().stream()
                .filter(x->x.getId()==sale.getId()).findFirst()
                .map(x->sale)
                .orElseThrow(SaleNotFoundException::new));
    }


}
