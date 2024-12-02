package br.com.fiap.soat07.techchallenge.producao.infra.repository.rest;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.PedidoStatusEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.microsservico")
public class ServiceConfigProperties {

    private String baseURL;

    ServiceConfigProperties() {}
    ServiceConfigProperties(String base) {
        this.baseURL = base;
    }

    public String getBaseURL() {
        if (baseURL != null && !baseURL.endsWith("/")) {
            baseURL = baseURL+ "/";
        }
        return baseURL;
    }

    public String getPedidoURL(Atendimento atendimento) {
        if (getBaseURL() == null)
            throw new IllegalStateException("URL base do serviço de pedido não foi configurada, parâmetro inválido");

        if (atendimento == null)
            throw new IllegalStateException("Obrigatório informar o atendimento");

        return getBaseURL()+atendimento.getIdPedido()+"?status="+ PedidoStatusEnum.get(atendimento.getSituacao());
    }

}