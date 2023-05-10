package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.OrderDAO;
import br.com.sinqia.model.Order;

import java.time.LocalDate;

public class OrderTest {
    public static void main(String[] args) {
        OrderDAO orderDAO = DaoFactory.createOrderDAO();

        System.out.println("==== Order, findAll ====");
        orderDAO.findAll().forEach(System.out::println);

        System.out.println("\n==== Order, findById ====");
        System.out.println(orderDAO.findById(1L));

        System.out.println("\n==== Order, save ====");
        Order order = new Order();
        order.setCreatedAt(LocalDate.of(2023, 3, 2));
        System.out.println(orderDAO.save(order));

        System.out.println("\n==== Order, deleteById ====");
        orderDAO.deleteById(order.getId());
        orderDAO.findAll().forEach(System.out::println);

    }
}
