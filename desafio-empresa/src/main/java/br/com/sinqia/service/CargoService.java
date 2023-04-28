package br.com.sinqia.service;

import br.com.sinqia.model.Cargo;

import java.util.List;

public interface CargoService {
    List<Cargo> findAll();
    Cargo findById(Long id);
    Cargo save(Cargo cargo);
    Cargo update(Long id, Cargo cargo);
    void delete(Long id);
}
