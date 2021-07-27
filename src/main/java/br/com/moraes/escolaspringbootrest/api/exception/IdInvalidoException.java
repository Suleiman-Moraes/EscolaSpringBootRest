package br.com.moraes.escolaspringbootrest.api.exception;

public class IdInvalidoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IdInvalidoException(){
		super("ID inválido por ser nulo, negativo ou igual a zero.");
	}
}
