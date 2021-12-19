package com.scifiblog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import com.scifiblog.model.Leitor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeitorRepositoryTest {

	@Autowired
	private LeitorRepository leitorRepository;

	@BeforeAll
	void start() {
		leitorRepository.save(new Leitor(0L, "Lion Ferreira", "lion@email.com.br", "senha123456", ""));
		leitorRepository.save(new Leitor(0L, "Cat Ferreira", "cat@email.com.br", "senha12345", ""));
		leitorRepository.save(new Leitor(0L, "Ma Ferreira", "ma@email.com.br", "senha1234", ""));
		leitorRepository.save(new Leitor(0L, "Guga", "guga@email.com.br", "senha123", ""));
	}

	@Test
	@DisplayName("Retorna 1 email")
	public void deveRetornarUmEmail() {
		Optional<Leitor> leitor = leitorRepository.findByEmail("ma@email.com.br");
		assertTrue(leitor.get().getEmail().equals("ma@email.com.br"));
	}

	@Test
	@DisplayName("Retorna 3 nomes")
	public void deveRetornarTresNomes() {
		List<Leitor> listaDeLeitores = leitorRepository.findAllByNomeContainingIgnoreCase("feRreIrA");
		assertEquals(3, listaDeLeitores.size());
		assertTrue(listaDeLeitores.get(0).getNome().equals("Lion Ferreira"));
		assertTrue(listaDeLeitores.get(1).getNome().equals("Cat Ferreira"));
		assertTrue(listaDeLeitores.get(2).getNome().equals("Ma Ferreira"));
	}

}
