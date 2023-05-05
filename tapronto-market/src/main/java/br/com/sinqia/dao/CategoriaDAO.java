package br.com.sinqia.dao;

import br.com.sinqia.model.Categoria;
import br.com.sinqia.model.dto.CategoriaDTO;

import java.util.List;
import java.util.Optional;

public interface CategoriaDAO {
    Categoria save(Categoria categoria);
    Optional<Categoria> findById(Long id);
    void delete(Categoria categoria);
    List<Categoria> findAll();

}
