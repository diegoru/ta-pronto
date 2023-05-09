package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.StockDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAOImpl implements StockDAO {

    private Connection conn;

    public StockDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Stock stock) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO stock (description) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, stock.getDescription());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    stock.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Stock stock) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE stock SET description = ? WHERE id = ?");
            st.setString(1, stock.getDescription());
            st.setLong(2, stock.getId());
            st.executeUpdate();
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
            st = conn.prepareStatement("DELETE FROM stock WHERE id = ?");
            st.setLong(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) throw new DbException("Unexpected error! No rows affected!");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Stock findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM stock WHERE stock.id = ?");
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getLong("id"));
                stock.setDescription(rs.getString("description"));
                return stock;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Stock> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM stock ORDER BY description");
            rs = st.executeQuery();
            List<Stock> list = new ArrayList<>();
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getLong("id"));
                stock.setDescription(rs.getString("description"));
                list.add(stock);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
