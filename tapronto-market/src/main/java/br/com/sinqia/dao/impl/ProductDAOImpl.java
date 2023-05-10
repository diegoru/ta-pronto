package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.ProductDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Category;
import br.com.sinqia.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAOImpl implements ProductDAO {

    private final Connection conn;

    public ProductDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Product product) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO product "
                            + "(name, price, quantity, category_id) "
                            + "VALUES "
                            + "(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, product.getName());
            st.setBigDecimal(2, product.getPrice());
            st.setInt(3, product.getQuantity());
            st.setLong(4, product.getCategory().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    product.setId(id);
                }
                DB.closeResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Product product) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE product SET name = ?, price = ?, category_id = ? WHERE id = ?");
            st.setString(1, product.getName());
            st.setBigDecimal(2, product.getPrice());
            st.setLong(3, product.getCategory().getId());
            st.setLong(4, product.getId());

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
            st = conn.prepareStatement("DELETE FROM product WHERE id = ?");
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
    public Product findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT product.*, category.name as CatName " +
                            "FROM product " +
                            "INNER JOIN category " +
                            "ON product.category_id = category.id " +
                            "WHERE product.id = ?"
            );

            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Category category = new Category(
                        rs.getLong("category_id"),
                        rs.getString("CatName"));
                Product product = new Product();
                        product.setId(rs.getLong("id"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getBigDecimal("price"));
                        product.setCategory(category);
                return product;
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
    public List<Product> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT product.*, category.name as CatName " +
                        "FROM product INNER JOIN category " +
                        "ON product.category_id = category.id " +
                        "ORDER BY name"
            );

            rs = st.executeQuery();
            List<Product> list = new ArrayList<>();
            Map<Long, Category> map = new HashMap<>();
            while (rs.next()) {
                Category category = map.get(rs.getLong("category_id"));
                if (category == null) {
                    category = new Category(rs.getLong("category_id"), rs.getString("CatName"));
                    map.put(rs.getLong("category_id"), category);
                }
                Product product = new Product();
                        product.setId(rs.getLong("id"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getBigDecimal("price"));
                        product.setCategory(category);
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e. getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT product.*, category.name as CatName " +
                            "FROM product INNER JOIN category " +
                            "ON product.category_id = category.id " +
                            "WHERE category_id = ? " +
                            "ORDER BY name"
            );
            st.setLong(1, category.getId());
            rs = st.executeQuery();
            List<Product> list = new ArrayList<>();
            Map<Long, Category> map = new HashMap<>();
            while (rs.next()) {
                Category cat = map.get(rs.getLong("category_id"));
                if (cat == null) {
                    cat = new Category(rs.getLong("category_id"), rs.getString("CatName"));
                    map.put(rs.getLong("category_id"), cat);
                }
                Product product = new Product();
                        product.setId(rs.getLong("id"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getBigDecimal("price"));
                        product.setQuantity(rs.getInt("quantity"));
                        product.setCategory(cat);
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e. getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
