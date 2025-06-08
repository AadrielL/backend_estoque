package com.seu.estoque.controller;

import com.seu.estoque.model.Usuario;
import com.seu.estoque.model.UsuarioDTO;        
import com.seu.estoque.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> perfil(@AuthenticationPrincipal Usuario usuario) {
        UsuarioDTO usuarioDTO = usuarioService.converterParaDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }
}   