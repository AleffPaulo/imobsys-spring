package br.com.imobsys.controller;

import java.io.IOException;
import br.com.imobsys.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();

		// Libera páginas públicas e arquivos de estilo CSS
		if (uri.endsWith("login") || uri.endsWith("login.html") || uri.endsWith(".css")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = req.getSession(false);
		User usuario = (session != null) ? (User) session.getAttribute("user") : null;

		if (usuario == null) {
			resp.sendRedirect("/login.html");
			return;
		}

		// Restrição por papel (ADMIN vs USER)
		String role = usuario.getRole();

		if ("USER".equals(role)) {
			// Usuário comum só pode acessar consulta de locações, relatórios e logout
			if (uri.contains("listarLocacoes") || uri.contains("relatorio") || uri.contains("/locacoes/list") || uri.contains("logout")) {
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect("/listarLocacoes.html");
			}
		} else if ("ADMIN".equals(role)) {
			// Admin tem acesso completo ao sistema
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect("/login.html");
		}
	}
}