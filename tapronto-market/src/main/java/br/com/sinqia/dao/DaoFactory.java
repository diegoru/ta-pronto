package br.com.sinqia.dao;

import br.com.sinqia.dao.impl.CategoryDAOImpl;
import br.com.sinqia.dao.impl.OrderDAOImpl;
import br.com.sinqia.dao.impl.OrderItemDAOImpl;
import br.com.sinqia.dao.impl.ProductDAOImpl;
import br.com.sinqia.db.DB;

public class DaoFactory {

    public static CategoryDAO createCategoryDao() {
        return new CategoryDAOImpl(DB.getConnection());
    }
    public static ProductDAO createProductDao() {
        return new ProductDAOImpl(DB.getConnection());
    }
    public static OrderDAO createOrderDAO() {
        return new OrderDAOImpl(DB.getConnection());
    }

    public static OrderItemDAO createOrderItemDAO() {
        return new OrderItemDAOImpl(DB.getConnection());
    }
}
