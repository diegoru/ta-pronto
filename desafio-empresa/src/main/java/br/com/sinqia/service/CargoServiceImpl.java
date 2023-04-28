package br.com.sinqia.service;

import br.com.sinqia.model.Cargo;
import br.com.sinqia.repository.CargoRepository;

import java.util.List;

public class CargoServiceImpl implements CargoService {
    private final CargoRepository cargoRepository = new CargoRepository();

    @Override
    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    @Override
    public Cargo findById(Long id) {
        return cargoRepository.findById(id);
    }

    @Override
    public Cargo save(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    @Override
    public Cargo update(Long id, Cargo cargo) {
        return cargoRepository.update(id, cargo);
    }

    @Override
    public void delete(Long id) {
        cargoRepository.delete(id);
    }
}
