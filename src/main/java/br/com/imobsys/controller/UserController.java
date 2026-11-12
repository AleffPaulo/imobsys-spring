package br.com.imobsys.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.imobsys.model.User;
import br.com.imobsys.repository.UserRepository;
import br.com.imobsys.service.AuthService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AuthService auth;

	@GetMapping("/list")
	public List<User> list() {
		return userRepo.findAll();
	}

	@PostMapping("/add")
	public void add(@RequestBody User user) {
		auth.register(user.getUsername(), user.getPassword(), user.getRole());
	}
}