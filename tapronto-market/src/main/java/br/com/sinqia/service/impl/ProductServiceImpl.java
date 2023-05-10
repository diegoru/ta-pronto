package br.com.sinqia.service.impl;

import br.com.sinqia.dao.ProductDAO;
import br.com.sinqia.exceptions.InsuficientQuantityOfProductException;
import br.com.sinqia.exceptions.InvalidQuantityException;
import br.com.sinqia.exceptions.ProductNotFoundException;
import br.com.sinqia.model.Product;
import br.com.sinqia.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    @Override
    public void save(Product product) {
        if (product == null) throw new RuntimeException("Unexpected error! No product to insert");
        productDAO.save(product);
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

    @Override
    public void addQuantityToProductById(Long id, Integer quantity) {
        if (quantity <= 0) throw new InvalidQuantityException();
        Product product = productDAO.findById(id);
        if (product == null) throw new ProductNotFoundException();
        quantity += product.getQuantity();
        product.setQuantity(quantity);
        productDAO.update(product);
    }

    @Override
    public void reduceQuantityToProductById(Long id, Integer quantity) {
        if (quantity <= 0) throw new InvalidQuantityException();
        Product product = productDAO.findById(id);
        if (product == null) throw new ProductNotFoundException();
        quantity = product.getQuantity() - quantity;
        if (quantity < 0) throw new InsuficientQuantityOfProductException();
        product.setQuantity(quantity);
        productDAO.update(product);
    }
}
