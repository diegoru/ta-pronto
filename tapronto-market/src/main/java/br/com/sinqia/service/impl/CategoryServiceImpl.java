package br.com.sinqia.service.impl;

import br.com.sinqia.dao.CategoryDAO;
import br.com.sinqia.exceptions.CategoryNotFoundException;
import br.com.sinqia.model.Category;
import br.com.sinqia.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;


    @Override
    public void save(Category category) {
        if (category == null) throw new RuntimeException("Unexpected error! No category to save");
        categoryDAO.save(category);
    }

    @Override
    public void update(Category category) {
        Category getCategory = categoryDAO.findById(category.getId());
        if (getCategory == null) throw new CategoryNotFoundException();
        categoryDAO.update(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryDAO.findById(id);
        if (category == null) throw new CategoryNotFoundException();
        categoryDAO.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        Category category = categoryDAO.findById(id);
        if (category == null) throw new CategoryNotFoundException();
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }
}
