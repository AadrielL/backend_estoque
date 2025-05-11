// UsuarioCadastroDTO.java (para cadastro)
package com.seu.estoque.model;

public class UsuarioCadastroDTO {
    private String username;
    private String password;    // Só este DTO tem senha
    private String email;

    // Construtor vazio para deserialização
    public UsuarioCadastroDTO() {}

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}