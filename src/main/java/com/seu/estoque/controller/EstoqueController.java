package com.seu.estoque.controller;

import com.seu.estoque.model.Estoque;
import com.seu.estoque.model.Usuario;
import com.seu.estoque.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/hospital/{hospital}")
    public ResponseEntity<List<Estoque>> listarPorHospital(@PathVariable String hospital) {
        return ResponseEntity.ok(estoqueService.listarPorHospital(hospital));
    }

    @PostMapping
    public ResponseEntity<Estoque> criarEstoque(@RequestBody Estoque estoque, @AuthenticationPrincipal Usuario usuario) {
        Estoque salvo = estoqueService.criarEstoque(estoque, usuario);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping("/{id}/alterar-quantidade")
    public ResponseEntity<Estoque> alterarQuantidade(
            @PathVariable Long id,
            @RequestParam Integer quantidade,
            @RequestParam String acao,
            @AuthenticationPrincipal Usuario usuario) {
        Estoque salvo = estoqueService.atualizarEstoque(id, quantidade, acao, usuario);
        return ResponseEntity.ok(salvo);
    }
}