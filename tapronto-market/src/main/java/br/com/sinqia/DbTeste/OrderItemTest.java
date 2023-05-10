package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.OrderItemDAO;

public class OrderItemTest {
    public static void main(String[] args) {
        OrderItemDAO itemDAO = DaoFactory.createOrderItemDAO();

        System.out.println("==== OrderItem, findAll ====");
        itemDAO.findAll().forEach(System.out::println);
    }
}
