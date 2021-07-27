package br.com.moraes.escolaspringbootrest.api.handler;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.moraes.escolaspringbootrest.api.exception.ExceptionResponse;
import br.com.moraes.escolaspringbootrest.api.exception.IdInvalidoException;
import br.com.moraes.escolaspringbootrest.api.exception.RecursoNaoEncontradoException;
import br.com.moraes.escolaspringbootrest.api.exception.ValidacaoException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler{

	private static final Log log = LogFactory.getLog(CustomizedResponseEntityExceptionHandler.class);

	@ExceptionHandler({ RecursoNaoEncontradoException.class })
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception exception, WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus);
		return ResponseEntity.status(httpStatus).body(exceptionResponse);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public final ResponseEntity<ExceptionResponse> handleEmptyResultDataAccessException(Exception exception,
			WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.GONE;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus);
		return ResponseEntity.status(httpStatus).body(exceptionResponse);
	}

	@ExceptionHandler({ IdInvalidoException.class })
	public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception,
			WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus);
		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	@ExceptionHandler(ValidacaoException.class)
	public final ResponseEntity<ExceptionResponse> handleValidacaoException(ValidacaoException exception,
			WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus, exception.getErros());
		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	private ExceptionResponse tratar(Exception exception, WebRequest webRequest, HttpStatus httpStatus) {
		List<String> erros = new LinkedList<>();
		erros.add(exception.getMessage());
		return tratar(exception, webRequest, httpStatus, erros);
	}

	private ExceptionResponse tratar(Exception exception, WebRequest webRequest, HttpStatus httpStatus,
			List<String> erros) {
		final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), erros,
				ExceptionUtils.getRootCauseMessage(exception), webRequest.getDescription(Boolean.FALSE), httpStatus);
		log.error(String.format("Erro durante requisição - |%s| ## Detalhe do Erro: |%s|", erros.toString(),
				exceptionResponse.getMessageDevelop()));
		return exceptionResponse;
	}
}
