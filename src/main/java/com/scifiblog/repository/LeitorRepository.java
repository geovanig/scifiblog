package com.scifiblog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scifiblog.model.Leitor;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long>{

	public Optional<Leitor> findByEmail(String email);
	public List <Leitor> findAllByNomeContainingIgnoreCase(String nome);
	public Optional<Leitor> findById(long id);
	
}
