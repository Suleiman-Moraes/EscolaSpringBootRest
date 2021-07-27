package br.com.moraes.escolaspringbootrest.api.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date timestamp;
	
	private List<String> messages;

	private String messageDevelop;

	private String details;

	@JsonIgnore
	private HttpStatus httpStatus;

	public ExceptionResponse(Date timestamp, List<String> messages, String messageDevelop, String details,
			HttpStatus httpStatus) {
		this.timestamp = timestamp;
		this.messageDevelop = messageDevelop;
		this.details = details;
		this.httpStatus = httpStatus;
		this.messages = messages;
	}

	public String getHttpErro() {
		if (httpStatus != null) {
			return String.format("%s - %s", httpStatus.value(), httpStatus.getReasonPhrase());
		}
		return String.format("%s - %s", HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessageDevelop() {
		return messageDevelop;
	}

	public void setMessageDevelop(String messageDevelop) {
		this.messageDevelop = messageDevelop;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
