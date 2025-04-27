package com.seu.estoque.controller;

import com.seu.estoque.model.Estoque;
import com.seu.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estoque")
@CrossOrigin(origins = "*")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public List<Estoque> listar() {
        return estoqueService.listarTodos();
    }

    @PostMapping
    public Estoque salvar(@RequestBody Estoque estoque) {
        return estoqueService.salvar(estoque);
    
    }
    
    @DeleteMapping("/{id}")
public void deletar(@PathVariable Long id) {
    estoqueService.deletar(id);
}

}
