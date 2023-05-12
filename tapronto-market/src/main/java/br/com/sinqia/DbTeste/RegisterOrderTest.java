package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.OrderDAO;
import br.com.sinqia.dao.RegisterDAO;
import br.com.sinqia.dao.RegisterOrderDAO;
import br.com.sinqia.model.Order;
import br.com.sinqia.model.Register;
import br.com.sinqia.model.RegisterOrder;

public class RegisterOrderTest {
    public static void main(String[] args) {
        RegisterDAO registerDAO = DaoFactory.createRegisterDAO();
        OrderDAO orderDAO =DaoFactory.createOrderDAO();
        RegisterOrderDAO registerOrderDAO = DaoFactory.createRegisterOrderDAO();

        System.out.println("=== RegisterOrder, findById ==");
        System.out.println(registerOrderDAO.findById(1L));

        System.out.println("\n=== RegisterOrder, save ===");

        Register register = registerDAO.findById(1L).get();
        Order order = orderDAO.findById(1L).get();

        RegisterOrder registerOrder = new RegisterOrder();
        registerOrder.setRegister(register);
        registerOrder.setOrder(order);

        System.out.println(registerOrderDAO.save(registerOrder));

        System.out.println("\n=== RegisterOrder, deleteById ===");
        registerOrderDAO.deleteById(registerOrder.getId());
    }
}
