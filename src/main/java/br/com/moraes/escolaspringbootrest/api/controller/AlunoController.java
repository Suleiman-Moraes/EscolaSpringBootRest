package br.com.moraes.escolaspringbootrest.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.moraes.escolaspringbootrest.api.exception.RecursoNaoEncontradoException;
import br.com.moraes.escolaspringbootrest.api.model.Aluno;
import br.com.moraes.escolaspringbootrest.api.service.AlunoService;

@RestController
@RequestMapping("/api/aluno")
public class AlunoController {

	@Autowired
	private AlunoService service;

	@GetMapping("page")
	public ResponseEntity<Page<Aluno>> paginar(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws Exception {
		final Page<Aluno> pag = service.paginar(page, size);
		if (CollectionUtils.isEmpty(pag.getContent())) {
			throw new RecursoNaoEncontradoException();
		}
		return ResponseEntity.ok(pag);
	}

	@GetMapping
	public ResponseEntity<List<Aluno>> findAll() throws Exception {
		final List<Aluno> lista = service.findAll();
		if (CollectionUtils.isEmpty(lista)) {
			throw new RecursoNaoEncontradoException();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Aluno> findById(@PathVariable long id) throws Exception {
		final Aluno aluno = service.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException());
		return ResponseEntity.ok(aluno);
	}

	@PostMapping
	public ResponseEntity<Aluno> insert(@RequestBody Aluno aluno) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(aluno));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Aluno> update(@RequestBody Aluno aluno, @PathVariable long id) throws Exception {
		return ResponseEntity.ok(service.update(aluno, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
