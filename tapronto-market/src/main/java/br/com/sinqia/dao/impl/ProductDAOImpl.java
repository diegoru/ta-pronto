package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.ProductDAO;
import br.com.sinqia.model.Product;

import java.sql.Connection;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final Connection conn;

    public ProductDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
