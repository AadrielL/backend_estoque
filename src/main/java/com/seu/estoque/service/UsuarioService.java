package com.seu.estoque.service;

import com.seu.estoque.model.Usuario;
import com.seu.estoque.model.UsuarioCadastroDTO;
import com.seu.estoque.model.UsuarioDTO;
import com.seu.estoque.model.PerfilUsuario;
import com.seu.estoque.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Métodos existentes
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Métodos para DTOs
    public List<UsuarioDTO> listarTodosDTO() {
        return usuarioRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO converterParaDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil().name()
        );
    }

    // Métodos de cadastro e autenticação
    @Transactional
    public Usuario cadastrarUsuario(UsuarioCadastroDTO cadastroDTO) {
        if (!cadastroDTO.getSenha().equals(cadastroDTO.getConfirmarSenha())) {
            throw new RuntimeException("Senha e confirmação não coincidem");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroDTO.getNome());
        usuario.setEmail(cadastroDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroDTO.getSenha()));
        usuario.setPerfil(PerfilUsuario.VIEWER); // Perfil padrão

        return usuarioRepository.save(usuario);
    }

    // Métodos de administração
    @Transactional
    public void autorizarComoAdmin(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setPerfil(PerfilUsuario.ADMIN);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void revogarAdmin(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setPerfil(PerfilUsuario.VIEWER);
        usuarioRepository.save(usuario);
    }

    // Métodos auxiliares
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByEmail(username) // username = email
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}