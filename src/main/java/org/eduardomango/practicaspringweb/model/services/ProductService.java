package org.eduardomango.practicaspringweb.model.services;

import org.eduardomango.practicaspringweb.model.entities.Product.ProductEntity;
import org.eduardomango.practicaspringweb.model.exceptions.EntityDuplicatedException;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.exceptions.SaleNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final IRepository<ProductEntity> productRepository;

    public ProductService(IRepository<ProductEntity> productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public ProductEntity findById(long id) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("El producto buscado no se encuentra"));
    }

    public ProductEntity findByName(String name) {
        return productRepository.findAll()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("El producto buscado no se encuentra"));
    }

    public List<ProductEntity> findMoreExpensiveThan(Double price) {
        return productRepository.findAll()
                .stream()
                .filter(user -> user.getPrice() > price)
                .toList();
    }

    public void save(ProductEntity p) {
        boolean exists =
                productRepository.findAll().stream()
                        .anyMatch(x -> x.getId() == p.getId());
        if (exists)
            throw new EntityDuplicatedException("Ya se encuentra el producto");
        productRepository.save(p);
    }

    public void delete(ProductEntity p) {
        productRepository.delete(
                productRepository.findAll().stream()
                        .filter(x -> x.getId() == p.getId())
                        .findFirst().orElseThrow(() -> new ProductNotFoundException("El producto buscado no se encuentra"))
        );
    }

    public void update(ProductEntity p) {
        productRepository.update(
                productRepository.findAll().stream()
                        .filter(x -> x.getId() == p.getId()).findFirst()
                        .map(x -> p)
                        .orElseThrow(() -> new ProductNotFoundException("El producto buscado no se encuentra")));
    }
}
