package com.seu.estoque.controller;

import com.seu.estoque.model.UsuarioCadastroDTO;
import com.seu.estoque.model.UsuarioDTO;
import com.seu.estoque.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @PutMapping("/autorizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> autorizarAdmin(@PathVariable Long id) {
        usuarioService.autorizarComoAdmin(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/revogar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> revogarAdmin(@PathVariable Long id) {
        usuarioService.revogarAdmin(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioCadastroDTO cadastroDTO) {
        usuarioService.cadastrarUsuario(cadastroDTO);
        return ResponseEntity.ok().build();
    }
}