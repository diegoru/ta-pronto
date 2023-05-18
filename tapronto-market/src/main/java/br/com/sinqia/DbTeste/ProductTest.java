package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.CategoryDAO;
import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.ProductDAO;
import br.com.sinqia.model.Category;
import br.com.sinqia.model.Product;

import java.math.BigDecimal;

public class ProductTest {
    public static void main(String[] args) {
        ProductDAO productDAO = DaoFactory.createProductDao();
        CategoryDAO categoryDAO = DaoFactory.createCategoryDao();

        System.out.println("==== Product, save ====");
        Category category = categoryDAO.findById(2L).get();
        Product product = new Product();
        product.setName("Leite Ninho");
        product.setPrice(new BigDecimal("5.6"));
        product.setQuantity(4);
        product.setCategory(category);
        productDAO.save(product);
    }
}
