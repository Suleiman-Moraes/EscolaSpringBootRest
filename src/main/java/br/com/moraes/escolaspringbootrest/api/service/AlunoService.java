package br.com.moraes.escolaspringbootrest.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.moraes.escolaspringbootrest.api.model.Aluno;

public interface AlunoService {

	Aluno insert(Aluno aluno);

	Aluno update(Aluno aluno, Long id);

	Optional<Aluno> findById(Long id);

	void delete(Long id);

	Page<Aluno> paginar(Integer page, Integer size);

	List<Aluno> findAll();
}
