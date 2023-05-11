package br.com.sinqia.dao;

import br.com.sinqia.model.Register;

import java.util.List;
import java.util.Optional;

public interface RegisterDAO {

    List<Register> findAll();
    Optional<Register> findById(Long id);
    Register save(Register register);
    void deleteById(Long id);
}
