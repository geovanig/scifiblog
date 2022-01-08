package com.scifiblog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scifiblog.model.Leitor;
import com.scifiblog.model.LoginLeitor;
import com.scifiblog.repository.LeitorRepository;
import com.scifiblog.service.LeitorService;

@RestController
@RequestMapping("/leitores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeitorController {

	@Autowired
	private LeitorService leitorService;

	@Autowired
	private LeitorRepository leitorRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Leitor>> getAll() {
		return ResponseEntity.ok(leitorRepository.findAll());
	}

	@PostMapping("/logar")
	public ResponseEntity<LoginLeitor> loginLeitor(@RequestBody Optional<LoginLeitor> loginLeitor) {
		return leitorService.autenticarLeitor(loginLeitor)
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Leitor> postLeitor(@Valid @RequestBody Leitor leitor) {
		return leitorService.cadastrarLeitor(leitor)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Leitor> putLeitor(@Valid @RequestBody Leitor leitor) {
		return leitorService.atualizarLeitor(leitor)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deleteLeitor(@PathVariable long id) {
		return leitorRepository.findById(id).map(resposta -> {
			leitorRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
