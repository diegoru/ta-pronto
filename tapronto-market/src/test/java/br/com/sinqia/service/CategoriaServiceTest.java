package br.com.sinqia.service;

import br.com.sinqia.dao.CategoriaDAO;
import br.com.sinqia.dao.CategoriaDAOImpl;
import br.com.sinqia.exceptions.CategoriaNaoEncontradaException;
import br.com.sinqia.model.Categoria;
import br.com.sinqia.model.dto.CategoriaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @InjectMocks
    CategoriaServiceImpl service;
    @Mock
    CategoriaDAOImpl dao;

    @Test
    @DisplayName("Deve cadastrar uma categoria")
    public void deveCadastrar() {
        when(dao.save(any(Categoria.class))).thenReturn(getCategoria());
        assertEquals(getCategoriaDTO(),service.save(getCategoriaDTO()));
    }

    @Test
    @DisplayName("Deve retornar categoria pelo id")
    public void deveRetornarPorId() {
        Long id = 1L;
        when(dao.findById(anyLong())).thenReturn(Optional.of(getCategoria()));
        assertEquals(getCategoriaDTO(), service.findById(id));
    }

    @Test
    @DisplayName("Deve lancar erro categoria por id não encontrada")
    public void deveLancarErroCategoriaNaoEncontradaPeloId() {
        Long id = 1L;
        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CategoriaNaoEncontradaException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Deve remover categoria pelo id")
    public void deveRemoverPeloId() {
        Long id = 1L;
        when(dao.findById(anyLong())).thenReturn(Optional.of(getCategoria()));
        service.delete(id);
        verify(dao, times(1)).delete(getCategoria());
    }

    @Test
    @DisplayName("Deve lancar erro categoria nao encontrada ao tentar remover")
    public void deveLancarErroCategoriaNaoEncontradaAoDeletar() {
        Long id = 1L;
        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CategoriaNaoEncontradaException.class, () -> service.delete(id));
    }

    @Test
    @DisplayName("Deve atualizar categoria")
    public void deveAtualizar() {
        Long id = 1L;

        Categoria categoria = getCategoria();

        when(dao.findById(anyLong())).thenReturn(Optional.of(categoria));
        when(dao.save(any(Categoria.class))).thenReturn(categoria);

        Categoria categoriaAtualizada = categoria;
        categoriaAtualizada.setDescricao("Padaria");

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setDescricao(categoriaAtualizada.getDescricao());

        assertEquals(categoriaDTO, service.update(id, categoriaDTO));
    }

    @Test
    @DisplayName("Deve lancar erro categoria não encontrada")
    public void deveLancarErroCategoriaNaoEncontradaAoAtualizar() {
        Long id = 1L;
        Categoria categoria = getCategoria();

        when(dao.findById(anyLong())).thenReturn(Optional.empty());

        Categoria categoriaAtualizada = categoria;
        categoriaAtualizada.setDescricao("Padaria");

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setDescricao(categoriaAtualizada.getDescricao());

        assertThrows(CategoriaNaoEncontradaException.class, () -> service.update(id, categoriaDTO));
    }

    @Test
    @DisplayName("Deve listar categorias")
    public void deveListar() {
        List<Categoria> listaCategorias = Arrays.asList(getCategoria());
        when(dao.findAll()).thenReturn(listaCategorias);
        List<CategoriaDTO> listaCategoriasDTO = listaCategorias.stream().map(categoria -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setDescricao(categoria.getDescricao());
            return categoriaDTO;
        }).collect(Collectors.toList());
        assertEquals(listaCategoriasDTO, service.findAll());
    }


    private static Categoria getCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setDescricao("Matinais");
        return categoria;
    }
    private static CategoriaDTO getCategoriaDTO() {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setDescricao("Matinais");
        return categoriaDTO;
    }

}
