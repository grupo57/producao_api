package br.com.fiap.soat07.techchallenge.producao.infra.repository.rest;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoAtualizadoException;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoNaoEncontradoException;
import br.com.fiap.soat07.techchallenge.producao.core.gateway.PedidoGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PedidoRestImpl implements PedidoGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoRestImpl.class);

    @Autowired
    private ServiceConfigProperties serviceConfigProperties;

    @Override
    public void update(Atendimento atendimento, SituacaoDoAtendimento situacaoDoAtendimento) {

        String url = serviceConfigProperties.getPedidoURL(atendimento);
        if (url == null)
            throw new IllegalStateException("URL do serviço de pedido não foi configurada");

        LOGGER.info("alterando situação do pedido {}, código: {} para {}.",
                    atendimento.getIdPedido(),
                    atendimento.getCodigo(),
                    situacaoDoAtendimento
        );

        RestClient restClient = RestClient.create();
        String result = restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.value() == 400, (request, response) -> {
                    throw new PedidoNaoEncontradoException(atendimento.getIdPedido());
                })
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new PedidoNaoEncontradoException(atendimento.getIdPedido());
                })
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new PedidoNaoAtualizadoException(atendimento.getIdPedido());
                })
                .body(String.class);
        System.err.println(result);
    }

}
