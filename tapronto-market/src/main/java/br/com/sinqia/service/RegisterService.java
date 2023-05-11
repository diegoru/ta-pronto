package br.com.sinqia.service;

import br.com.sinqia.model.Register;

import java.util.List;

public interface RegisterService {

    List<Register> findAll();
    Register findById(Long id);
    Register save(Register register);
    Register update(Long id, Register register);
    void deleteById(Long id);
}
