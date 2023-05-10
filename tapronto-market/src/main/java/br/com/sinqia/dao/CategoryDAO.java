package br.com.sinqia.dao;

import br.com.sinqia.model.Category;

import java.util.List;

public interface CategoryDAO {
    void save(Category category);
    void update(Category category);
    void deleteById(Long id);
    Category findById(Long id);
    List<Category> findAll();

}
