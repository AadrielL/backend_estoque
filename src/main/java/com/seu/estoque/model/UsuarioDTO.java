package com.seu.estoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    private String nome;

    private String email;

    private String perfil; // Pode ser o nome do enum PerfilUsuario como String
}