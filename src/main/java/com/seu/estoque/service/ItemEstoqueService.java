package com.seu.estoque.service;

import com.seu.estoque.model.ItemEstoque;
import com.seu.estoque.repository.ItemEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemEstoqueService {

    @Autowired
    private ItemEstoqueRepository repository;

    public ItemEstoque adicionarOuAtualizar(ItemEstoque novoItem) {
        Optional<ItemEstoque> existente = repository.findByNomeAndCategoria(novoItem.getNome(), novoItem.getCategoria());
        if (existente.isPresent()) {
            ItemEstoque item = existente.get();
            item.setQuantidade(item.getQuantidade() + novoItem.getQuantidade());
            return repository.save(item);
        } else {
            return repository.save(novoItem);
        }
    }

    public ItemEstoque removerQuantidade(String nome, String categoria, int quantidade) {
        Optional<ItemEstoque> existente = repository.findByNomeAndCategoria(nome, categoria);
        if (existente.isPresent()) {
            ItemEstoque item = existente.get();
            item.setQuantidade(item.getQuantidade() - quantidade);
            return repository.save(item);
        }
        throw new RuntimeException("Item não encontrado");
    }
}
