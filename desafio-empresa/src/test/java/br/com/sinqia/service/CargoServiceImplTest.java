package br.com.sinqia.service;

import br.com.sinqia.exception.CargoNaoEncontradoException;
import br.com.sinqia.model.Cargo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CargoServiceImplTest {

    private CargoService cargoService = new CargoServiceImpl();

    @BeforeAll
    public static void carregarDados() {
        Carregar carregar = new Carregar();
        carregar.dados();
    }

    @Test
    public void deveRetornarCargoPorId() {
        assertEquals("Desenvolvedor", cargoService.findById(1L).getDescricao());
    }

    @Test
    void deveRetornarCargoNaoEncontradoException() {
        assertThrows(CargoNaoEncontradoException.class, () -> {
            cargoService.findById(4L);
        });
    }

    @Test
    void deveRetornarCargoCadastrado() {
        Cargo gerente = cargoService.save(new Cargo("Gerente", 0.08));
        assertEquals("Gerente", cargoService.findById(4L).getDescricao());
    }

    @Test
    void deveRetornarCargoUpdate() {
        Cargo cargo = new Cargo("Coordenador", 0.1);
        cargoService.update(3L, cargo);
        assertEquals("Coordenador", cargoService.findById(3L).getDescricao());
    }

    @Test
    void deveRetornarCargoNaoEncontradoExceptionUpdate() {
        assertThrows(CargoNaoEncontradoException.class, () -> {
            Cargo cargo = new Cargo("Coordenador", 0.1);
            cargoService.update(5L, cargo);
        });
    }

}