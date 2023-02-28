package io.github.curso.cartoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.curso.cartoes.domain.ClienteCartao;


public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
    List<ClienteCartao> findByCpf(String cpf);
}