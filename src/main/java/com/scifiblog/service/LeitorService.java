package com.scifiblog.service;

import java.nio.charset.Charset;

import java.util.Optional;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.scifiblog.model.Leitor;
import com.scifiblog.model.LoginLeitor;
import com.scifiblog.repository.LeitorRepository;

@Service
public class LeitorService {

	@Autowired
	private LeitorRepository leitorRepository;

	public Optional<Leitor> cadastrarLeitor(Leitor leitor) {
		if (leitorRepository.findByEmail(leitor.getEmail()).isPresent()) {
			return Optional.empty();
		}
		leitor.setSenha(criptografarSenha(leitor.getSenha()));
		return Optional.of(leitorRepository.save(leitor));
	}

	public Optional<Leitor> atualizarLeitor(Leitor leitor) {
		if (leitorRepository.findById(leitor.getId()).isPresent()) {
			Optional<Leitor> buscaLeitor = leitorRepository.findByEmail(leitor.getEmail());
			if (buscaLeitor.isPresent()) {
				if (buscaLeitor.get().getId() != leitor.getId())
					return Optional.empty();
			}
			leitor.setSenha(criptografarSenha(leitor.getSenha()));
			return Optional.of(leitorRepository.save(leitor));
		}
		return Optional.empty();
	}

	public Optional<LoginLeitor> autenticarLeitor(Optional<LoginLeitor> loginLeitor) {
		Optional<Leitor> leitor = leitorRepository.findByEmail(loginLeitor.get().getEmail());
		if (leitor.isPresent()) {
			if (compararSenhas(loginLeitor.get().getSenha(), leitor.get().getSenha())) {
				loginLeitor.get().setId(leitor.get().getId());
				loginLeitor.get().setNome(leitor.get().getNome());
				loginLeitor.get().setFoto(leitor.get().getFoto());
				loginLeitor.get().setSenha(leitor.get().getSenha());
				loginLeitor.get().setTipo(leitor.get().getTipo());
				loginLeitor.get().setToken(gerarBasicToken(loginLeitor.get().getEmail(), loginLeitor.get().getSenha()));
				return loginLeitor;
			}
		}
		return Optional.empty();
	}

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senhaDigitada, senhaBanco);
	}

	private String gerarBasicToken(String email, String senha) {
		String token = email + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);
	}

}
