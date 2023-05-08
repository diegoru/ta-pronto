package br.com.sinqia.service;

import br.com.sinqia.model.Category;

import java.util.List;

public interface CategoryService {
    void insert(Category category);
    void update(Category category);
    void deleteById(Long id);
    Category findById(Long id);

    List<Category> findAll();
}
