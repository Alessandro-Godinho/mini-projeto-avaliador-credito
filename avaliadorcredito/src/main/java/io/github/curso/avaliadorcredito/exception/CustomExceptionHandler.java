package io.github.curso.avaliadorcredito.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {
	

	
	@ExceptionHandler(ErroComunicacaoMicroServiceException.class)
	public ResponseEntity<ErrorMessage> validacaoDeRegraDeNegocio(ErroComunicacaoMicroServiceException ex, WebRequest request) {
		
		ErrorMessage message = new ErrorMessage(
		        ex.getStatus(),
		        ex.getMessage(),
		        request.getDescription(false));
		    
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<ErrorMessage> registroNaoEncontradoException(RegistroNaoEncontradoException ex, WebRequest request) {
		
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        ex.getMessage(),
		        request.getDescription(false));
		    
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ErroSolicitacaoCartaoException.class)
	public ResponseEntity<ErrorMessage> erroSolicitacaoCartaoException(ErroSolicitacaoCartaoException ex, WebRequest request) {
		
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        ex.getMessage(),
		        request.getDescription(false));
		    
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	
}
