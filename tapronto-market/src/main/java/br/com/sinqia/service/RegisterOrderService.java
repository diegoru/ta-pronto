package br.com.sinqia.service;

import br.com.sinqia.model.RegisterOrder;

public interface RegisterOrderService {
    RegisterOrder findById(Long id);
    RegisterOrder save(RegisterOrder registerOrder);
    RegisterOrder update(Long id, RegisterOrder registerOrder);
    void deleteById(Long id);
}
