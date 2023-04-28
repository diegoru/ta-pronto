package br.com.sinqia.repository;

import br.com.sinqia.exception.CargoNaoEncontradoException;
import br.com.sinqia.model.Cargo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CargoRepository {

    private static final List<Cargo> cargos = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong();

    public List<Cargo> findAll() {
        return cargos;
    }

    public Cargo findById(Long id) {
        return cargos.stream()
                .filter(idCargo -> id.equals(idCargo.getId()))
                .findAny()
                .orElseThrow(CargoNaoEncontradoException::new);
    }

    public Cargo save(Cargo cargo) {
        cargo.setId(counter.incrementAndGet());
        cargos.add(cargo);
        return cargo;
    }

    public Cargo update(Long id, Cargo cargo) {
        Cargo cargoAtual = findById(id);
        cargoAtual.setDescricao(cargo.getDescricao());
        cargoAtual.setAliquota(cargo.getAliquota());
        return cargoAtual;
    }

    public boolean existById(Long id) {
        return cargos.stream().anyMatch(idFuncioanrio -> id.equals(idFuncioanrio.getId()));
    }

    public void delete(Long id) {
        if (!existById(id)) {
            throw new CargoNaoEncontradoException();
        }
        Cargo cargo = findById(id);
        cargos.remove(cargo);
    }
}
