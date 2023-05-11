package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.CategoryDAO;
import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.OrderItemDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Category;
import br.com.sinqia.model.Order;
import br.com.sinqia.model.OrderItem;
import br.com.sinqia.model.Product;

import java.sql.*;
import java.util.*;

public class OrderItemDAOImpl implements OrderItemDAO {

    private Connection conn;
    private CategoryDAO categoryDAO;

    public OrderItemDAOImpl(Connection conn) {
        this.conn = conn;
        this.categoryDAO = DaoFactory.createCategoryDao();
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT order_item.*, orders.created_at as created, " +
                            "product.name as prodName, product.price as prodPrice, product.category_id as catId " +
                            "FROM order_item INNER JOIN orders ON order_item.order_id = orders.id " +
                            "INNER JOIN product ON order_item.product_id = product.id " +
                            "WHERE order_item.id =  ?"
            );
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Order order = createOrder(rs);
                Category category = createCategory(rs.getLong("catId"));
                Product product = createProduct(rs, category);
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getLong("id"));
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQuantity(rs.getInt("quantity"));
                return Optional.of(orderItem);
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
    public OrderItem save(OrderItem item) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO order_item (order_id, product_id, quantity) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, item.getOrder().getId());
            st.setLong(2, item.getProduct().getId());
            st.setInt(3, item.getQuantity());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    item.getId();
                    return item;
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

    }

    @Override
    public List<OrderItem> findItemByOrderId(Long idPedido) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT order_item.*, orders.created_at as created, " +
                            "product.name as prodName, product.price as prodPrice, product.category_id as catId " +
                            "FROM order_item INNER JOIN orders ON order_item.order_id = orders.id " +
                            "INNER JOIN product ON order_item.product_id = product.id " +
                            "WHERE order_id =  ?"
            );
            st.setLong(1, idPedido);
            rs = st.executeQuery();
            List<OrderItem> list = new ArrayList<>();
            Map<Long, Order> map = new HashMap<>();
            while (rs.next()) {
                Order order = map.get(rs.getLong("order_id"));
                if (order == null) {
                    order = createOrder(rs);
                    map.put(rs.getLong("order_id"), order);
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getLong("id"));
                Category category = createCategory(rs.getLong("catId"));
                Product product = createProduct(rs, category);
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQuantity(rs.getInt("quantity"));
                list.add(orderItem);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Order createOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("order_id"));
        order.setCreatedAt(rs.getDate("created").toLocalDate());
        return order;
    }

    private Category createCategory(Long catId) {
        Category category = categoryDAO.findById(catId);
        return category;
    }

    private Product createProduct(ResultSet rs, Category category) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("product_id"));
        product.setName(rs.getString("prodName"));
        product.setPrice(rs.getBigDecimal("prodPrice"));
        product.setQuantity(rs.getInt("quantity"));
        return product;
    }
}
