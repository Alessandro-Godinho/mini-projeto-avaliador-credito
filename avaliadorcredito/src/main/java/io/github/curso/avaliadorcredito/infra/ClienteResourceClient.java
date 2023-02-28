package io.github.curso.avaliadorcredito.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.curso.avaliadorcredito.dto.DadosCliente;

@FeignClient(value = "clientes", path = "/clientes")
public interface ClienteResourceClient {
    
	@GetMapping(params = "cpf")
    DadosCliente dadosCliente(@RequestParam("cpf") String cpf);

}
