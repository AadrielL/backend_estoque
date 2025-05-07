package com.seu.estoque.controller;

import com.seu.estoque.model.Estoque;
import com.seu.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/item-estoque")
@CrossOrigin(origins = "*")

public class ItemEstoqueController{

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public List<Estoque> listar() {
        return estoqueService.listarTodos();
    }

    @GetMapping("/hospital/{hospital}")
    public List<Estoque> listarPorHospital(@PathVariable String hospital) {
        return estoqueService.listarPorHospital(hospital);
    }

    @PostMapping
    public Estoque salvar(@RequestBody Estoque estoque) {
        return estoqueService.salvar(estoque);
    }

   
    

    @PutMapping("/{id}")
    public Estoque atualizar(@PathVariable Long id, @RequestBody Estoque estoqueAtualizado) {
        return estoqueService.atualizar(id, estoqueAtualizado);
    }
}
    