package io.github.curso.clientes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.curso.clientes.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
}
