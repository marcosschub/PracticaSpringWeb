package org.eduardomango.practicaspringweb.model.services;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.SaleEntity;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.*;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

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



    public void save(long idSale, long idProduct, long idUser,@Min(0) long quantity) {
        try {
            saleRepository.save(
                SaleEntity.builder()
                .id(idSale)
                .products(productService.findById(idProduct))
                .quantity(quantity)
                .client(userService.findById(idUser))
                .saleDate(LocalDate.now()).build());
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
