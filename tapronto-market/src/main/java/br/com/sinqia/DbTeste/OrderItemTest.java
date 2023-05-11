package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.*;
import br.com.sinqia.model.Order;
import br.com.sinqia.model.OrderItem;
import br.com.sinqia.model.Product;

public class OrderItemTest {
    public static void main(String[] args) {
        ProductDAO productDAO = DaoFactory.createProductDao();
        OrderDAO orderDAO = DaoFactory.createOrderDAO();
        OrderItemDAO itemDAO = DaoFactory.createOrderItemDAO();

        System.out.println("=== OrderItem, findItem ===");
        itemDAO.findItemByOrderId(1L).forEach(System.out::println);

        System.out.println("\n=== OrderItem, findById ===");
        System.out.println(itemDAO.findById(1L).get());

        System.out.println("\n=== OrderItem, save ===");
        Product product = productDAO.findById(5L);
        Order order = orderDAO.findById(1L).get();
        OrderItem orderItem = new OrderItem(null, order,product,3);
        OrderItem save = itemDAO.save(orderItem);
        System.out.println(save);


    }
}
