package com.seu.estoque.controller;

import com.seu.estoque.model.HistoricoEstoque;
import com.seu.estoque.repository.HistoricoEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoEstoqueRepository historicoRepository;

    @GetMapping("/estoque/{estoqueId}")
    public ResponseEntity<List<HistoricoEstoque>> listarPorEstoque(@PathVariable Long estoqueId) {
        return ResponseEntity.ok(historicoRepository.findByEstoqueIdOrderByDataHoraDesc(estoqueId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistoricoEstoque>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(historicoRepository.findByUsuarioIdOrderByDataHoraDesc(usuarioId));
    }
} 
    

