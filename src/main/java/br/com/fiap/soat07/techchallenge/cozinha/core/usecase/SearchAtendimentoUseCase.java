package br.com.fiap.soat07.techchallenge.cozinha.core.usecase;

import java.time.LocalDate;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.gateway.AtendimentoGateway;

@Component
public class SearchAtendimentoUseCase {

	private final AtendimentoGateway atendimentoGateway;


	public SearchAtendimentoUseCase(AtendimentoGateway atendimentoGateway) {
		this.atendimentoGateway = atendimentoGateway;
	}


	/**
	 * Get by id
	 * @param id {@link Long}
	 * @return {@link Optional<Atendimento>}
	 */
	public Optional<Atendimento> findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar código do atendimento");

		return atendimentoGateway.findById(id);
	}

	/**
	 * Get by Pedido
	 * @param id {@link Long}
	 * @return {@link Collection<Atendimento>}
	 */
	public Optional<Atendimento> findByPedido(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar o código do pedido");

		return atendimentoGateway.findByPedido(id);
	}

	/**
	 * Listagem de Atendimentos filtrado por Data e Situação
	 * @param data {@link LocalDate}
	 * @param situacoes {@link SituacaoDoAtendimento}
	 * @return {@link Collection<Atendimento>}
	 */
	public Collection<Atendimento> findByData(LocalDate data, EnumSet<SituacaoDoAtendimento> situacoes) {
		if (data == null)
			throw new IllegalArgumentException("Obrigatório informar a data");

		return atendimentoGateway.findByData(data, situacoes);
	}


	/**
	 * Listagem de Atendimentos em aberto
	 * @return {@link Collection<Atendimento>}
	 */
	public Collection<Atendimento> find() {
		return atendimentoGateway.find();
	}	
}