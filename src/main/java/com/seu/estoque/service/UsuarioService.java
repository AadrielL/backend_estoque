package com.seu.estoque.service;

import com.seu.estoque.model.Role;
import com.seu.estoque.model.Usuario;
import com.seu.estoque.model.UsuarioCadastroDTO;
import com.seu.estoque.model.UsuarioDTO;
import com.seu.estoque.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
            .map(u -> new UsuarioDTO(u.getId(), u.getUsername(), u.getRole()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void autorizarComoAdmin(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setRole(Role.ADMIN);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void revogarAdmin(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setRole(Role.VIEWER);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void cadastrarUsuario(UsuarioCadastroDTO cadastroDTO) {
        if (usuarioRepository.findByUsername(cadastroDTO.getUsername()) != null) {
            throw new RuntimeException("Username já existe");
        }
        
        Usuario usuario = new Usuario();
        usuario.setUsername(cadastroDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(cadastroDTO.getPassword()));
        usuario.setRole(Role.VIEWER); // Sempre começa como VIEWER
        usuario.setEnabled(true);
        usuarioRepository.save(usuario);
    }
}