package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.RegisterDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Cashier;
import br.com.sinqia.model.Register;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegisterDAOImpl implements RegisterDAO {
    private final Connection conn;

    public RegisterDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Register> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT register.*, cashier.open as isOpen " +
                    "FROM register INNER JOIN cashier ON register.cashier_id = cashier.id"
            );
            rs = st.executeQuery();
            List<Register> list = new ArrayList<>();
            while (rs.next()) {
                Cashier cashier = createCashier(rs);
                Register register = createRegister(rs,cashier);
                list.add(register);
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
    public Optional<Register> findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT register.*, cashier.open as isOpen " +
                        "FROM register INNER JOIN cashier ON register.cashier_id = cashier.id " +
                        "WHERE register.id = ?"
            );
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Cashier cashier = createCashier(rs);
                Register register = createRegister(rs, cashier);
                return Optional.of(register);
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
    public Register save(Register register) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO register (date_time, opening_balance, closed_balance, cashier_id) " +
                            "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            st.setTimestamp(1, Timestamp.valueOf(register.getDateTime()));
            st.setBigDecimal(2, register.getOpeningBalance());
            st.setBigDecimal(3, register.getClosedBalance());
            st.setLong(4, register.getCashier().getId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    register.setId(id);
                    return register;
                }
                DB.closeResultSet(rs);
            } else {
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
            st = conn.prepareStatement("DELETE FROM register WHERE register.id = ?");
            st.setLong(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) throw new DbException("Unexpected error! No rows affected");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    private Cashier createCashier(ResultSet rs) throws SQLException {
        Cashier cashier = new Cashier();
        cashier.setId(rs.getLong("cashier_id"));
        cashier.setOpen(rs.getBoolean("isOpen"));
        return cashier;
    }

    private Register createRegister(ResultSet rs, Cashier cashier) throws SQLException {
        Register register = new Register();
        register.setId(rs.getLong("id"));
        register.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        register.setCashier(cashier);
        register.setOpeningBalance(rs.getBigDecimal("opening_balance"));
        register.setOpeningBalance(rs.getBigDecimal("closed_balance"));
        return register;
    }
}
