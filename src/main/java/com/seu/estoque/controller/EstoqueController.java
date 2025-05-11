package com.seu.estoque.controller;

import com.seu.estoque.model.Estoque;
import com.seu.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estoque")
@CrossOrigin(origins = "*")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public List<Estoque> listarTodos() {
        return estoqueService.listarTodos();
    }

    @GetMapping("/hospital")
    public List<Estoque> listarPorHospital(@RequestParam String hospital) {
        return estoqueService.listarPorHospital(hospital);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Estoque> item = estoqueService.buscarPorId(id);
        return item.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorCategoriaEHospital(@RequestParam String categoria, @RequestParam String hospital) {
        Optional<Estoque> item = estoqueService.buscarPorCategoriaEHospital(categoria, hospital);
        return item.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Estoque item) {
        try {
            Estoque novo = estoqueService.adicionar(item);
            return ResponseEntity.ok(novo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estoque itemAtualizado) {
        try {
            Estoque atualizado = estoqueService.atualizar(id, itemAtualizado);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/somar")
    public ResponseEntity<?> somarQuantidade(
            @RequestParam String categoria,
            @RequestParam int quantidade,
            @RequestParam String hospital) {
        try {
            Estoque item = estoqueService.somarQuantidade(categoria, quantidade, hospital);
            return ResponseEntity.ok().body("Item adicionado com sucesso! A quantidade agora é: " + item.getQuantidade());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/subtrair")
    public ResponseEntity<?> subtrairQuantidade(
            @RequestParam String categoria,
            @RequestParam int quantidade,
            @RequestParam String hospital) {
        try {
            Estoque item = estoqueService.subtrairQuantidade(categoria, quantidade, hospital);
            return ResponseEntity.ok().body("Item atualizado com sucesso! A quantidade agora é: " + item.getQuantidade());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/quantidade")
    public ResponseEntity<?> atualizarQuantidade(@PathVariable Long id, @RequestParam int quantidade) {
        try {
            Estoque item = estoqueService.atualizarQuantidade(id, quantidade);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }
}