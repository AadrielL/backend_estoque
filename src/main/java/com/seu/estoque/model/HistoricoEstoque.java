package com.seu.estoque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historico_estoque")
public class HistoricoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String acao; // "ADICAO", "REMOCAO", "AJUSTE", "CRIAÇÃO", "ATUALIZAÇÃO"

    private Integer quantidadeAntes;

    private Integer quantidadeDepois;

    private LocalDateTime dataHora;
}