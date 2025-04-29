package com.seu.estoque.repository;

import com.seu.estoque.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    List<Estoque> findByHospital(String hospital);  // NOVO
}
