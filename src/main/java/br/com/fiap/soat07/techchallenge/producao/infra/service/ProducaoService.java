package br.com.fiap.soat07.techchallenge.producao.infra.service;

import br.com.fiap.soat07.techchallenge.producao.core.gateway.PedidoGateway;
import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge.producao.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.producao.core.usecase.CreateAtendimentoUseCase;
import br.com.fiap.soat07.techchallenge.producao.core.usecase.SearchAtendimentoUseCase;
import br.com.fiap.soat07.techchallenge.producao.core.usecase.UpdateAtendimentoSituacaoCanceladoUseCase;
import br.com.fiap.soat07.techchallenge.producao.core.usecase.UpdateAtendimentoSituacaoConcluidoUseCase;
import br.com.fiap.soat07.techchallenge.producao.core.usecase.UpdateAtendimentoSituacaoIniciadoUseCase;

@Component
public class ProducaoService {

    private final CreateAtendimentoUseCase createAtendimentoUseCase;
    private final SearchAtendimentoUseCase searchAtendimentoUseCase;
    private final UpdateAtendimentoSituacaoIniciadoUseCase updateAtendimentoSituacaoIniciadoUseCase;
    private final UpdateAtendimentoSituacaoConcluidoUseCase updateAtendimentoSituacaoConcluidoUseCase;
    private final UpdateAtendimentoSituacaoCanceladoUseCase updateAtendimentoSituacaoCanceladoUseCase;

    public ProducaoService(final AtendimentoGateway atendimentoGateway, final PedidoGateway pedidoGateway) {
        this.createAtendimentoUseCase = new CreateAtendimentoUseCase(
                atendimentoGateway, pedidoGateway);
        this.updateAtendimentoSituacaoIniciadoUseCase = new UpdateAtendimentoSituacaoIniciadoUseCase(
                atendimentoGateway, pedidoGateway);
        this.updateAtendimentoSituacaoConcluidoUseCase = new UpdateAtendimentoSituacaoConcluidoUseCase(
                atendimentoGateway, pedidoGateway);
        this.updateAtendimentoSituacaoCanceladoUseCase = new UpdateAtendimentoSituacaoCanceladoUseCase(
                atendimentoGateway, pedidoGateway);
        this.searchAtendimentoUseCase = new SearchAtendimentoUseCase(atendimentoGateway);
    }

    public CreateAtendimentoUseCase getCreateAtendimentoUseCase() {
        return createAtendimentoUseCase;
    }

    public UpdateAtendimentoSituacaoIniciadoUseCase getUpdateAtendimentoSituacaoIniciado() {
        return updateAtendimentoSituacaoIniciadoUseCase;
    }

    public UpdateAtendimentoSituacaoConcluidoUseCase getUpdateAtendimentoSituacaoConcluido() {
        return updateAtendimentoSituacaoConcluidoUseCase;
    }

    public UpdateAtendimentoSituacaoCanceladoUseCase getUpdateAtendimentoSituacaoCancelado() {
        return updateAtendimentoSituacaoCanceladoUseCase;
    }

    public SearchAtendimentoUseCase getSearchAtendimentoUseCase() {
        return searchAtendimentoUseCase;
    }
}
