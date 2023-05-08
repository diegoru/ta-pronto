package br.com.sinqia.service.impl;

import br.com.sinqia.dao.ProductDAO;
import br.com.sinqia.exceptions.ProductNotFoundException;
import br.com.sinqia.model.Product;
import br.com.sinqia.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;


    @Override
    public void insert(Product product) {
        if (product == null) throw new RuntimeException("Unexpected error! No product to insert");
        productDAO.insert(product);
    }

    @Override
    public void update(Product product) {
        Product getProduct = productDAO.findById(product.getId());
        if (getProduct == null) throw new ProductNotFoundException();
        productDAO.update(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = productDAO.findById(id);
        if (product == null) throw new ProductNotFoundException();
        productDAO.deleteById(id);
    }

    @Override
    public Product findById(Long id) {
        Product product = productDAO.findById(id);
        if (product == null) throw new ProductNotFoundException();
        return product;
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }
}
