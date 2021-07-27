package br.com.moraes.escolaspringbootrest.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.moraes.escolaspringbootrest.api.model.Curso;

public interface CursoService {

	Boolean existsById(Long id);

	Curso insert(Curso curso);

	Curso update(Curso curso, Long id);

	Optional<Curso> findById(Long id);

	void delete(Long id);

	Page<Curso> paginar(Integer page, Integer size);

	List<Curso> findAll();
}
