package io.github.curso.avaliadorcredito.infra;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.curso.avaliadorcredito.dto.Cartao;
import io.github.curso.avaliadorcredito.dto.CartaoCliente;


@FeignClient(value = "cartoes", path = "/cartoes")
public interface CartaoResourceClient {
    
	@GetMapping(params = "cpf")
	List<CartaoCliente> getCartoesByCliente(@RequestParam("cpf") String cpf);
	
	@GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda);

}
