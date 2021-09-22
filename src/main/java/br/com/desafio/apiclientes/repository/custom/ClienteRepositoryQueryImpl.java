package br.com.desafio.apiclientes.repository.custom;

import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.desafio.apiclientes.model.Cliente;

public class ClienteRepositoryQueryImpl implements ClienteRepositoryQuery {

	private EntityManager entityManager;

	public ClienteRepositoryQueryImpl(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Cliente> findByNome(final Map<String, String> filtro, final Pageable pageable) {
		var queryStr = new StringBuilder();
		queryStr.append("select c from Cliente c where 1 = 1");
		filtro.forEach((chave, valor) -> {
			if (valor != null) {
				queryStr.append(" and " + chave + " = :" + chave);
			}
		});
		var query = entityManager.createQuery(queryStr.toString());
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		filtro.forEach((chave, valor) -> {
			if (valor != null) {
				query.setParameter(chave, valor);
			}
		});
		var clientes = query.getResultList();

		return new PageImpl<>(clientes, pageable, clientes.size());
	}

}
