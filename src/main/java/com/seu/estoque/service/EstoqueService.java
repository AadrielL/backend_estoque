package com.seu.estoque.service;

import com.seu.estoque.model.Estoque;
import com.seu.estoque.model.HistoricoEstoque;
import com.seu.estoque.model.Usuario;
import com.seu.estoque.repository.EstoqueRepository;
import com.seu.estoque.repository.HistoricoEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final HistoricoEstoqueRepository historicoEstoqueRepository;

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public List<Estoque> listarPorHospital(String hospital) {
        return estoqueRepository.findByHospital(hospital);
    }

    public Optional<Estoque> buscarPorId(Long id) {
        return estoqueRepository.findById(id);
    }

    public Optional<Estoque> buscarPorCategoriaEHospital(String categoria, String hospital) {
        return estoqueRepository.findByCategoriaAndHospital(categoria.trim().toLowerCase(), hospital);
    }

    public Estoque criarEstoque(Estoque estoque, Usuario usuario) {
        if (estoque.getQuantidade() < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa.");
        }
        estoque.setCategoria(estoque.getCategoria().trim().toLowerCase());
        estoque.setUltimoUsuarioAlteracao(usuario);
        estoque.setDataUltimaAlteracao(LocalDateTime.now());
        Estoque salvo = estoqueRepository.save(estoque);

        HistoricoEstoque historico = new HistoricoEstoque();
        historico.setEstoque(salvo);
        historico.setUsuario(usuario);
        historico.setAcao("CRIAÇÃO");
        historico.setQuantidadeAntes(0);
        historico.setQuantidadeDepois(estoque.getQuantidade());
        historico.setDataHora(LocalDateTime.now());
        historicoEstoqueRepository.save(historico);

        return salvo;
    }

    public Estoque atualizarEstoque(Long id, Integer quantidadeAlterada, String acao, Usuario usuario) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        Integer quantidadeAntes = estoque.getQuantidade();
        Integer quantidadeDepois;

        if ("ADICAO".equalsIgnoreCase(acao)) {
            quantidadeDepois = quantidadeAntes + quantidadeAlterada;
        } else if ("REMOCAO".equalsIgnoreCase(acao)) {
            quantidadeDepois = quantidadeAntes - quantidadeAlterada;
            if (quantidadeDepois < 0) {
                throw new RuntimeException("Quantidade insuficiente! Disponível: " + quantidadeAntes +
                        ". Tentativa de retirar: " + quantidadeAlterada + ".");
            }
        } else {
            throw new IllegalArgumentException("Ação inválida: " + acao);
        }

        estoque.setQuantidade(quantidadeDepois);
        estoque.setUltimoUsuarioAlteracao(usuario);
        estoque.setDataUltimaAlteracao(LocalDateTime.now());
        Estoque salvo = estoqueRepository.save(estoque);

        HistoricoEstoque historico = new HistoricoEstoque();
        historico.setEstoque(salvo);
        historico.setUsuario(usuario);
        historico.setAcao(acao.toUpperCase());
        historico.setQuantidadeAntes(quantidadeAntes);
        historico.setQuantidadeDepois(quantidadeDepois);
        historico.setDataHora(LocalDateTime.now());
        historicoEstoqueRepository.save(historico);

        return salvo;
    }
}