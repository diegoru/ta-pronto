package br.com.sinqia.dao;

import br.com.sinqia.model.Stock;

import java.util.List;

public interface StockDAO {
    void insert(Stock stock);
    void update(Stock stock);
    void deleteById(Long id);
    Stock findById(Long id);
    List<Stock> findAll();
}
