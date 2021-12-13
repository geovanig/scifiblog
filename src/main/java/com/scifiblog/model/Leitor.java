package com.scifiblog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "leitor")
public class Leitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Campo não pode estar em branco")
	@Size(min = 1, max = 128)
	private String nome;
	
	@NotBlank(message = "Campo não pode estar em branco")
	@Size(min = 6, max = 128,  message = "campo não pode ter menos que 6 caractéres e nem pode ter mais de 128")
	private String email;
	
	@NotBlank(message = "Campo não pode estar em branco")
	@Size(min = 6, max = 32, message = "campo não pode ter menos que 6 caractéres e nem pode ter mais de 32")
	private String senha;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
