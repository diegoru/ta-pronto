package br.com.sinqia.service;

import br.com.sinqia.exception.CargoNaoEncontradoException;
import br.com.sinqia.model.Cargo;
import br.com.sinqia.repository.CargoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class CargoServiceImplTest {

    @InjectMocks
    private CargoServiceImpl cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @Test
    public void deveRetornarListaCargo() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setDescricao("Desenvolvedor");
        cargo.setAliquota(0.05);
        Mockito.when(cargoRepository.findAll()).thenReturn(Arrays.asList(cargo));
        assertEquals(Arrays.asList(cargo), cargoService.findAll());
    }


    @Test
    public void deveRetornarCargoPorId() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setDescricao("Desenvolvedor");
        cargo.setAliquota(0.05);
        Mockito.when(cargoRepository.findById(Mockito.anyLong())).thenReturn(cargo);
        assertEquals("Desenvolvedor", cargoService.findById(1L).getDescricao());
    }


    @Test
    void deveRetornarCargoNaoEncontradoException() {
        Mockito.when(cargoRepository.findById(Mockito.anyLong())).thenThrow(CargoNaoEncontradoException.class);
        assertThrows(CargoNaoEncontradoException.class, () -> cargoService.findById(1L));
    }

    @Test
    void deveRetornarCargoCadastrado() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setDescricao("Desenvolvedor");
        cargo.setAliquota(0.05);
        Mockito.when(cargoRepository.save(Mockito.any(Cargo.class))).thenReturn(cargo);
        assertEquals(cargo, cargoService.save(cargo));
    }


    @Test
    void deveRetornarCargoUpdate() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setDescricao("Desenvolvedor");
        cargo.setAliquota(0.05);

        Cargo cargoAtualizado = new Cargo();
        cargoAtualizado.setDescricao("Desenvolvedor");
        cargoAtualizado.setAliquota(0.10);

        Mockito.when(cargoRepository.update(Mockito.anyLong(), Mockito.any(Cargo.class))).thenReturn(cargoAtualizado);
        assertEquals(0.10, cargoService.update(1L, cargoAtualizado).getAliquota());
    }

    @Test
    void deveRetornarCargoNaoEncontradoExceptionUpdate() {
        Cargo cargoAtualizado = new Cargo();
        cargoAtualizado.setDescricao("Desenvolvedor");
        cargoAtualizado.setAliquota(0.10);
        Mockito.when(cargoRepository.update(Mockito.anyLong(), Mockito.any(Cargo.class))).thenThrow(CargoNaoEncontradoException.class);
        assertThrows(CargoNaoEncontradoException.class, () -> cargoService.update(1L, cargoAtualizado));

    }

    @Test
    public void deveriaRetornarClienteDeletado() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setDescricao("Desenvolvedor");
        cargo.setAliquota(0.05);

        cargoService.delete(1L);

        Mockito.verify(cargoRepository).delete(1L);
    }
}