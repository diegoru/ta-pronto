package br.com.sinqia.dao;

import br.com.sinqia.model.Category;
import br.com.sinqia.model.Product;

import java.util.List;

public interface ProductDAO {
    void save(Product product);
    void update(Product product);
    void deleteById(Long id);
    Product findById(Long id);
    List<Product> findAll();
    List<Product> findByCategory(Category category);

}
