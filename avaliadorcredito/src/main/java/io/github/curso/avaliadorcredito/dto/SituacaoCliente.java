package io.github.curso.avaliadorcredito.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SituacaoCliente {
	
	private DadosCliente cliente;
	private List<CartaoCliente> cartoes;

}
