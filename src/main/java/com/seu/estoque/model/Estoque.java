package com.seu.estoque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hospital;

    private String categoria;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario ultimoUsuarioAlteracao;

    private LocalDateTime dataUltimaAlteracao;
}