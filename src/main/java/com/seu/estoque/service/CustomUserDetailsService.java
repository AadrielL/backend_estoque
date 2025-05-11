
package com.seu.estoque.service;

import com.seu.estoque.model.Usuario;
import com.seu.estoque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        String role = "ROLE_" + usuario.getRole().name();
        return new org.springframework.security.core.userdetails.User(
            usuario.getEmail(),
            usuario.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}