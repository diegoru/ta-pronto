package br.com.sinqia.dao;

import br.com.sinqia.dao.impl.*;
import br.com.sinqia.db.DB;
import br.com.sinqia.model.Cashier;

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

    public static CashierDAO createCashierDAO() {
        return new CashierDAOImpl(DB.getConnection());
    }

    public static RegisterDAO createRegisterDAO() {
        return new RegisterDAOImpl(DB.getConnection());
    }
}
