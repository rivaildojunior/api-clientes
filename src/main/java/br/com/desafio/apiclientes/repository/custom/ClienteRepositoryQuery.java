package br.com.desafio.apiclientes.repository.custom;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.desafio.apiclientes.model.Cliente;

public interface ClienteRepositoryQuery {

	Page<Cliente> findByNome(Map<String, String> filtro, Pageable pageable);

}
