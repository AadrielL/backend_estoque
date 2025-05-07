package com.seu.estoque.service;

import com.seu.estoque.model.Estoque;
import com.seu.estoque.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    // Listar todos os itens
    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    // Listar por hospital (nome do método igual ao controller)
    public List<Estoque> listarPorHospital(String hospital) {
        return estoqueRepository.findByHospital(hospital);
    }

    // Buscar por ID
    public Optional<Estoque> buscarPorId(Long id) {
        return estoqueRepository.findById(id);
    }

    // Buscar por produto (nome exato)
    public Optional<Estoque> buscarPorProduto(String produto) {
        return estoqueRepository.findByProduto(produto);
    }

    // Adicionar ou atualizar um item
    public Estoque salvar(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    // Atualizar um item existente (método necessário para o controller)
    public Estoque atualizar(Long id, Estoque estoqueAtualizado) {
        Estoque estoqueExistente = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        estoqueExistente.setProduto(estoqueAtualizado.getProduto());
        estoqueExistente.setQuantidade(estoqueAtualizado.getQuantidade());
        estoqueExistente.setHospital(estoqueAtualizado.getHospital());

        return estoqueRepository.save(estoqueExistente);
    }

    // Atualizar quantidade de um produto específico
    public Estoque atualizarQuantidade(Long id, int quantidade) {
        Estoque estoque = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        return estoqueRepository.save(estoque);
    }

    // Deletar item (caso queira reativar no futuro)
    public void deletar(Long id) {
        estoqueRepository.deleteById(id);
    }
}