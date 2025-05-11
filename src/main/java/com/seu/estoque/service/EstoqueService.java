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
    private EstoqueRepository repository;

    public List<Estoque> listarTodos() {
        return repository.findAll();
    }

    public List<Estoque> listarPorHospital(String hospital) {
        return repository.findByHospital(hospital);
    }

    public Optional<Estoque> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Estoque> buscarPorCategoriaEHospital(String categoria, String hospital) {
        return repository.findByCategoriaAndHospital(categoria.trim().toLowerCase(), hospital);
    }

    public Estoque adicionar(Estoque item) {
        if (item.getQuantidade() < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa.");
        }
        item.setCategoria(item.getCategoria().trim().toLowerCase());
        return repository.save(item);
    }

    public Estoque atualizar(Long id, Estoque itemAtualizado) {
        Estoque itemExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (itemAtualizado.getQuantidade() < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa.");
        }

        itemExistente.setCategoria(itemAtualizado.getCategoria().trim().toLowerCase());
        itemExistente.setQuantidade(itemAtualizado.getQuantidade());
        itemExistente.setHospital(itemAtualizado.getHospital());

        return repository.save(itemExistente);
    }

    public Estoque somarQuantidade(String categoria, int quantidade, String hospital) {
        if (quantidade < 0) {
            throw new RuntimeException("Não é permitido somar valor negativo.");
        }
        Optional<Estoque> existente = repository.findByCategoriaAndHospital(categoria.trim().toLowerCase(), hospital);
        if (existente.isPresent()) {
            Estoque item = existente.get();
            int novaQuantidade = item.getQuantidade() + quantidade;
            item.setQuantidade(novaQuantidade);
            return repository.save(item);
        }
        Estoque novo = new Estoque();
        novo.setCategoria(categoria.trim().toLowerCase());
        novo.setQuantidade(quantidade);
        novo.setHospital(hospital);
        return repository.save(novo);
    }

    public Estoque subtrairQuantidade(String categoria, int quantidade, String hospital) {
        if (quantidade < 0) {
            throw new RuntimeException("Não é permitido subtrair valor negativo.");
        }
        Optional<Estoque> existente = repository.findByCategoriaAndHospital(categoria.trim().toLowerCase(), hospital);
        if (existente.isPresent()) {
            Estoque item = existente.get();
            int novaQuantidade = item.getQuantidade() - quantidade;
            if (novaQuantidade < 0) {
                throw new RuntimeException("Quantidade insuficiente! Disponível: " + item.getQuantidade() +
                    ". Tentativa de retirar: " + quantidade + ". A operação deixaria o estoque negativo.");
            }
            item.setQuantidade(novaQuantidade);
            return repository.save(item);
        }
        throw new RuntimeException("Item não encontrado para este hospital.");
    }

    public Estoque atualizarQuantidade(Long id, int quantidade) {
        Estoque item = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        int novaQuantidade = item.getQuantidade() + quantidade;
        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade não pode ficar negativa. Quantidade atual: " +
                                     item.getQuantidade() + ", Tentativa de redução: " + Math.abs(quantidade));
        }

        item.setQuantidade(novaQuantidade);
        return repository.save(item);
    }
}