package br.com.imobsys.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "locacoes")
public class Locacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String imovel;       // Ex: Rua das Flores, 120 - Centro
	private String tipo;         // Ex: Casa, Apartamento, Comercial
	private String inquilino;    // Ex: João da Silva (ou "Sem Inquilino")
	private double valorMensal;  // Ex: 1200.00
	private String status;       // "ATIVO", "DISPONIVEL", "PENDENTE"

	public Locacao() {
	}

	public Locacao(Long id, String imovel, String tipo, String inquilino, double valorMensal, String status) {
		this.id = id;
		this.imovel = imovel;
		this.tipo = tipo;
		this.inquilino = inquilino;
		this.valorMensal = valorMensal;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImovel() {
		return imovel;
	}

	public void setImovel(String imovel) {
		this.imovel = imovel;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getInquilino() {
		return inquilino;
	}

	public void setInquilino(String inquilino) {
		this.inquilino = inquilino;
	}

	public double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(double valorMensal) {
		this.valorMensal = valorMensal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}