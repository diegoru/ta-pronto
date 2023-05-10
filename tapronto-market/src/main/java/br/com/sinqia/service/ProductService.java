package br.com.sinqia.service;

import br.com.sinqia.model.Product;

import java.util.List;

public interface ProductService {
    void save(Product product);
    void update(Product product);
    void deleteById(Long id);
    Product findById(Long id);
    List<Product> findAll();
    void addQuantityToProductById(Long id, Integer quantity);
    void reduceQuantityToProductById(Long id, Integer quantity);

}
