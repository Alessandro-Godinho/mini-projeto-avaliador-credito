package io.github.curso.avaliadorcredito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.curso.avaliadorcredito.dto.DadosAvaliacao;
import io.github.curso.avaliadorcredito.dto.DadosSolicitacaoEmissaoCartao;
import io.github.curso.avaliadorcredito.dto.ProtocoloSolicitacaoCartao;
import io.github.curso.avaliadorcredito.dto.RetornoAvaliacaoCliente;
import io.github.curso.avaliadorcredito.dto.SituacaoCliente;
import io.github.curso.avaliadorcredito.service.AvaliadorCreditoService;

@RestController
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {
	
	@Autowired
	private AvaliadorCreditoService avalidadorCreditoService;
	@GetMapping
	public String status () {
		return "ok";
	}
	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
		SituacaoCliente situacaoCliente = avalidadorCreditoService.obterSituacaoCliente(cpf);
		return ResponseEntity.ok(situacaoCliente);
	}
	
	 @PostMapping
	    public ResponseEntity realizarAvaliacao( @RequestBody DadosAvaliacao dados ){
	            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avalidadorCreditoService
	                    .realizarAvaliacao(dados.getCpf(), dados.getRenda());
	            return ResponseEntity.ok(retornoAvaliacaoCliente);
	    }

	    @PostMapping("solicitacoes-cartao")
	    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
	            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avalidadorCreditoService
	                    .solicitarEmissaoCartao(dados);
	            return ResponseEntity.ok(protocoloSolicitacaoCartao);
	    }

}
