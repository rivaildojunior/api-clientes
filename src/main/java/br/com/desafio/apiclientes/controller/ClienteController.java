package br.com.desafio.apiclientes.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.desafio.apiclientes.controller.dto.ClienteInput;
import br.com.desafio.apiclientes.controller.dto.ClienteOutput;
import br.com.desafio.apiclientes.controller.mapper.ClienteMapper;
import br.com.desafio.apiclientes.model.Cliente;
import br.com.desafio.apiclientes.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteService clienteService;
	private final ClienteMapper mapper;

	public ClienteController(ClienteService clienteService, ClienteMapper mapper) {
		this.clienteService = clienteService;
		this.mapper = mapper;
	}

	@PostMapping
	public ResponseEntity<ClienteOutput> saveCliente(@RequestBody @Valid ClienteInput input) {
		var cliente = this.clienteService.adicionar(this.mapper.toDomain(input));
		var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();
		return ResponseEntity.created(location).body(this.mapper.toOutput(cliente));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteOutput> getClienteById(@PathVariable Long id) {
		var cliente = this.clienteService.buscarPorId(id);
		return ResponseEntity.ok().body(this.mapper.toOutput(cliente));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<ClienteOutput>> getAllClientes(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) String endereco,
			@RequestParam(required = false) String documento,
			@PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable) {
		Page<Cliente> pageCliente = this.clienteService.buscarTodos(nome, endereco, documento, pageable);

		return ResponseEntity.ok().body(this.mapper.toOutput(pageCliente));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		this.clienteService.excluir(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteOutput> updateCliente(@PathVariable Long id, @RequestBody ClienteInput input) {
		var cliente = this.clienteService.atualizar(id, this.mapper.toDomain(input));
		return ResponseEntity.ok().body(this.mapper.toOutput(cliente));
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarCliente(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		clienteService.ativarCliente(codigo, ativo);
		
	}
}
