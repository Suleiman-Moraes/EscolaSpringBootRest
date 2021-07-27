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
import br.com.moraes.escolaspringbootrest.api.model.Aluno;
import br.com.moraes.escolaspringbootrest.api.repository.AlunoRepository;
import br.com.moraes.escolaspringbootrest.api.service.AlunoService;
import br.com.moraes.escolaspringbootrest.api.service.CursoService;

@Service
public class AlunoServiceImpl implements AlunoService {

	@Autowired
	private AlunoRepository repository;
	
	@Autowired
	private CursoService cursoService;
	
	private void validId(Long id) {
		if(id == null || id <= 0) {
			throw new IdInvalidoException();
		}
		else if(!repository.existsById(id)) {
			throw new RecursoNaoEncontradoException();
		}
	}
	
	private void validar(Aluno aluno) {
		List<String> erros = new LinkedList<>();
		if(StringUtils.isEmpty(aluno.getNome())) {
			erros.add("Nome deve ser informado.");
		}
		else if(aluno.getNome().length() > 80) {
			erros.add("Nome deve ter no máximo 80 caracteres.");
		}
		
		if(StringUtils.isEmpty(aluno.getTelefone())) {
			erros.add("Cpf deve ser informado.");
		}
		else if(aluno.getCpf().length() > 11) {
			erros.add("Cpf deve ter no máximo 11 caracteres.");
		}
		else {
			final Long id = aluno.getId() == null ? 0l : aluno.getId();
			if(repository.existsByIdNotAndCpf(id, aluno.getCpf())) {
				erros.add("Este curso já foi cadastrado.");
			}
		}
		
		if(StringUtils.isNotEmpty(aluno.getTelefone()) && aluno.getTelefone().length() > 11) {
			erros.add("Telefone deve ter no máximo 11 caracteres.");
		}
		
		if(StringUtils.isNotEmpty(aluno.getEndereco()) && aluno.getEndereco().length() > 150) {
			erros.add("Endereço deve ter no máximo 150 caracteres.");
		}
		
		if(aluno.getDataNascimento() == null) {
			erros.add("Data de nascimento deve ser informada.");
		}
		
		if(aluno.getCurso() == null || aluno.getCurso().getId() == null) {
			erros.add("Curso deve ser informado.");
		}
		else if(!cursoService.existsById(aluno.getCurso().getId())) {
			erros.add("Curso válido deve ser informado.");
		}
		
		if(erros.size() > 0) {
			throw new ValidacaoException(erros);
		}
	}
	
	@Override
	public Aluno insert(Aluno aluno) {
		validar(aluno);
		return repository.save(aluno);
	}
	
	@Override
	public Aluno update(Aluno aluno, Long id) {
		validId(id);
		aluno.setId(id);
		validar(aluno);
		return repository.save(aluno);
	}
	
	@Override
	public Optional<Aluno> findById(Long id){
		validId(id);
		return repository.findById(id);
	}
	
	@Override
	public void delete(Long id) {
		validId(id);
		repository.deleteById(id);
	}

	@Override
	public Page<Aluno> paginar(Integer page, Integer size) {
		return repository.findAll(PageRequest.of(page, size, Sort.by(Direction.ASC, "nome")));
	}

	@Override
	public List<Aluno> findAll() {
		return repository.findAll(Sort.by(Direction.ASC, "nome"));
	}
}
