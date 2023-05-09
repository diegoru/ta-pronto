package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.CategoryDAO;
import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Category;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOImplTest {

    private static Connection conn;
    private static CategoryDAO dao;

    @BeforeAll
    public static void start() throws SQLException {
        conn = DB.getConnection();
        dao = DaoFactory.createCategoryDao();
        initializationDataBase();
    }
    @Test
    @DisplayName("Deve inserir categoria no banco de dados")
    public void insertTest() {
        Category category = new Category(null, "Matinais");
        assertDoesNotThrow(() -> dao.insert(category));
    }

    @Test
    @DisplayName("Deve atualizar registro categoria no banco de dados")
    void updateTest() {
        Category category = new Category(1L, "Padaria");
        assertDoesNotThrow(() -> dao.update(category));
    }

    @Test
    @DisplayName("Deve deletar registro categoria pelo id")
    void deleteByIdTest() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
    }

    @Test
    @DisplayName("Deve lancar uma exception ao deletar categoria pelo id")
    void dbExceptionDeleteById() {
        DbException dbException = assertThrows(DbException.class, () -> dao.deleteById(3L));
        assertEquals("Unexpected error! No rows affected!", dbException.getMessage());
    }

    @Test
    @DisplayName("Deve buscar uma categoria por id no banco de dados")
    void findByIdTest() {
        assertDoesNotThrow(() -> dao.findById(1L));
    }

    @Test
    @DisplayName("Deve listar as categorias registradas no banco de dados")
    void findAllTest() {
        assertDoesNotThrow(() -> dao.findAll());
    }

    public static void initializationDataBase() throws SQLException {
        PreparedStatement st = null;

        String dropTables = "DROP TABLE product, category";
        st = conn.prepareStatement(dropTables);
        st.executeUpdate();
        DB.closeStatement(st);

        String createTableCategory =
                "CREATE TABLE category ( "
                + "id BIGINT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(60) NOT NULL, "
                + "PRIMARY KEY (id))";
        st = conn.prepareStatement(createTableCategory);
        st.executeUpdate();
        DB.closeStatement(st);

        String createTableProduct =
                "CREATE TABLE product( " +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "price DECIMAL(5,2) NOT NULL, " +
                        "category_id BIGINT NOT NULL, " +
                        "PRIMARY KEY (id), " +
                        "FOREIGN KEY (category_id) REFERENCES category (id) )";
        st = conn.prepareStatement(createTableProduct);
        st.executeUpdate();
        DB.closeStatement(st);

        st = conn.prepareStatement("INSERT INTO category (name) VALUES (?)");
        st.setString(1, "Matinais");
        st.executeUpdate();
        DB.closeStatement(st);
    }

}