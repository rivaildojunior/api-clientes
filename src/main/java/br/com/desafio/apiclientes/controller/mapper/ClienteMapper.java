package br.com.desafio.apiclientes.controller.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.desafio.apiclientes.controller.dto.ClienteInput;
import br.com.desafio.apiclientes.controller.dto.ClienteOutput;
import br.com.desafio.apiclientes.model.Cliente;

@Component
public class ClienteMapper {

	private ModelMapper modelMapper;

	public ClienteMapper() {
		this.modelMapper = new ModelMapper();
	}

	public Cliente toDomain(ClienteInput input) {
		return this.modelMapper.map(input, Cliente.class);
	}

	public ClienteOutput toOutput(Cliente domain) {
		return this.modelMapper.map(domain, ClienteOutput.class);
	}

	public Page<ClienteOutput> toOutput(Page<Cliente> clientes) {
		return clientes.map(this::toOutput);
	}

}
