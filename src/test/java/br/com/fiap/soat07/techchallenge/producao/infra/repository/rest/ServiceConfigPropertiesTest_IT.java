package br.com.fiap.soat07.techchallenge.producao.infra.repository.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@SpringBootTest(classes = ServiceConfigProperties.class)
@ExtendWith(SpringExtension.class)
class ServiceConfigPropertiesTest_IT {

    @Value("${spring.microsservico.pedidoURL}")
    private String base;

    @Autowired
    private ServiceConfigProperties config;

    @Test
    void lerPropriedade() {
        ServiceConfigProperties config = new ServiceConfigProperties(base);
        assertThat(config.getBaseURL()).isEqualTo("http://localhost-teste:8080/pedido/");
    }

}
