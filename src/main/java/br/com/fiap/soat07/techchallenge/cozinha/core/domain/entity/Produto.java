package br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity;

import java.util.Objects;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.TipoProdutoEnum;

public class Produto {

	private Long id;
	private String codigo;
	private String nome;
	private TipoProdutoEnum tipoProduto;

	public Produto() {
	}
	
	public Produto(String codigo, String nome, TipoProdutoEnum tipo) {
		this();
		this.codigo = codigo;
		this.nome = nome;
		this.tipoProduto = tipo;
	}

	
	
	public Long getId() {
		return this.id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public TipoProdutoEnum getTipoProduto() {
		return this.tipoProduto;
	}

	public String toString() {
		return "Produto(id=" + this.getId() + ", codigo=" + this.getCodigo() + ", nome=" + this.getNome() + ", tipo=" + this.getTipoProduto() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Produto produto)) return false;
        return Objects.equals(getCodigo(), produto.getCodigo()) && Objects.equals(getNome(), produto.getNome()) && getTipoProduto() == produto.getTipoProduto();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodigo(), getNome(), getTipoProduto());
	}

}