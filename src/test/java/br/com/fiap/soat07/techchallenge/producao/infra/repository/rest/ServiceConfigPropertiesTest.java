package br.com.fiap.soat07.techchallenge.producao.infra.repository.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.SituacaoDoAtendimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ContextConfiguration(classes = {ServiceConfigProperties.class})
//@ExtendWith(SpringExtension.class)
class ServiceConfigPropertiesTest {

    private ServiceConfigProperties config;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        config = new ServiceConfigProperties();
        config = spy(config);
    }

    @Test
    void testUrlBaseNull() {
        when(config.getBaseURL()).thenReturn(null);

        assertThatThrownBy(() -> config.getPedidoURL(any()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("URL base do serviço de pedido não foi configurada, parâmetro inválido");
    }

    @Test
    void testGetPedidoURLAtendimentoNull() {
        when(config.getBaseURL()).thenReturn("http://teste.com/");

        assertThatThrownBy(() -> config.getPedidoURL(null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Obrigatório informar o atendimento");
    }

    @Test
    void testUrlBaseSemBarra() {
        ServiceConfigProperties config = new ServiceConfigProperties("http://teste.com");

        assertThat(config.getBaseURL()).isEqualTo("http://teste.com/");
    }

    @Test
    void testGetPedidoURL() {
        LocalDateTime iniciado = LocalDate.of(1970, 1, 1).atStartOfDay();
        Atendimento atendimento = new Atendimento(1L, 1L, "Codigo", iniciado, null, null, new ArrayList<>());

        when(config.getBaseURL()).thenReturn("http://teste.com/pedido/");
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.RECEBIDO);
        assertThat(config.getPedidoURL(atendimento)).isEqualTo("http://teste.com/pedido/1?status="+ PedidoStatusEnum.get(SituacaoDoAtendimento.RECEBIDO));
    }

}
