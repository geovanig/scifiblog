package com.scifiblog.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scifiblog.model.Postagem;

//A anotação Repository indica o que a Interface é e vai fazer.
//Ao estender JpaRepository, temos que colocar,
//entre as chaves diamante, a classe que o repository irá atender
//e o tipo do id que se encontra na mesma entidade 
@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{

	//Esse é um método personalizado que se comporta como uma instrução SQL usando like.
	//Por exemplo "SELECT * FROM postagem WHERE titulo LIKE \"%titulo%\;"
	public List<Postagem> findAllByTituloContainingIgnoreCase (String titulo);
	
}
