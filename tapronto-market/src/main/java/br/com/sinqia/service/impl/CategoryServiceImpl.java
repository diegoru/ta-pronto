package br.com.sinqia.service.impl;

import br.com.sinqia.dao.CategoryDAO;
import br.com.sinqia.exceptions.CategoryNotFoundException;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.model.Category;
import br.com.sinqia.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;


    @Override
    public Category findById(Long id) {
        Optional<Category> optionalCategory = categoryDAO.findById(id);
        optionalCategory.orElseThrow(CategoryNotFoundException::new);
        return optionalCategory.get();
    }
    @Override
    public Category save(Category category) {
        if (category == null) throw new RuntimeException("Unexpected error! No category to save");
        return categoryDAO.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        if (category == null) throw new DbException("Unexpected error! No category to update");
        Optional<Category> optionalCategory = categoryDAO.findById(id);
        optionalCategory.orElseThrow(CategoryNotFoundException::new);
        return categoryDAO.save(optionalCategory.get());
    }

    @Override
    public void deleteById(Long id) {
        categoryDAO.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryDAO.deleteById(id);
    }



    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }
}
