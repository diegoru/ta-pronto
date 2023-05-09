package br.com.sinqia.service.impl;

import br.com.sinqia.dao.StockDAO;
import br.com.sinqia.exceptions.StockNotFoundException;
import br.com.sinqia.model.Stock;
import br.com.sinqia.service.StockService;

import java.util.List;

public class StockServiceImpl implements StockService {

    private StockDAO stockDAO;


    @Override
    public void insert(Stock stock) {
        if (stock == null) throw new RuntimeException("Unexpected error! No stock to insert");
        stockDAO.insert(stock);
    }

    @Override
    public void update(Stock stock) {
        Stock getStock = stockDAO.findById(stock.getId());
        if (getStock == null) throw new StockNotFoundException();
        stockDAO.update(stock);
    }

    @Override
    public void deleteById(Long id) {
        Stock stock = stockDAO.findById(id);
        if (stock == null) throw new StockNotFoundException();
        stockDAO.deleteById(id);
    }

    @Override
    public Stock findById(Long id) {
        Stock stock = stockDAO.findById(id);
        if (stock == null) throw new StockNotFoundException();
        return stock;
    }

    @Override
    public List<Stock> findAll() {
        return stockDAO.findAll();
    }
}
