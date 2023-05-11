package br.com.sinqia.service;

import br.com.sinqia.model.Cashier;

import java.util.List;
import java.util.Optional;

public interface CashierService {

    List<Cashier> findAll();
    Cashier findById(Long id);
    Cashier save(Cashier cashier);
    Cashier update(Long id, Cashier cashier);
    void deleteById(Long id);

}
