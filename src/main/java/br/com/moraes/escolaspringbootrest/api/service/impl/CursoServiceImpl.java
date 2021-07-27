package br.com.moraes.escolaspringbootrest.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.moraes.escolaspringbootrest.api.exception.IdInvalidoException;
import br.com.moraes.escolaspringbootrest.api.exception.RecursoNaoEncontradoException;
import br.com.moraes.escolaspringbootrest.api.exception.ValidacaoException;
import br.com.moraes.escolaspringbootrest.api.model.Curso;
import br.com.moraes.escolaspringbootrest.api.repository.CursoRepository;
import br.com.moraes.escolaspringbootrest.api.service.CursoService;

@Service
public class CursoServiceImpl implements CursoService {
	
	@Autowired
	private CursoRepository repository;

	private void validId(Long id) {
		if (id == null || id <= 0) {
			throw new IdInvalidoException();
		} else if (!existsById(id)) {
			throw new RecursoNaoEncontradoException();
		}
	}

	private void validar(Curso curso) {
		List<String> erros = new LinkedList<>();
		if (StringUtils.isEmpty(curso.getNome())) {
			erros.add("Nome deve ser informado.");
		} else if (curso.getNome().length() > 80) {
			erros.add("Nome deve ter no máximo 80 caracteres.");
		} else {
			final Long id = curso.getId() == null ? 0l : curso.getId();
			if (repository.existsByIdNotAndNome(id, curso.getNome())) {
				erros.add("Este curso já foi cadastrado.");
			}
		}

		if (curso.getValor() == null) {
			erros.add("Valor deve ser informado.");
		} else if (curso.getValor() <= 0) {
			erros.add("Valor deve ser maior que zero.");
		}

		if (StringUtils.isNotEmpty(curso.getDescricao()) && curso.getDescricao().length() > 300) {
			erros.add("Descriçaõ deve ter no máximo 300 caracteres.");
		}

		if (erros.size() > 0) {
			throw new ValidacaoException(erros);
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return repository.existsById(id);
	}

	@Override
	public Curso insert(Curso curso) {
		validar(curso);
		return repository.save(curso);
	}

	@Override
	public Curso update(Curso curso, Long id) {
		validId(id);
		curso.setId(id);
		validar(curso);
		return repository.save(curso);
	}

	@Override
	public Optional<Curso> findById(Long id) {
		validId(id);
		return repository.findById(id);
	}

	@Override
	public void delete(Long id) {
		validId(id);
		repository.deleteById(id);
	}

	@Override
	public Page<Curso> paginar(Integer page, Integer size) {
		return repository.findAll(PageRequest.of(page, size, Sort.by(Direction.ASC, "nome")));
	}
	
	@Override
	public List<Curso> findAll(){
		return repository.findAll(Sort.by(Direction.ASC, "nome"));
	}
}
