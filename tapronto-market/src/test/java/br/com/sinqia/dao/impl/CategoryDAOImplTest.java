package br.com.sinqia.dao.impl;

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
    private static CategoryDAOImpl dao;

    @BeforeAll
    public static void start() throws SQLException {
        conn = DB.getConnection();
        dao = new CategoryDAOImpl(conn);
        initializationBD();
    }

    @AfterAll
    public static void end() throws SQLException {
        DB.closeConnetion();
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
    @DisplayName("Deve lancar exception ao deletar categoria pelo id")
    void dbExceptionDeleteById() {
        assertThrows(DbException.class, () -> dao.deleteById(3L));
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

    private static void initializationBD() throws SQLException {
        PreparedStatement stTruncate = conn.prepareStatement("TRUNCATE TABLE category");
        stTruncate.executeUpdate();
        PreparedStatement stInsert = conn.prepareStatement("INSERT INTO category (name) VALUES (?)");
        stInsert.setString(1, "teste");
        stInsert.executeUpdate();
    }
}