package com.seu.estoque.controller;

import com.seu.estoque.model.Usuario;
import com.seu.estoque.model.UsuarioDTO;
import com.seu.estoque.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> perfil(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsername(username);

        if (usuario != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getId(), 
                usuario.getUsername(),  // Agora vai funcionar com o getter adicionado
                usuario.getRole()
            );
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}