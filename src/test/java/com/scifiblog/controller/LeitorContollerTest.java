package com.scifiblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.scifiblog.model.Leitor;
import com.scifiblog.service.LeitorService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LeitorContollerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private LeitorService leitorService;

	@Test
	@Order(1)
	@DisplayName("Cadastrar Leitor")
	public void deveCriarLeitor() {

		HttpEntity<Leitor> requisicao = new HttpEntity<Leitor>(
				new Leitor(0L, "Paulo Antunes", "paulo_antunes@email.com.br", "13465278", ""));

		ResponseEntity<Leitor> resposta = testRestTemplate.exchange("/leitores/cadastrar", HttpMethod.POST, requisicao,
				Leitor.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getEmail(), resposta.getBody().getEmail());
	}

	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		leitorService.cadastrarLeitor(new Leitor(0L, "Maria da Silva", "maria_silva@email.com.br", "13465278", ""));

		HttpEntity<Leitor> requisicao = new HttpEntity<Leitor>(
				new Leitor(0L, "Maria da Silva", "maria_silva@email.com.br", "13465278", ""));

		ResponseEntity<Leitor> resposta = testRestTemplate.exchange("/leitores/cadastrar", HttpMethod.POST, requisicao,
				Leitor.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("Alterar leitor")
	public void deveAtualizarUmleitor() {

		Optional<Leitor> leitorCreate = leitorService
				.cadastrarLeitor(new Leitor(0L, "Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", ""));

		Leitor leitorUpdate = new Leitor(leitorCreate.get().getId(), "Juliana Andrews Ramos",
				"juliana_ramos@email.com.br", "juliana123", "");

		HttpEntity<Leitor> requisicao = new HttpEntity<Leitor>(leitorUpdate);

		ResponseEntity<Leitor> resposta = testRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Leitor.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(leitorUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(leitorUpdate.getEmail(), resposta.getBody().getEmail());
	}

	@Test
	@Order(4)
	@DisplayName("Listar leitores")
	public void deveMostrarTodosLeitores() {


		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/leitores/all",
				HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

}
