package io.github.curso.cartoes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.curso.cartoes.domain.Cartao;
import io.github.curso.cartoes.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoService {

	private final CartaoRepository repository;

	@Transactional
	public Cartao save(Cartao cartao) {
		return repository.save(cartao);
	}

	public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return repository.findByRendaLessThanEqual(rendaBigDecimal);
	}

	public List<Cartao> getCartoes() {
		return repository.findAll();
	}
}