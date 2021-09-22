package br.com.desafio.apiclientes.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

public class ClienteInput {

	@NotBlank(message = "O nome é obrigatório")
	private String nome;

	@NotBlank(message = "O endereço é obrigatório")
	private String endereco;
	
	@NotBlank(message = "O documento é obrigatório")
	private String documento;

	private LocalDate dataNascimento;

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

}
