package br.com.moraes.escolaspringbootrest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.moraes.escolaspringbootrest.api.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	boolean existsByIdNotAndCpf(Long id, String cpf);
}
