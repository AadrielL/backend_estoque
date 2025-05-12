// UsuarioCadastroDTO.java (para cadastro)
package com.seu.estoque.model;

public class UsuarioCadastroDTO {
    private String username;
    private String password;    // Só este DTO tem senha
    

    // Construtor vazio para deserialização
    public UsuarioCadastroDTO() {}

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
}