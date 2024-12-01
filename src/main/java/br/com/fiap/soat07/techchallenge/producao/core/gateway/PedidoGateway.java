package br.com.fiap.soat07.techchallenge.producao.core.gateway;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.SituacaoDoAtendimento;

import java.util.Collection;
import java.util.Optional;

public interface PedidoGateway {

     /**
     * Atualiza a situação do pedido de acordo com a situação do atendimento
     * @param atendimento
     * @param situacaoDoAtendimento
     */
     void update(Atendimento atendimento, SituacaoDoAtendimento situacaoDoAtendimento);

}