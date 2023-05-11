package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.OrderDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private final Connection conn;

    public OrderDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Order> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM orders ORDER BY created_at");
            rs = st.executeQuery();
            List<Order> list = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setCreatedAt(rs.getDate("created_at").toLocalDate());
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM orders WHERE orders.id = ?");
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setCreatedAt(rs.getDate("created_at").toLocalDate());
                return Optional.of(order);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Order save(Order order) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO orders (created_at) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setDate(1, Date.valueOf(order.getCreatedAt()));

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    order.setId(id);
                    return order;
                }
                DB.closeResultSet(rs);
            }else {
                throw new DbException("Unexpected error! No rows affected!");
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Long id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM orders WHERE orders.id = ?");
            st.setLong(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) throw new DbException("Unexpected error! No rows affected!");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
}
