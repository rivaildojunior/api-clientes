package br.com.desafio.apiclientes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.desafio.apiclientes.model.Cliente;
import br.com.desafio.apiclientes.repository.custom.ClienteRepositoryQuery;

public interface ClienteRepository extends CrudRepository<Cliente, Long>, ClienteRepositoryQuery {

	@Query("select c FROM Cliente c ")
	Page<Cliente> findAll(Pageable pageable);

}
