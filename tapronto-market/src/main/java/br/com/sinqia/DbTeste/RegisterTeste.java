package br.com.sinqia.DbTeste;

import br.com.sinqia.dao.DaoFactory;
import br.com.sinqia.dao.RegisterDAO;

public class RegisterTeste {

    public static void main(String[] args) {

        RegisterDAO registerDAO = DaoFactory.createRegisterDAO();

        System.out.println("=== Register, findAll ===");
        registerDAO.findAll().forEach(System.out::println);

        System.out.println("=== Register, findById ===");
        System.out.println(registerDAO.findById(1L));
    }
}
