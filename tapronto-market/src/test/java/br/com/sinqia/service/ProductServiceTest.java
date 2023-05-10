package br.com.sinqia.service;

import br.com.sinqia.dao.ProductDAO;
import br.com.sinqia.exceptions.InsuficientQuantityOfProductException;
import br.com.sinqia.exceptions.InvalidQuantityException;
import br.com.sinqia.exceptions.ProductNotFoundException;
import br.com.sinqia.model.Category;
import br.com.sinqia.model.Product;
import br.com.sinqia.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl service;
    @Mock
    private ProductDAO dao;

    @Test
    @DisplayName("Deve inserir um produto")
    public void insertTest() {
        Product product = getProduct();
        product.setId(1L);
        service.save(product);
        verify(dao, times(1)).save(product);
    }

    @Test
    @DisplayName("Deve lancar erro produto nulo")
    public void shouldNotInsertAProductNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> service.save(null));
        assertEquals("Unexpected error! No product to insert", runtimeException.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar um produto por id")
    public void updateTest() {

        Product product = getProduct();
        product.setId(1L);

        when(dao.findById(anyLong())).thenReturn(product);

        product.setName("Biscoito Água e Sal");

        service.update(product);
        verify(dao, times(1)).update(product);
    }



    @Test
    @DisplayName("Deve lancar produto nao encontrado update")
    public void productNotFoundUpdate() {
       Product product = getProduct();
        product.setId(1L);

        when(dao.findById(anyLong())).thenReturn(nullable(Product.class));

        product.setName("Biscoito Água e Sal");

        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class,
                () -> service.update(product));
        assertEquals("Product not found", productNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve deletar um produto pelo id")
    public void deleteTest() {
        Product product = getProduct();
        product.setId(1L);
        when(dao.findById(anyLong())).thenReturn(product);
        service.deleteById(product.getId());
        verify(dao, times(1)).deleteById(product.getId());
    }

    @Test
    @DisplayName("Deve lancar produto nao encontrado pelo id ao deletar")
    public void productNotFoundDelete() {
        when(dao.findById(anyLong())).thenReturn(null);
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> service.deleteById(1L));
        assertEquals("Product not found", productNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve obter produto pelo id")
    public void findByIdTest() {
        Long id = 1L;
        Product product = getProduct();
        product.setId(id);
        when(dao.findById(anyLong())).thenReturn(product);
        assertEquals(product, service.findById(id));
    }

    @Test
    @DisplayName("Deve lancar produto nao encontrado pelo id")
    public void productNotFoundById() {
        when(dao.findById(anyLong())).thenReturn(null);
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> service.findById(1L));
        assertEquals("Product not found", productNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de produtos")
    public void findAllTest() {
        List<Product> list = Arrays.asList(getProduct());
        when(dao.findAll()).thenReturn(list);
        assertEquals(list, service.findAll());

    }

    @Test
    @DisplayName("Deve Adicionar quantidade ao estoque do produto pelo id")
    public void addQuantityToProductByIdTest() {
        int quantity = 5;
        Product product = getProduct();
        product.setId(1L);
        when(dao.findById(anyLong())).thenReturn(product);
        product.setQuantity(product.getQuantity() + quantity);
        service.addQuantityToProductById(1L, quantity);
        verify(dao, times(1)).update(product);
    }

    @Test
    @DisplayName("Deve lancar erro quantidade invalida ao adicionar quantidade ao produto")
    public void exceptionInvalidQuantityToAddQuantity() {
        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class, () -> service.addQuantityToProductById(1L, 0));
        assertEquals("Invalid quantity", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lancar erro adicionar quantidade produto nulo")
    public void exceptionAddQuantityToProductNull() {
        when(dao.findById(anyLong())).thenReturn(nullable(Product.class));
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> service.addQuantityToProductById(1L, 15));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve reduzir quantidade estoque por id ")
    public void reduceQuantityToProductByIdTest() {
        int quantity = 3;
        Product product = getProduct();
        product.setId(1L);
        when(dao.findById(anyLong())).thenReturn(product);
        product.setQuantity(product.getQuantity() - quantity);
        service.reduceQuantityToProductById(1L, quantity);
        verify(dao, times(1)).update(product);
    }

    @Test
    @DisplayName("Deve lancar erro quantidade invalida ao tentar remover quantidade ao produto")
    public void exceptionInvalidQuantityToReduceQuantity() {
        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class, () -> service.reduceQuantityToProductById(1L, 0));
        assertEquals("Invalid quantity", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lancar erro reduzir quantidade produto nulo")
    public void exceptionReduceQuantityToProductNull() {
        when(dao.findById(anyLong())).thenReturn(nullable(Product.class));
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> service.reduceQuantityToProductById(1L, 5));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lancar erro reduzir quantidade maior que estoque")
    public void exceptionInvalidQuantityReduceQuantity() {
        int quantity = 15;
        Product product = getProduct();
        product.setId(1L);
        when(dao.findById(anyLong())).thenReturn(product);
        InsuficientQuantityOfProductException exception = assertThrows(InsuficientQuantityOfProductException.class, () -> service.reduceQuantityToProductById(1L, quantity));
        assertEquals("Insufficient quantity of product in stock", exception.getMessage());
    }

    public static Product getProduct() {
        Category category = new Category(1L, "Matinais");

        Product product = new Product();
        product.setName("Bolacha trakinas");
        product.setPrice(new BigDecimal("4.50"));
        product.setCategory(category);
        product.setQuantity(10);
        return product;
    }

}
