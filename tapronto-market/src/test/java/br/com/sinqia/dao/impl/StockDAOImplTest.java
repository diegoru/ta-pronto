package br.com.sinqia.dao.impl;

import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.StockDAO;
import br.com.sinqia.db.DB;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StockDAOImplTest {

    private static Connection conn;
    private static StockDAO dao;

    @BeforeAll
    public static void start() throws SQLException {
        conn = DB.getConnection();
        dao = DaoFactory.createStockDAO();
        initializationDataBase();
    }

    @Test
    @DisplayName("Deve inserir um estoque no banco de dados")
    void insertTest() {
        Stock stock = new Stock();
        stock.setDescription("Loja");
        assertDoesNotThrow(() -> dao.insert(stock));
    }

    @Test
    @DisplayName("Deve atualizar um estoque no banco de dados")
    void updateTest() {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setDescription("Secundário");
        assertDoesNotThrow(() -> dao.update(stock));
    }

    @Test
    @DisplayName("Deve deletar um registro de estoque pelo id")
    void deleteByIdTest() {
        assertDoesNotThrow(() -> dao.deleteById(2L));
    }

    @Test
    @DisplayName("Deve lancar uma exception ao tentar deletar um estoque pelo id")
    void dbExceptionDeleteById() {
        DbException dbException = assertThrows(DbException.class, () -> dao.deleteById(3L));
        assertEquals("Unexpected error! No rows affected!", dbException.getMessage());
    }

    @Test
    @DisplayName("Deve buscar no banco de dados um estoque pelo id")
    void findByIdTest() {
        assertDoesNotThrow(() -> dao.findById(1L));
    }

    @Test
    @DisplayName("Deve listar os estoques registrados no banco de dados")
    void findAll() {
        assertDoesNotThrow(() -> dao.findAll());
    }

    private static void initializationDataBase() throws SQLException {
        PreparedStatement st = null;

        String dropTable = "DROP TABLE stock";
        st = conn.prepareStatement(dropTable);
        st.executeUpdate();
        DB.closeStatement(st);

        String createTableStock =
                "CREATE TABLE stock (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "description VARCHAR(100) NOT NULL, " +
                "PRIMARY KEY (id))";

        st = conn.prepareStatement(createTableStock);
        st.executeUpdate();
        DB.closeStatement(st);

        st = conn.prepareStatement("INSERT INTO stock (description) VALUES (?)");
        st.setString(1, "Principal");
        st.executeUpdate();
        st.setString(1,"Loja");
        st.executeUpdate();
        DB.closeStatement(st);
    }
}