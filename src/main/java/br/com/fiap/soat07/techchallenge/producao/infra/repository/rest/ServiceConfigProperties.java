package br.com.fiap.soat07.techchallenge.producao.infra.repository.rest;

// POJO for Services Configuration Property type

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.PedidoStatusEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.microsservico")
public class ServiceConfigProperties {

    private String pedidoURL;


    public String getPedidoURL(Atendimento atendimento) {
        if (pedidoURL == null)
            throw new IllegalStateException("URL do serviço de pedido não foi configurada, parâmetro inválido");
        return pedidoURL+atendimento.getIdPedido()+"?status="+ PedidoStatusEnum.get(atendimento.getSituacao());
    }
    public void setPedidoURL(String pedidoURL) {
        this.pedidoURL = pedidoURL;
    }
}