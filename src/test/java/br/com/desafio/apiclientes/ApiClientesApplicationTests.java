package br.com.desafio.apiclientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.desafio.apiclientes.model.Cliente;
import br.com.desafio.apiclientes.repository.ClienteRepository;
import br.com.desafio.apiclientes.service.ClienteService;
import br.com.desafio.apiclientes.service.exception.ClienteNaoEncontradoException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiClientesApplication.class)
@AutoConfigureMockMvc
class ApiClientesApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	@InjectMocks
	ClienteService service;

	@Mock
	private ClienteRepository clienteRepository;

	@Test
	public void quando_um_cliente_existe1() throws Exception {
		Cliente cliente = criar();

		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		Cliente clienteMock = service.adicionar(cliente);

		assertTrue(clienteMock.getAtivo());
		assertEquals("Rivaildo", clienteMock.getNome());
		assertEquals("123456", clienteMock.getDocumento());
		assertEquals("Rua Arcoverde N. 3000", clienteMock.getEndereco());

	}

	@Test
	public void pesquisar_um_cliente_que_existe2() throws Exception {
		Cliente cliente = criar();

		Mockito.when(clienteRepository.findById(1l)).thenReturn(Optional.of(cliente));
		Cliente clienteMock = service.buscarPorId(1l);

		assertTrue(clienteMock.getAtivo());
		assertEquals("Rivaildo", clienteMock.getNome());
		assertEquals("123456", clienteMock.getDocumento());
		assertEquals("Rua Arcoverde N. 3000", clienteMock.getEndereco());

	}

	@Test
	public void pesquisar_um_cliente_que_nao_existe3() throws Exception {

		Mockito.when(clienteRepository.findById(2l)).thenReturn(Optional.empty());

		try {
			service.buscarPorId(2l);
			fail();
		} catch (ClienteNaoEncontradoException e) {
			assertTrue(e.getMessage().contains("Cliente não encontrado"));
		}

	}

	private Cliente criar() {
		Cliente cliente = new Cliente();
		cliente.setId(1l);
		cliente.setNome("Rivaildo");
		cliente.setDocumento("123456");
		cliente.setEndereco("Rua Arcoverde N. 3000");
		service.adicionar(cliente);
		return cliente;
	}
}
