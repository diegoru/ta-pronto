package br.com.sinqia.dao;

import br.com.sinqia.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);

}
