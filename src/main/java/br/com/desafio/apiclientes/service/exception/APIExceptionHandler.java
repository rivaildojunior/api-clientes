package br.com.desafio.apiclientes.service.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.desafio.apiclientes.model.Mensagem;

@RestControllerAdvice
public class APIExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<Mensagem> handleClienteNaoEncontradoException(ClienteNaoEncontradoException e,
			HttpServletRequest request) {

		Mensagem erro = new Mensagem("Cliente não encontrado");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<Mensagem> handle(MethodArgumentNotValidException exception) {
		List<Mensagem> dto = new ArrayList<>();

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			Mensagem erro = new Mensagem(mensagem);
			dto.add(erro);
		});

		return dto;
	}

}
