package br.com.fiap.soat07.techchallenge.producao.core.usecase;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoAtualizadoException;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoEncontradoException;
import br.com.fiap.soat07.techchallenge.producao.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.producao.core.gateway.PedidoGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAtendimentoSituacaoIniciadoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAtendimentoSituacaoIniciadoUseCase.class);

    private final AtendimentoGateway atendimentoGateway;
    private final PedidoGateway pedidoGateway;

    public UpdateAtendimentoSituacaoIniciadoUseCase(final AtendimentoGateway atendimentoGateway, final PedidoGateway pedidoGateway) {
        this.atendimentoGateway = atendimentoGateway;
        this.pedidoGateway = pedidoGateway;
    }

    public Atendimento execute(Atendimento atendimento) {
        if (atendimento == null)
            throw new IllegalArgumentException();

        if (SituacaoDoAtendimento.RECEBIDO.equals(atendimento.getSituacao())) {
            atendimento.iniciado();
            atendimento = atendimentoGateway.save(atendimento);
            try {
                pedidoGateway.update(atendimento, SituacaoDoAtendimento.INICIADO);
            } catch (PedidoNaoEncontradoException | PedidoNaoAtualizadoException e1) {
                LOGGER.error("pedido {}, código: {} não foi atualizado para a situação {}. {}",
                        atendimento.getIdPedido(),
                        atendimento.getCodigo(),
                        SituacaoDoAtendimento.INICIADO,
                        e1.getMessage()
                );
            }
            return atendimento;
        }

        if (SituacaoDoAtendimento.INICIADO.equals(atendimento.getSituacao()))
            return atendimento;

        if (SituacaoDoAtendimento.ENTREGUE.equals(atendimento.getSituacao())) {
            throw new IllegalStateException("atendimento já finalizado");
        }

        if (SituacaoDoAtendimento.CANCELADO.equals(atendimento.getSituacao()))
            throw new IllegalStateException("atendimento cancelado");

        return atendimento;
    }

}