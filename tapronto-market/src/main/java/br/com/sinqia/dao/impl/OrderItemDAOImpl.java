package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.OrderItemDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Order;
import br.com.sinqia.model.OrderItem;
import br.com.sinqia.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItemDAOImpl implements OrderItemDAO {

    private Connection conn;

    public OrderItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<OrderItem> findById(Long id) {

        return Optional.empty();
    }

    @Override
    public OrderItem save(OrderItem item) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    public void findItemByOrderId(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM order_item WHERE order_id = ?");
            st.setLong(id);

            List<OrderItem> list = new ArrayList<>();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
