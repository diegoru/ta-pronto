package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.CashierDAO;
import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.OrderDAO;
import br.com.sinqia.dao.RegisterOrderDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Cashier;
import br.com.sinqia.model.Order;
import br.com.sinqia.model.Register;
import br.com.sinqia.model.RegisterOrder;

import java.sql.*;
import java.util.Optional;

public class RegisterOrderDAOImpl implements RegisterOrderDAO {

    private Connection conn;
    private CashierDAO cashierDAO;
    private OrderDAO orderDAO;

    public RegisterOrderDAOImpl(Connection conn) {
        this.conn = conn;
        this.cashierDAO = DaoFactory.createCashierDAO();
        this.orderDAO = DaoFactory.createOrderDAO();
    }

    @Override
    public Optional<RegisterOrder> findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT register_order.*, " +
                    "register.date_time, register.opening_balance, register.closed_balance, register.cashier_id, " +
                    "orders.created_at as dateOrder, orders.total " +
                    "FROM register_order " +
                    "INNER JOIN register ON register_order.register_id = register.id " +
                    "INNER JOIN orders ON register_order.order_id = orders.id " +
                    "WHERE register_order.id = ?"
                    );
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                RegisterOrder registerOrder = new RegisterOrder();
                registerOrder.setId(rs.getLong("id"));
                registerOrder.setRegister(getRegister(rs));
                registerOrder.setOrder(getOrder(rs.getLong("order_id")));
                return Optional.of(registerOrder);
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
    public RegisterOrder save(RegisterOrder registerOrder) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO register_order (register_id, order_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, registerOrder.getRegister().getId());
            st.setLong(2, registerOrder.getOrder().getId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    registerOrder.setId(id);
                    return registerOrder;
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected");
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
            st = conn.prepareStatement("DELETE FROM register_order WHERE register_order.id = ?");
            st.setLong(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) throw new DbException("Unexpected error! No rows affected");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    private Register getRegister(ResultSet rs) throws SQLException {
        Cashier cashier = cashierDAO.findById(rs.getLong("cashier_id")).get();

        Register register = new Register();
        register.setId(rs.getLong("register_id"));
        register.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        register.setOpeningBalance(rs.getBigDecimal("opening_balance"));
        register.setClosedBalance(rs.getBigDecimal("closed_balance"));
        register.setCashier(cashier);

        return register;
    }
    private Order getOrder(Long id) {
        return orderDAO.findById(id).get();
    }
}
