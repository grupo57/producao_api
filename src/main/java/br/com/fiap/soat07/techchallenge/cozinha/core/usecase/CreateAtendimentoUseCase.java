package br.com.fiap.soat07.techchallenge.cozinha.core.usecase;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.exception.PedidoJaAtendidoException;
import br.com.fiap.soat07.techchallenge.cozinha.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto.PedidoDTO;

public class CreateAtendimentoUseCase {

    private final AtendimentoGateway atendimentoGateway;

    public CreateAtendimentoUseCase(final AtendimentoGateway atendimentoGateway) {
        this.atendimentoGateway = atendimentoGateway;
    }

    public Atendimento execute(final PedidoDTO pedidoDTO) {
        if (pedidoDTO == null)
            throw new IllegalArgumentException();

        if (pedidoDTO.getCodigo() == null || pedidoDTO.getCodigo().isEmpty())
            throw new IllegalArgumentException("Obrigatório informar o codigo do pedido");

        // verificar a unicidade do pedido
        if (!atendimentoGateway.findByPedido(pedidoDTO.getId()).isEmpty())
            throw new PedidoJaAtendidoException(pedidoDTO.getId());

        return atendimentoGateway.criar(
                pedidoDTO.getId(),
                (pedidoDTO.getCliente() == null || pedidoDTO.getCliente().isEmpty() ? pedidoDTO.getCodigo() : pedidoDTO.getCliente()+ " " + pedidoDTO.getCodigo()),
                pedidoDTO.getProdutos());
    }

}