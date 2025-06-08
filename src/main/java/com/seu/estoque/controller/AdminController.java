package com.seu.estoque.controller;

import com.seu.estoque.model.UsuarioCadastroDTO; // Import correto
import com.seu.estoque.model.UsuarioDTO;        // Import correto
import com.seu.estoque.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosDTO());
    }

    @PutMapping("/autorizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> autorizarAdmin(@PathVariable Long id) {
        usuarioService.autorizarComoAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/revogar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> revogarAdmin(@PathVariable Long id) {
        usuarioService.revogarAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody UsuarioCadastroDTO cadastroDTO) {
        usuarioService.cadastrarUsuario(cadastroDTO);
        return ResponseEntity.status(201).build();
    }
}