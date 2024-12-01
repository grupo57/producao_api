package br.com.fiap.soat07.techchallenge.producao.core.usecase;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoJaAtendidoException;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoAtualizadoException;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoEncontradoException;
import br.com.fiap.soat07.techchallenge.producao.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.producao.core.gateway.PedidoGateway;
import br.com.fiap.soat07.techchallenge.producao.infra.repository.rest.PedidoRestImpl;
import br.com.fiap.soat07.techchallenge.producao.infra.rest.dto.PedidoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAtendimentoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAtendimentoUseCase.class);

    private final AtendimentoGateway atendimentoGateway;
    private final PedidoGateway pedidoGateway;

    public CreateAtendimentoUseCase(final AtendimentoGateway atendimentoGateway, PedidoGateway pedidoGateway) {
        this.atendimentoGateway = atendimentoGateway;
        this.pedidoGateway = pedidoGateway;
    }

    public Atendimento execute(final PedidoDTO pedidoDTO) {
        if (pedidoDTO == null)
            throw new IllegalArgumentException();

        if (pedidoDTO.getCodigo() == null || pedidoDTO.getCodigo().isEmpty())
            throw new IllegalArgumentException("Obrigatório informar o codigo do pedido");

        // verificar a unicidade do pedido
        if (atendimentoGateway.findByPedido(pedidoDTO.getId()).isPresent())
            throw new PedidoJaAtendidoException(pedidoDTO.getId());

        Atendimento atendimento = atendimentoGateway.criar(
                pedidoDTO.getId(),
                (pedidoDTO.getCliente() == null || pedidoDTO.getCliente().isEmpty() ? pedidoDTO.getCodigo()
                        : pedidoDTO.getCliente() + " " + pedidoDTO.getCodigo()),
                pedidoDTO.getProdutos());

        try {
            pedidoGateway.update(atendimento, SituacaoDoAtendimento.INICIADO);
        } catch (PedidoNaoEncontradoException | PedidoNaoAtualizadoException e1) {
            LOGGER.error("pedido {}, código: {} não foi atuaizado para a situação {}. {}",
                    atendimento.getIdPedido(),
                    atendimento.getCodigo(),
                    SituacaoDoAtendimento.INICIADO,
                    e1.getMessage()
            );
        }

        return atendimento;
    }

}