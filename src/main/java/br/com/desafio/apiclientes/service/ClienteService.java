package br.com.desafio.apiclientes.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.desafio.apiclientes.model.Cliente;
import br.com.desafio.apiclientes.repository.ClienteRepository;
import br.com.desafio.apiclientes.service.exception.ClienteNaoEncontradoException;

@Service
public class ClienteService {

	private final ClienteRepository repository;

	public ClienteService(final ClienteRepository repository) {
		this.repository = repository;
	}

	@CacheEvict(cacheNames = "clientes", allEntries = true)
	public Cliente adicionar(final Cliente cliente) {
		return this.repository.save(cliente);
	}

	@CachePut(cacheNames = "clientes", key = "#id")
	public Cliente atualizar(final Long id, final Cliente cliente) {
		cliente.setId(id);
		var clienteDB = buscarPorId(id);
		BeanUtils.copyProperties(cliente, clienteDB, "codigo");
		return repository.save(clienteDB);
	}

	@CacheEvict(cacheNames = "clientes", allEntries = true)
	public void excluir(final Long id) {
		var clienteDB = buscarPorId(id);
		this.repository.delete(clienteDB);
	}

	@Cacheable(cacheNames = "clientes", key = "#id")
	public Cliente buscarPorId(final Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
	}

	@Cacheable(cacheNames = "clientes", key = "{ #root.methodName, #nome, #endereco, #documento, #pageable }")
	public Page<Cliente> buscarTodos(final String nome, final String endereco, 
			final String documento, final Pageable pageable) {

		if (nome == null) {
			return repository.findAll(pageable);
		} else {
			Map<String, String> filtro = new HashMap<>();
			filtro.put("nome", nome);
			filtro.put("endereco", endereco);
			filtro.put("documento", documento);
			return repository.findByNome(filtro, pageable);
		}
	}
	
	@CacheEvict(cacheNames = "clientes", allEntries = true)
	public void ativarCliente(final Long codigo, final Boolean ativo) {
		var clienteDB = buscarPorId(codigo);
		clienteDB.setAtivo(ativo);
		repository.save(clienteDB);
	}
}
