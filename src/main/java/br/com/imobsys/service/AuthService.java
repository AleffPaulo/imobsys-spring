package br.com.imobsys.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.imobsys.model.Locacao;
import br.com.imobsys.model.User;
import br.com.imobsys.repository.LocacaoRepository;
import br.com.imobsys.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private LocacaoRepository locacaoRepo;

	public User login(String username, String password) {
		return userRepo.findByUsername(username)
				.filter(user -> BCrypt.checkpw(password, user.getPassword()))
				.orElse(null);
	}

	public void register(String username, String password, String role) {
		String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		User user = new User();
		user.setUsername(username);
		user.setPassword(hash);
		user.setRole(role);
		userRepo.save(user);
	}

	// Cria usuários e dados iniciais automaticamente na primeira execução
	@PostConstruct
	public void initDefaultData() {
		if (userRepo.count() == 0) {
			register("admin", "admin123", "ADMIN");
			register("usuario", "123456", "USER");
		}
		if (locacaoRepo.count() == 0) {
			locacaoRepo.save(new Locacao(null, "Rua João da Escóssia, 145 - Nova Betânia", "Apartamento", "Carlos Eduardo", 1350.00, "ATIVO"));
			locacaoRepo.save(new Locacao(null, "Av. Presidente Dutra, 890 - Alto de São Manoel", "Comercial", " Maria Fernanda", 2200.00, "ATIVO"));
			locacaoRepo.save(new Locacao(null, "Rua Frei Miguelinho, 42 - Doze Anos", "Casa", "Sem Inquilino", 950.00, "DISPONIVEL"));
		}
	}
}