package br.com.sinqia.dao;

import br.com.sinqia.dao.impl.CategoryDAOImpl;
import br.com.sinqia.dao.impl.ProductDAOImpl;
import br.com.sinqia.dao.impl.StockDAOImpl;
import br.com.sinqia.db.DB;

public class DaoFactory {

    public static CategoryDAO createCategoryDao() {
        return new CategoryDAOImpl(DB.getConnection());
    }
    public static ProductDAO createProductDao() {
        return new ProductDAOImpl(DB.getConnection());
    }
    public static StockDAO createStockDAO() {
        return new StockDAOImpl(DB.getConnection());
    }
}
