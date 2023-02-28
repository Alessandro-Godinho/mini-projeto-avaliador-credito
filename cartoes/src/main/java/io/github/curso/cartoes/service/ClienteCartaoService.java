package io.github.curso.cartoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.curso.cartoes.domain.ClienteCartao;
import io.github.curso.cartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}