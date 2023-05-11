package br.com.sinqia.dao;

import br.com.sinqia.model.Cashier;

import java.util.List;
import java.util.Optional;

public interface CashierDAO {
    List<Cashier> findAll();
    Optional<Cashier> findById(Long id);
    Cashier save(Cashier cashier);
    void deletebyId(Long id);
}
