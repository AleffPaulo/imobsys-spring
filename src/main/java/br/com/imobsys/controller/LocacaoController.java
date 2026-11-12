package br.com.imobsys.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.imobsys.model.Locacao;
import br.com.imobsys.repository.LocacaoRepository;

@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

	@Autowired
	private LocacaoRepository locacaoRepo;

	@GetMapping("/list")
	public List<Locacao> list(@RequestParam(required = false) String status) {
		if (status != null && !status.isEmpty() && !status.equals("TODOS")) {
			return locacaoRepo.findByStatusIgnoreCase(status);
		}
		return locacaoRepo.findAll();
	}

	@PostMapping("/add")
	public void add(@RequestBody Locacao locacao) {
		locacaoRepo.save(locacao);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		locacaoRepo.deleteById(id);
	}
}