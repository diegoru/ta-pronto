package br.com.sinqia.service.impl;

import br.com.sinqia.dao.CashierDAO;
import br.com.sinqia.exceptions.CashierNotFoundException;
import br.com.sinqia.model.Cashier;
import br.com.sinqia.service.CashierService;

import java.util.List;
import java.util.Optional;

public class CashierServiceImpl implements CashierService {

    private CashierDAO cashierDAO;

    @Override
    public List<Cashier> findAll() {
        return cashierDAO.findAll();
    }

    @Override
    public Cashier findById(Long id) {
        Optional<Cashier> cashier = cashierDAO.findById(id);
        cashier.orElseThrow(CashierNotFoundException::new);
        return cashier.get();
    }

    @Override
    public Cashier save(Cashier cashier) {
        if (cashier == null) throw new RuntimeException("Unexpected error! No cashier to save");
        return cashierDAO.save(cashier);
    }

    @Override
    public Cashier update(Long id, Cashier cashier) {
        if (cashier == null)  throw new RuntimeException("Unexpected error! No cashier to update");
        Optional<Cashier> optionalCashier = cashierDAO.findById(id);
        optionalCashier.orElseThrow(CashierNotFoundException::new);
        Cashier getCashier = optionalCashier.get();
        getCashier.setOpen(cashier.isOpen());
        return cashierDAO.save(getCashier);
    }

    @Override
    public void deleteById(Long id) {
        cashierDAO.findById(id).orElseThrow(CashierNotFoundException::new);
        cashierDAO.deletebyId(id);
    }

}
