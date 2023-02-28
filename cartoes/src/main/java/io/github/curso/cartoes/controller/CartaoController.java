package io.github.curso.cartoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.curso.cartoes.domain.Cartao;
import io.github.curso.cartoes.domain.ClienteCartao;
import io.github.curso.cartoes.dto.CartaoSaveDto;
import io.github.curso.cartoes.dto.CartoesPorClienteDto;
import io.github.curso.cartoes.service.CartaoService;
import io.github.curso.cartoes.service.ClienteCartaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartaoController {
	

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;



    @PostMapping
    public ResponseEntity<?> cadastra( @RequestBody CartaoSaveDto request ){
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda){
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteDto>> getCartoesByCliente(
            @RequestParam("cpf") String cpf){
        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteDto> resultList = lista.stream()
                .map(CartoesPorClienteDto::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
    
    @GetMapping
    public ResponseEntity<List<Cartao>> cartoes(){
        return ResponseEntity.ok( cartaoService.getCartoes());
    }

}
