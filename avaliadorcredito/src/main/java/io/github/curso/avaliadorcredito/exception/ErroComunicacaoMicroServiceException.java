package io.github.curso.avaliadorcredito.exception;

import lombok.Getter;

public class ErroComunicacaoMicroServiceException extends RuntimeException {

	private static final long serialVersionUID = -477081854987432724L;
	
	@Getter
	private Integer status;
	
	public ErroComunicacaoMicroServiceException(String msg, Integer status) {
		super(msg);
		this.status =status;
	}

}
