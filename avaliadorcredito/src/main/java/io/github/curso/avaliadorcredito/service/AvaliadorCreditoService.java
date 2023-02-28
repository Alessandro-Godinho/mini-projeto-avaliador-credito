package io.github.curso.avaliadorcredito.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import io.github.curso.avaliadorcredito.dto.Cartao;
import io.github.curso.avaliadorcredito.dto.CartaoAprovado;
import io.github.curso.avaliadorcredito.dto.DadosCliente;
import io.github.curso.avaliadorcredito.dto.DadosSolicitacaoEmissaoCartao;
import io.github.curso.avaliadorcredito.dto.ProtocoloSolicitacaoCartao;
import io.github.curso.avaliadorcredito.dto.RetornoAvaliacaoCliente;
import io.github.curso.avaliadorcredito.dto.SituacaoCliente;
import io.github.curso.avaliadorcredito.exception.ErroComunicacaoMicroServiceException;
import io.github.curso.avaliadorcredito.exception.ErroSolicitacaoCartaoException;
import io.github.curso.avaliadorcredito.exception.RegistroNaoEncontradoException;
import io.github.curso.avaliadorcredito.infra.CartaoResourceClient;
import io.github.curso.avaliadorcredito.infra.ClienteResourceClient;
import io.github.curso.avaliadorcredito.queue.SolicitacaoEmissaoCartaoPublisher;

@Service
public class AvaliadorCreditoService {

	@Autowired
	private ClienteResourceClient clienteResourceClient;
	@Autowired
	private CartaoResourceClient cartaoResourceClient;
	@Autowired
    private SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

	
	public SituacaoCliente obterSituacaoCliente(String cpf) {
		
		try {
			return SituacaoCliente.builder()
					.cliente(clienteResourceClient.dadosCliente(cpf))
					.cartoes(cartaoResourceClient.getCartoesByCliente(cpf))
					//.cartoes(new ArrayList<>())
					.build();
			
		} catch (FeignException.FeignClientException e) {
			if(HttpStatus.NOT_FOUND.value() == e.status()) {
				throw new RegistroNaoEncontradoException("Registro não encontrado para esse CPF");
			}
			throw new ErroComunicacaoMicroServiceException(e.getMessage(), e.status());
		}
	}
	
	 public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda){
	        try{
	            DadosCliente dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
	            ResponseEntity<List<Cartao>> cartoesResponse = cartaoResourceClient.getCartoesRendaAteh(renda);

	            List<Cartao> cartoes = cartoesResponse.getBody();
	            var listaCartoesAprovados = cartoes.stream().map(cartao -> {


	                BigDecimal limiteBasico = cartao.getLimiteBasico();
	                BigDecimal idadeBD = BigDecimal.valueOf(dadosClienteResponse.getIdade());
	                var fator = idadeBD.divide(BigDecimal.valueOf(10));
	                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

	                CartaoAprovado aprovado = new CartaoAprovado();
	                aprovado.setCartao(cartao.getNome());
	                aprovado.setBandeira(cartao.getBandeira());
	                aprovado.setLimiteAprovado(limiteAprovado);

	                return aprovado;
	            }).collect(Collectors.toList());

	            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

	        }catch (FeignException.FeignClientException e){
	            int status = e.status();
	            if(HttpStatus.NOT_FOUND.value() == status){
	                throw new RegistroNaoEncontradoException("");
	            }
	            throw new ErroComunicacaoMicroServiceException(e.getMessage(), status);
	        }
	    }

	    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
	        try{
	            emissaoCartaoPublisher.solicitarCartao(dados);
	            var protocolo = UUID.randomUUID().toString();
	            return new ProtocoloSolicitacaoCartao(protocolo);
	        }catch (Exception e){
	            throw new ErroSolicitacaoCartaoException(e.getMessage());
	        }
	    }

}
