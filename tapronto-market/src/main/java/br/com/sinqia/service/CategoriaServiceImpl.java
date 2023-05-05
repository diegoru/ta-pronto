package br.com.sinqia.service;

import br.com.sinqia.dao.CategoriaDAO;
import br.com.sinqia.dao.CategoriaDAOImpl;
import br.com.sinqia.exceptions.CategoriaNaoEncontradaException;
import br.com.sinqia.model.Categoria;
import br.com.sinqia.model.dto.CategoriaDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaDAO dao;

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setDescricao(categoriaDTO.getDescricao());

        Categoria categoriaSave = dao.save(categoria);

        CategoriaDTO categoriaDTOSave = new CategoriaDTO();
        categoriaDTOSave.setDescricao(categoriaSave.getDescricao());

        return categoriaDTOSave;
    }

    @Override
    public CategoriaDTO findById(Long id) {
        try {
            Optional<Categoria> categoria = dao.findById(id);
            categoria.orElseThrow(CategoriaNaoEncontradaException::new);
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setDescricao(categoria.get().getDescricao());
            return categoriaDTO;
        } catch (CategoriaNaoEncontradaException e) {
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Optional<Categoria> categoria = dao.findById(id);
            categoria.orElseThrow(CategoriaNaoEncontradaException::new);
            dao.delete(categoria.get());
        } catch (CategoriaNaoEncontradaException e) {
            throw e;
        }
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        try {
            Optional<Categoria> categoria = dao.findById(id);
            categoria.orElseThrow(CategoriaNaoEncontradaException::new);

            Categoria categoriaAtualizada = dao.save(categoria.get());

            CategoriaDTO categoriaDTOAtualizada = new CategoriaDTO();
            categoriaDTOAtualizada.setDescricao(categoriaAtualizada.getDescricao());

            return categoriaDTOAtualizada;
        } catch (CategoriaNaoEncontradaException e) {
            throw e;
        }
    }

    @Override
    public List<CategoriaDTO> findAll() {
        return dao.findAll().stream().map(categoria -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setDescricao(categoria.getDescricao());
            return categoriaDTO;
        }).collect(Collectors.toList());
    }


}
