package br.com.sinqia.service;

import br.com.sinqia.model.Cargo;

public class Carregar {
    private CargoService cargoService = new CargoServiceImpl();
    public void dados() {
        cargoService.save(new Cargo("Desenvolvedor", 0.05));
        cargoService.save(new Cargo("Analista de sistema", 0.05));
        cargoService.save(new Cargo("Departamento pessoal", 0.05));
    }
}
