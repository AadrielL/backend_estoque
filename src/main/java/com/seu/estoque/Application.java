package com.seu.estoque;

import com.seu.estoque.model.Usuario;
import com.seu.estoque.model.Role;
import com.seu.estoque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    @Value("${spring.datasource.url:NOT_SET}")
    private String datasourceUrl;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            // Imprime a URL do datasource para debug
            System.out.println("Datasource URL: " + datasourceUrl);

            // Cria admin se não existir
            if (repository.findByUsername("admin") == null) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("senhaAdmin"));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                repository.save(admin);
            }

            // Cria viewer se não existir
            if (repository.findByUsername("viewer") == null) {
                Usuario viewer = new Usuario();
                viewer.setUsername("viewer");
                viewer.setPassword(passwordEncoder.encode("senhaViewer"));
                viewer.setRole(Role.VIEWER);
                viewer.setEnabled(true);
                repository.save(viewer);
            }
        };
    }
}