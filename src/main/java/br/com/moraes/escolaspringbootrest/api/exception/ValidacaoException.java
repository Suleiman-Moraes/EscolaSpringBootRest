package br.com.moraes.escolaspringbootrest.api.exception;

import java.util.LinkedList;
import java.util.List;

public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private List<String> erros;
	
	public ValidacaoException(List<String> erros) {
		this.erros = erros;
	}
	
	public ValidacaoException(String erro) {
		this.erros = new LinkedList<>();
		this.erros.add(erro);
	}
	
	public List<String> getErros() {
		return erros;
	}
}
