package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.CashierDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Cashier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CashierDAOImpl implements CashierDAO {

    private Connection conn;

    public CashierDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Cashier> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM cashier");
            rs = st.executeQuery();

            List<Cashier> list = new ArrayList<>();

            while (rs.next()) {
                Cashier cashier = new Cashier(rs.getLong("id"), rs.getBoolean("open"));
                list.add(cashier);
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
    public Optional<Cashier> findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM cashier WHERE cashier.id = ?");
            st.setLong(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Cashier cashier = new Cashier(rs.getLong("id"), rs.getBoolean("open"));
                return Optional.of(cashier);
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
    public Cashier save(Cashier cashier) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO cashier (open) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setBoolean(1, cashier.isOpen());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    cashier.setId(id);
                    return cashier;
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
    public void deletebyId(Long id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM cashier WHERE cashier.id = ?");
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
