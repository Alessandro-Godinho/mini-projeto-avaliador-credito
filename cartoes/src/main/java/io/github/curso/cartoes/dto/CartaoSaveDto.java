package io.github.curso.cartoes.dto;

import java.math.BigDecimal;

import io.github.curso.cartoes.domain.BandeiraCartao;
import io.github.curso.cartoes.domain.Cartao;
import lombok.Data;

@Data
public class CartaoSaveDto {
	
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limite );
    }

}
