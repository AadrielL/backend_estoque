package templates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import templates.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
