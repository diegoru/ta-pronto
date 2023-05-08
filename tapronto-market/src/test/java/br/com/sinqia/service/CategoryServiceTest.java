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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    CategoryServiceImpl service;
    @Mock
    CategoryDAOImpl dao;

    @Test
    @DisplayName("Deve inserir categoria")
    public void insertTest() {
        Category category = new Category(1L, "Limpeza");
        service.insert(category);
        verify(dao, times(1)).insert(category);
    }

    @Test
    @DisplayName("Deve lancar erro categoria nula")
    public void shouldNotInsertACategoryNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> service.insert(null));
        assertEquals("Unexpected error! No category to insert", runtimeException.getMessage());
    }

        @Test
        @DisplayName("Deve atualizar uma categoria por id")
        public void updateTest() {
            Category category = getCategory();
            category.setId(1L);

            when(dao.findById(anyLong())).thenReturn(category);

            category.setName("Padaria");

            service.update(category);
            verify(dao, times(1)).update(category);
        }

    @Test
    @DisplayName("Deve lancar categoria nao encontrada update")
    public void categoryNotFoundUpdate() {
        Category category = new Category();
        category = getCategory();
        category.setId(1L);

        when(dao.findById(anyLong())).thenReturn(nullable(Category.class));

        Category categoryUpdate = category;
        categoryUpdate.setName("Padaria");

        CategoryNotFoundException categoryNotFoundException = assertThrows(CategoryNotFoundException.class, () -> service.update(categoryUpdate));
        assertEquals("Category not found", categoryNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve deletar uma categoria pelo id")
    public void deleteTest() {
        Category category = new Category();
        category = getCategory();
        category.setId(1L);
        when(dao.findById(anyLong())).thenReturn(category);
        service.deleteById(category.getId());
        verify(dao, times(1)).deleteById(category.getId());
    }

    @Test
    @DisplayName("Deve lancar categoria nao encontrada pelo id ao deletar")
    public void categoryNotFoundDelete() {
        when(dao.findById(anyLong())).thenReturn(null);
        CategoryNotFoundException categoryNotFoundException = assertThrows(CategoryNotFoundException.class, () -> service.deleteById(1L));
        assertEquals("Category not found", categoryNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve obter categoria pelo id")
    public void findByIdTest() {
        Long id = 1L;
        Category category = getCategory();
        category.setId(id);
        when(dao.findById(anyLong())).thenReturn(category);
        assertEquals(category, service.findById(id));
    }

    @Test
    @DisplayName("Deve lancar categoria nao encontrada pelo id")
    public void categoryNotFound() {
        when(dao.findById(anyLong())).thenReturn(null);
        CategoryNotFoundException categoryNotFoundException = assertThrows(CategoryNotFoundException.class, () -> service.findById(1L));
        assertEquals("Category not found", categoryNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de categorias")
    public void findAllTest() {
        List<Category> list = Arrays.asList(getCategory());
        when(dao.findAll()).thenReturn(list);
        assertEquals(list, service.findAll());

    }

    private Category getCategory() {
        Category category = new Category();
        category.setName("Matinais");
        return category;
    }

}
