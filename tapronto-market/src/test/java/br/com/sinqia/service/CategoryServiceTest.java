package br.com.sinqia.service;

import br.com.sinqia.dao.impl.CategoryDAOImpl;
import br.com.sinqia.exceptions.CategoryNotFoundException;
import br.com.sinqia.model.Category;
import br.com.sinqia.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    CategoryServiceImpl categoryService;
    @Mock
    CategoryDAOImpl categoryDAO;

    @Test
    @DisplayName("Deve salvar uma categoria")
    public void saveTest() {
        Category category = getCategory();
        category.setId(1L);
        categoryService.save(category);
        verify(categoryDAO, times(1)).save(category);
    }

    @Test
    @DisplayName("Deve lancar erro categoria nula ao tentar salvar")
    public void exceptionCategoryNullSave() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> categoryService.save(null));
        assertEquals("Unexpected error! No category to save", runtimeException.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar uma categoria")
    public void updateTest() {
        Long id = 1L;
        Category category = getCategory();
        category.setId(id);

        when(categoryDAO.findById(anyLong())).thenReturn(Optional.of(category));

        Category updateCategory = category;
        updateCategory.setName("Padaria");

        when(categoryDAO.save(any(Category.class))).thenReturn(updateCategory);

        assertEquals(updateCategory, categoryService.update(id, category));
    }

    @Test
    @DisplayName("Deve lancar categoria nao encontrada update")
    public void exceptionCategoryNotFoundUpdate() {
        Long id = 1L;
        Category category = getCategory();
        category.setId(1L);

        when(categoryDAO.findById(anyLong())).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.update(id, category));
        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve deletar uma categoria pelo id")
    public void deleteByIdTest() {
        Long id = 1L;
        Category category = getCategory();
        category.setId(id);
        when(categoryDAO.findById(anyLong())).thenReturn(Optional.of(category));
        categoryService.deleteById(category.getId());
        verify(categoryDAO, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lancar categoria nao encontrada pelo id ao deletar")
    public void exceptionCategoryNotFoundDeleteById() {
        Long id = 1L;
        when(categoryDAO.findById(anyLong())).thenReturn(Optional.empty());
        CategoryNotFoundException categoryNotFoundException = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.deleteById(id));
        assertEquals("Category not found", categoryNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve buscar categoria pelo id")
    public void findByIdTest() {
        Long id = 1L;
        Category category = getCategory();
        category.setId(id);
        when(categoryDAO.findById(anyLong())).thenReturn(Optional.of(category));
        assertEquals(category, categoryService.findById(id));
    }

    @Test
    @DisplayName("Deve lancar erro categoria nao encontrada pelo id")
    public void exceptionCategoryNotFound() {
        when(categoryDAO.findById(anyLong())).thenReturn(Optional.empty());
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.findById(1L));
        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de categorias")
    public void findAllTest() {
        List<Category> list = Arrays.asList(getCategory());
        when(categoryDAO.findAll()).thenReturn(list);
        assertEquals(list, categoryService.findAll());

    }

    private Category getCategory() {
        Category category = new Category();
        category.setName("Matinais");
        return category;
    }

}
