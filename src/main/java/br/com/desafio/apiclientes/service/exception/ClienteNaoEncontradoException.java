package br.com.desafio.apiclientes.service.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 4117791309825048345L;

	public ClienteNaoEncontradoException(String message) {
		super(message);
	}

}
