package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.CategoryDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoryDAOImpl implements CategoryDAO {

    private final Connection conn;

    public CategoryDAOImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Category> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM category ORDER BY name");
            rs = st.executeQuery();
            List<Category> list = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                list.add(category);
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
    public Optional<Category> findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM category WHERE category.id = ?");
            st.setLong(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                return Optional.of(category);
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
    public Category save(Category category) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO category (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, category.getName());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    category.setId(id);
                    return category;
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
            st = conn.prepareStatement("DELETE FROM category WHERE id = ?");
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
