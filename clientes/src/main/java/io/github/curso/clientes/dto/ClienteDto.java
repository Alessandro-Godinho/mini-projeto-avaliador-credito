package io.github.curso.clientes.dto;

import io.github.curso.clientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteDto {
	
	    private String cpf;
	    private String nome;
	    private Integer idade;

	    public Cliente toModel(){
	        return new Cliente(cpf, nome, idade);
	    }
	}

