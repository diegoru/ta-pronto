package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.CategoryDAO;
import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.ProductDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Category;
import br.com.sinqia.model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOImplTest {

    private static Connection conn;
    private static CategoryDAO categoryDAO;
    private static ProductDAO productDAO;

    @BeforeAll
    public static void start() throws SQLException {
        conn = DB.getConnection();
        categoryDAO = DaoFactory.createCategoryDao();
        productDAO = DaoFactory.createProductDao();
        initializationDataBase();
    }

    @Test
    @DisplayName("Deve inserir produto no banco de dados")
    void insertTest() {
            Category category = categoryDAO.findById(1L);
            Product product = new Product(null, "Pão de forma", new BigDecimal("7.90"), category);
            assertDoesNotThrow(() -> productDAO.insert(product));
    }

    @Test
    @DisplayName("Deve atualizar registro produto no banco de dados")
    void updateTest() {
        Category category = categoryDAO.findById(1L);
        Product product = new Product(1L, "Bolacha Trakinas", new BigDecimal("3.99"), category);
        assertDoesNotThrow(() -> productDAO.update(product));
    }

    @Test
    @DisplayName("Deve deletar registro produto pelo id")
    void deleteByIdTest() {
        assertDoesNotThrow(() -> productDAO.deleteById(1L));
    }

    @Test
    @DisplayName("Deve lancar exception ao deletar produto pelo id")
    void dbExceptionDeleteById() {
        assertThrows(DbException.class, () -> productDAO.deleteById(3L));
    }

    @Test
    @DisplayName("Deve buscar uma produto por id no banco de dados")
    void findByIdTest() {
        assertDoesNotThrow(() -> productDAO.findById(1L));
    }

    @Test
    @DisplayName("Deve listar os produtos registrados no banco de dados")
    void findAllTest() {
        assertDoesNotThrow(() -> productDAO.findAll());
    }

    @Test
    @DisplayName("Deve buscar produtos no banco de dados por categoria")
    public void findByCategoryTest() {
        Category category = categoryDAO.findById(1L);
        assertDoesNotThrow(() -> productDAO.findByCategory(category));
    }

    public static void initializationDataBase() throws SQLException {
        PreparedStatement st = null;

        String dropTables = "DROP TABLE product, category";
        st = conn.prepareStatement(dropTables);
        st.executeUpdate();

        String createTableCategory =
                "CREATE TABLE category ( "
                        + "id BIGINT NOT NULL AUTO_INCREMENT, "
                        + "name VARCHAR(60) NOT NULL, "
                        + "PRIMARY KEY (id))";

        String createTableProduct =
                "CREATE TABLE product( " +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "price DECIMAL(5,2) NOT NULL, " +
                        "category_id BIGINT NOT NULL, " +
                        "PRIMARY KEY (id), " +
                        "FOREIGN KEY (category_id) REFERENCES category (id) )";


        st = conn.prepareStatement(createTableCategory);
        st.executeUpdate();
        st = conn.prepareStatement(createTableProduct);
        st.executeUpdate();

        st = conn.prepareStatement("INSERT INTO category (name) VALUES (?)");
        st.setString(1, "Matinais");
        st.executeUpdate();

        st = conn.prepareStatement(
                "INSERT INTO product (name, price, category_id) "
                        + "VALUES (?, ?, ?)");
        st.setString(1, "Biscoito Trakinas");
        st.setBigDecimal(2, new BigDecimal("4.90"));
        st.setLong(3, 1L);
        st.executeUpdate();

        DB.closeStatement(st);
    }
}