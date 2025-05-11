// UsuarioDTO.java (para exibição)
package com.seu.estoque.model;

public class UsuarioDTO {
    private final Long id;           // final para imutabilidade
    private final String username;
    private final String email;
    private final Role role;

    public UsuarioDTO(Long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getters (sem setters pois é somente para exibição)
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}