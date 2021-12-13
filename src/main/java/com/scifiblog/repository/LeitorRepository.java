package com.scifiblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scifiblog.model.Leitor;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long>{

	public Optional<Leitor> findByEmail(String email);
	
}