package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.CashierDAO;
import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.RegisterDAO;
import br.com.sinqia.model.Cashier;
import br.com.sinqia.model.Register;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class RegisterTeste {

    public static void main(String[] args) {

        RegisterDAO registerDAO = DaoFactory.createRegisterDAO();
        CashierDAO cashierDAO = DaoFactory.createCashierDAO();

        System.out.println("=== Register, findAll ===");
        registerDAO.findAll().forEach(System.out::println);

        System.out.println("\n=== Register, findById ===");
        System.out.println(registerDAO.findById(1L));

        System.out.println("\n=== Register, save ===");
        Optional<Cashier> cashier = cashierDAO.findById(1L);
        Register register = new Register();
        register.setDateTime(LocalDateTime.of(2023, 5,9,9,55));
        register.setOpeningBalance(new BigDecimal("100.0"));
        register.setClosedBalance(new BigDecimal("100.0"));
        register.setCashier(cashier.get());
        Register saveRegister = registerDAO.save(register);
        System.out.println(saveRegister);

        System.out.println("\n=== Register, delete ===");
        registerDAO.deleteById(register.getId());
    }
}
