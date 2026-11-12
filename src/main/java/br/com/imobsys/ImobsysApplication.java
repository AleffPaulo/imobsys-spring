package br.com.imobsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // Necessário para habilitar o @WebFilter (AuthFilter)
public class ImobsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImobsysApplication.class, args);
	}

}