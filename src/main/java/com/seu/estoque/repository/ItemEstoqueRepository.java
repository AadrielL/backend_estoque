package com.seu.estoque.repository;

import com.seu.estoque.model.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findByNomeAndCategoria(String nome, String categoria);
}
