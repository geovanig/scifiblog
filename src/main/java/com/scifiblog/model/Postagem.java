package com.scifiblog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Aqui vemos as anotações Entity e Table,
//elas indicam que a classe Postagem é uma entidade
// e coloca um nome pra ela, respectivamente...

@Entity
@Table(name = "postagem")
public class Postagem {

	//Podemos ver a anotação Id que torna o atributo uma PK
	//e vemos a GeneratedValue que torna ele auto_icrement,
	//dentro do GenerateValue usamos a estrategia
	//de como irá ser gerado o auto_increment 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//Aqui, a anotação NotBlank, diz que o input do usuario não pode ser vazio,
	//caso fosse um número ao invés de string, então deveriamos usar a NotNull.
	//E a anotação Sise define uma regra para o tamanho minimo e maximo de caracteres 
	@NotBlank(message = "O título não pode estar em branco!")
	@Size(min = 1, max = 80, message = "O título só pode conter até 80 caracteres!")
	private String titulo;
	
	@NotBlank(message = "O texto não pode estar em branco!")
	@Size(min = 1, max = 8000, message = "O texto pode conter até 8000 caracteres!")
	private String texto;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new java.sql.Date(System.currentTimeMillis());
	
	//Aqui fica o atributo, de outra entidade/classe,
	//que será usado para fazer a relação,
	//o atributo terá o tipo da classe que será feita a relação
	//e tem a anotação com a cardianalidade da classe que ele se sencontra
	//(no caso, na classe Postagem)
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	Tema tema;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	
	
}
