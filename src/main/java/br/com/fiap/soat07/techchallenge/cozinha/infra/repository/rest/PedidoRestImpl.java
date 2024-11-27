package br.com.fiap.soat07.techchallenge.cozinha.infra.repository.rest;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge.cozinha.core.gateway.PedidoGateway;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PedidoRestImpl implements PedidoGateway {

    @Override
    public Optional<Pedido> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Pedido> find(int pageNumber, int pageSize) {
        return List.of();
    }
}
