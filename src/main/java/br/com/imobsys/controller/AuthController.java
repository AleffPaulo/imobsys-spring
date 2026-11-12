package br.com.imobsys.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.imobsys.model.User;
import br.com.imobsys.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService auth;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		User user = auth.login(username, password);
		if (user != null) {
			session.setAttribute("user", user);
			return ResponseEntity.ok(user.getRole());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.invalidate();
		response.sendRedirect("/login.html");
		return ResponseEntity.ok().build();
	}
}