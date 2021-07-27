package br.com.moraes.escolaspringbootrest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.moraes.escolaspringbootrest.api.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	boolean existsByIdNotAndNome(Long id, String nome);
}
