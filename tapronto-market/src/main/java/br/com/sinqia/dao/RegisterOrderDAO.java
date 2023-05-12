package br.com.sinqia.dao;

import br.com.sinqia.model.RegisterOrder;

import java.util.Optional;

public interface RegisterOrderDAO {
    Optional<RegisterOrder> findById(Long id);
    RegisterOrder save(RegisterOrder registerOrder);
    void deleteById(Long id);
}
