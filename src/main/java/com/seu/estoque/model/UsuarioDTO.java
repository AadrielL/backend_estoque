// UsuarioDTO.java (corrigido)
package com.seu.estoque.model;

public class UsuarioDTO {
    private final Long id;
    private final String username;
    private final String role; // Agora é String!

    public UsuarioDTO(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role.name(); // Converte enum para string ("ADMIN" ou "VIEWER")
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}