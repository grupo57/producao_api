package br.com.fiap.soat07.techchallenge.producao.infra.repository.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ServiceConfigProperties.class})
@ExtendWith(SpringExtension.class)
class ServiceConfigPropertiesTest {
    @Autowired
    private ServiceConfigProperties serviceConfigProperties;

    @Test
    void testGetPedidoURLAtendimentoNull() {
        assertThatThrownBy(() -> serviceConfigProperties.getPedidoURL(null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Obrigatório informar o atendimento");
    }


    @Test
    void testGetPedidoURL() {
        // Arrange
        LocalDateTime iniciado = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime preparado = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime concluido = LocalDate.of(1970, 1, 1).atStartOfDay();

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> serviceConfigProperties
                .getPedidoURL(new Atendimento(1L, 1L, "Codigo", iniciado, preparado, concluido, new ArrayList<>())));
    }

}
