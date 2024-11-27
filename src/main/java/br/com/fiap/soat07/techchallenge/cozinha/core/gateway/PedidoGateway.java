package br.com.fiap.soat07.techchallenge.cozinha.core.gateway;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Pedido;

import java.util.Collection;
import java.util.Optional;

public interface PedidoGateway {

    /**
     * Get by id
     * @param id {@link Long}
     * @return {@link Pedido}
     */
    Optional<Pedido> findById(long id);

    /**
     * Get pageable
     * @param pageNumber
     * @param pageSize
     * @return {@link Collection < Pedido >}
     */
    Collection<Pedido> find(int pageNumber, int pageSize);

}