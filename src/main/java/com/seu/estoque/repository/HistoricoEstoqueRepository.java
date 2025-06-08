package com.seu.estoque.repository;

import com.seu.estoque.model.HistoricoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoEstoqueRepository extends JpaRepository<HistoricoEstoque, Long> {
    List<HistoricoEstoque> findByEstoqueIdOrderByDataHoraDesc(Long estoqueId);
    List<HistoricoEstoque> findByUsuarioIdOrderByDataHoraDesc(Long usuarioId);
}