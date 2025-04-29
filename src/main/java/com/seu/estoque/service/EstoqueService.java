package com.seu.estoque.service;

import com.seu.estoque.model.Estoque;
import com.seu.estoque.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public Estoque salvar(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public void deletar(Long id) {
        estoqueRepository.deleteById(id);
    }

    public Estoque atualizar(Long id, Estoque estoqueAtualizado) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        estoque.setNome(estoqueAtualizado.getNome());
        estoque.setQuantidade(estoqueAtualizado.getQuantidade());
        estoque.setHospital(estoqueAtualizado.getHospital());
        estoque.setCategoria(estoqueAtualizado.getCategoria());
        return estoqueRepository.save(estoque);
    }

    public List<Estoque> listarPorHospital(String hospital) {
        return estoqueRepository.findByHospital(hospital);
    }
}
