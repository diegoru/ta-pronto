package br.com.sinqia.dao;

import br.com.sinqia.jdbc.ConnetionFactory;
import br.com.sinqia.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoriaDAOImpl implements CategoriaDAO {

    private final ConnetionFactory connection;

    public CategoriaDAOImpl(ConnetionFactory connection) {
        this.connection = connection;
    }

    @Override
    public Categoria save(Categoria categoria) {

        try {
            Connection conn = connection.getConnection();
            String sql = "INSERT INTO tbl_categoria(descricao) VALUES (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, categoria.getDescricao());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            Categoria categoriaSalva = new Categoria();
            if (resultSet.next()) {
                categoriaSalva.setId(resultSet.getLong(1));
                System.out.println(resultSet.getLong(1));
            }
            categoriaSalva.setDescricao(categoria.getDescricao());
            conn.close();
            resultSet.close();
            return categoriaSalva;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Categoria> findById(Long id) {

        return Optional.empty();
    }

    @Override
    public void delete(Categoria categoria) {

    }

    @Override
    public List<Categoria> findAll() {
        try {
            Connection conn = connection.getConnection();
            String sql = "SELECT * FROM tbl_categoria";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Categoria> categorias = new ArrayList<>();
            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getLong("id"));
                categoria.setDescricao(resultSet.getString("descricao"));
                categorias.add(categoria);
            }
            conn.close();
            resultSet.close();
            return categorias;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
