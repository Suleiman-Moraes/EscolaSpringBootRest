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
import br.com.moraes.escolaspringbootrest.api.model.Curso;
import br.com.moraes.escolaspringbootrest.api.service.CursoService;

@RestController
@RequestMapping("/api/curso")
public class CursoController {

	@Autowired
	private CursoService service;

	@GetMapping(value = "page")
	public ResponseEntity<Page<Curso>> paginar(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws Exception {
		final Page<Curso> pag = service.paginar(page, size);
		if (CollectionUtils.isEmpty(pag.getContent())) {
			throw new RecursoNaoEncontradoException();
		}
		return ResponseEntity.ok(pag);
	}

	@GetMapping
	public ResponseEntity<List<Curso>> findAll() throws Exception {
		final List<Curso> lista = service.findAll();
		if (CollectionUtils.isEmpty(lista)) {
			throw new RecursoNaoEncontradoException();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Curso> findById(@PathVariable long id) throws Exception {
		final Curso curso = service.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException());
		return ResponseEntity.ok(curso);
	}

	@PostMapping
	public ResponseEntity<Curso> insert(@RequestBody Curso curso) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(curso));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Curso> update(@RequestBody Curso curso, @PathVariable long id) throws Exception {
		return ResponseEntity.ok(service.update(curso, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
