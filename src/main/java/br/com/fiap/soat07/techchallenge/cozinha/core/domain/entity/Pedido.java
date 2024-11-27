package br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.PedidoStatusEnum;

public class Pedido {
	
	private Long id;
	private String nomeCliente;
	private String codigo;
	private Set<Produto> produtos;
	private PedidoStatusEnum status;

	public Pedido() {
		this.produtos = new HashSet<>();
		this.status = PedidoStatusEnum.PREPARO;
	}
	public Pedido(Long id, String codigo, String nomeCliente, PedidoStatusEnum status) {
		this.id = id;
		this.codigo = codigo;
		this.nomeCliente = nomeCliente;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nomeCliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public PedidoStatusEnum getStatus() {
		return status;
	}

	public Set<Produto> getProdutos() {
		if (produtos == null)
			this.produtos = new HashSet<>();
		return produtos;
	}

	@Override
	public String toString() {
		return nomeCliente == null || nomeCliente.isEmpty() ? codigo : nomeCliente;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo, nomeCliente);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(nomeCliente, other.nomeCliente);
	}

}