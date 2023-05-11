package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.CashierDAO;
import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.model.Cashier;

public class CashierTest {
    public static void main(String[] args) {
        CashierDAO cashierDAO = DaoFactory.createCashierDAO();

        System.out.println("=== Cashier, findAll ===");
        cashierDAO.findAll().forEach(System.out::println);

        System.out.println("\n=== Cashier, findById ===");
        System.out.println(cashierDAO.findById(1L));

        System.out.println("\n=== Cashier, save ===");
        Cashier cashier = new Cashier(null, false);
        Cashier saveCashier = cashierDAO.save(cashier);
        System.out.println(saveCashier);

        System.out.println("\n=== Cashier, delete ===");
        cashierDAO.deletebyId(cashier.getId());


    }
}
