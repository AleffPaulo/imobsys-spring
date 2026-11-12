package br.com.imobsys.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.imobsys.model.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
	List<Locacao> findByStatusIgnoreCase(String status);
}