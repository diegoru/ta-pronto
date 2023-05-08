package br.com.sinqia.dao;

import br.com.sinqia.dao.impl.CategoryDAOImpl;
import br.com.sinqia.db.DB;

public class DaoFactory {

    public static CategoryDAO createCategyDao() {
        return new CategoryDAOImpl(DB.getConnection());
    }
}
