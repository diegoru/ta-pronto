package br.com.sinqia.service.impl;

import br.com.sinqia.dao.RegisterDAO;
import br.com.sinqia.exceptions.DbException;
import br.com.sinqia.exceptions.RegisterNotFoundException;
import br.com.sinqia.model.Register;
import br.com.sinqia.service.RegisterService;

import java.util.List;
import java.util.Optional;

public class RegisterServiceImpl implements RegisterService {

    private RegisterDAO registerDAO;

    @Override
    public List<Register> findAll() {
        return registerDAO.findAll();
    }

    @Override
    public Register findById(Long id) {
        Optional<Register> optionalRegister = registerDAO.findById(id);
        optionalRegister.orElseThrow(RegisterNotFoundException::new);
        return optionalRegister.get();
    }

    @Override
    public Register save(Register register) {
        if (register == null) throw new RuntimeException("Unexpected error! No register to save");
        return registerDAO.save(register);
    }

    @Override
    public Register update(Long id, Register register) {
        if (register == null) throw new RuntimeException("Unexpected error! No register to update");
        Optional<Register> optionalRegister = registerDAO.findById(id);
        optionalRegister.orElseThrow(RegisterNotFoundException::new);
        Register getRegister = optionalRegister.get();
        getRegister.setDateTime(register.getDateTime());
        getRegister.setOpeningBalance(register.getOpeningBalance());
        getRegister.setClosedBalance(register.getClosedBalance());
        getRegister.setCashier(register.getCashier());
        return registerDAO.save(getRegister);
    }

    @Override
    public void deleteById(Long id) {
        registerDAO.findById(id).orElseThrow(RegisterNotFoundException::new);
        registerDAO.deleteById(id);
    }
}
