package br.com.fiap.soat07.techchallenge.cozinha.infra.service;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.techchallenge.cozinha.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.CreateAtendimentoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.SearchAtendimentoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.UpdateAtendimentoSituacaoCanceladoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.UpdateAtendimentoSituacaoConcluidoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.UpdateAtendimentoSituacaoIniciadoUseCase;

@Component
public class CozinhaService {

    private final CreateAtendimentoUseCase createAtendimentoUseCase;
    private final SearchAtendimentoUseCase searchAtendimentoUseCase;
	private final UpdateAtendimentoSituacaoIniciadoUseCase updateAtendimentoSituacaoIniciadoUseCase;
	private final UpdateAtendimentoSituacaoConcluidoUseCase updateAtendimentoSituacaoConcluidoUseCase;
	private final UpdateAtendimentoSituacaoCanceladoUseCase updateAtendimentoSituacaoCanceladoUseCase;

    public CozinhaService(final AtendimentoGateway atendimentoGateway) {
        this.createAtendimentoUseCase = new CreateAtendimentoUseCase(atendimentoGateway);
        this.updateAtendimentoSituacaoIniciadoUseCase = new UpdateAtendimentoSituacaoIniciadoUseCase(atendimentoGateway);
        this.updateAtendimentoSituacaoConcluidoUseCase = new UpdateAtendimentoSituacaoConcluidoUseCase(atendimentoGateway);
        this.updateAtendimentoSituacaoCanceladoUseCase = new UpdateAtendimentoSituacaoCanceladoUseCase(atendimentoGateway);
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
