package br.com.sinqia.service.impl;

import br.com.sinqia.dao.RegisterOrderDAO;
import br.com.sinqia.exceptions.RegisterOrderNotFoundException;
import br.com.sinqia.model.RegisterOrder;
import br.com.sinqia.service.RegisterOrderService;

import java.util.Optional;

public class RegisterOrderServiceImpl implements RegisterOrderService {

    private RegisterOrderDAO registerOrderDAO;

    @Override
    public RegisterOrder findById(Long id) {
        Optional<RegisterOrder> optionalRegisterOrder = registerOrderDAO.findById(id);
        optionalRegisterOrder.orElseThrow(RegisterOrderNotFoundException::new);
        return optionalRegisterOrder.get();
    }

    @Override
    public RegisterOrder save(RegisterOrder registerOrder) {
        if (registerOrder == null) throw new RuntimeException("Unexpected error! No register order to save");
        return registerOrderDAO.save(registerOrder);
    }

    @Override
    public RegisterOrder update(Long id, RegisterOrder registerOrder) {
        if (registerOrder == null) throw new RuntimeException("Unexpected error! No register order to update");

        Optional<RegisterOrder> optionalRegisterOrder = registerOrderDAO.findById(id);
        optionalRegisterOrder.orElseThrow(RegisterOrderNotFoundException::new);

        RegisterOrder updateRegisterOrder = optionalRegisterOrder.get();
        updateRegisterOrder.setRegister(registerOrder.getRegister());
        updateRegisterOrder.setOrder(registerOrder.getOrder());

        return save(updateRegisterOrder);
    }

    @Override
    public void deleteById(Long id) {
        registerOrderDAO.findById(id).orElseThrow(RegisterOrderNotFoundException::new);
        registerOrderDAO.deleteById(id);
    }
}
