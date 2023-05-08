package br.com.sinqia.dao;

import br.com.sinqia.model.Product;

import java.util.List;

public interface ProductDAO {
    void insert(Product product);
    void update(Product product);
    void deleteById(Long id);
    Product findById(Long id);
    List<Product> findAll();

}
