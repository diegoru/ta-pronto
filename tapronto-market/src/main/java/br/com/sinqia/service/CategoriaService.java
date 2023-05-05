package br.com.sinqia.service;

import br.com.sinqia.model.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaDTO save(CategoriaDTO categoriaDTO);
    CategoriaDTO findById(Long id);
    void delete(Long id);
    CategoriaDTO update(Long id, CategoriaDTO categoriaDTO);

    List<CategoriaDTO> findAll();
}
