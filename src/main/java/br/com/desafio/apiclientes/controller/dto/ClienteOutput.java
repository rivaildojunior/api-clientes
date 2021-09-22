package br.com.desafio.apiclientes.controller.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ClienteOutput {

	private String nome;
	private String endereco;
	private String documento;
	private LocalDate dataNascimento;
	private Integer idade;
	private Boolean ativo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		LocalDate localDate = LocalDate.parse(dataNascimento.toString());
		idade = (int) ChronoUnit.YEARS.between(localDate, LocalDate.now());
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	
}
