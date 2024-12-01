package br.com.fiap.soat07.techchallenge.producao.core.usecase;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoAtualizadoException;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoEncontradoException;
import br.com.fiap.soat07.techchallenge.producao.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.producao.core.gateway.PedidoGateway;
import br.com.fiap.soat07.techchallenge.producao.infra.repository.rest.PedidoRestImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAtendimentoSituacaoConcluidoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAtendimentoSituacaoConcluidoUseCase.class);

    private final AtendimentoGateway atendimentoGateway;
    private final PedidoGateway pedidoGateway;

    public UpdateAtendimentoSituacaoConcluidoUseCase(final AtendimentoGateway atendimentoGateway, final PedidoGateway pedidoGateway) {
        this.atendimentoGateway = atendimentoGateway;
        this.pedidoGateway = pedidoGateway;
    }

    public Atendimento execute(Atendimento atendimento) {
        if (atendimento == null)
            throw new IllegalArgumentException();

        if (SituacaoDoAtendimento.RECEBIDO.equals(atendimento.getSituacao())) {
            atendimento.entregue();
            atendimento = atendimentoGateway.save(atendimento);
            try {
                pedidoGateway.update(atendimento, SituacaoDoAtendimento.ENTREGUE);
            } catch (PedidoNaoEncontradoException | PedidoNaoAtualizadoException e1) {
                LOGGER.error("pedido {}, código: {} não foi atuaizado para a situação {}. {}",
                        atendimento.getIdPedido(),
                        atendimento.getCodigo(),
                        SituacaoDoAtendimento.ENTREGUE,
                        e1.getMessage()
                );
            }

            return atendimento;
        }

        if (SituacaoDoAtendimento.INICIADO.equals(atendimento.getSituacao())) {
            atendimento.entregue();
            atendimento = atendimentoGateway.save(atendimento);
            try {
                pedidoGateway.update(atendimento, SituacaoDoAtendimento.ENTREGUE);
            } catch (PedidoNaoEncontradoException | PedidoNaoAtualizadoException e1) {
                LOGGER.error("pedido {}, código: {} não foi atuaizado para a situação {}. {}",
                        atendimento.getIdPedido(),
                        atendimento.getCodigo(),
                        SituacaoDoAtendimento.ENTREGUE,
                        e1.getMessage()
                );
            }

            return atendimento;
        }

        if (SituacaoDoAtendimento.ENTREGUE.equals(atendimento.getSituacao()))
            return atendimento;

        if (SituacaoDoAtendimento.CANCELADO.equals(atendimento.getSituacao()))
            throw new IllegalStateException("atendimento cancelado");

        return atendimento;
    }

}