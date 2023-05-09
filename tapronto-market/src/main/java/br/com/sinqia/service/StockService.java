package br.com.sinqia.service;

import br.com.sinqia.model.Stock;

import java.util.List;

public interface StockService {
    void insert(Stock stock);
    void update(Stock stock);
    void deleteById(Long id);
    Stock findById(Long id);
    List<Stock> findAll();
}
