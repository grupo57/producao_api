package br.com.fiap.soat07.techchallenge.producao.infra.rest;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import br.com.fiap.soat07.techchallenge.producao.core.gateway.PedidoGateway;
import br.com.fiap.soat07.techchallenge.producao.infra.repository.rest.PedidoRestImpl;
import br.com.fiap.soat07.techchallenge.producao.infra.repository.rest.ServiceConfigProperties;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration.PedidoStatusEnum;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class producaoControllerTest_IT extends BaseIT {

    @MockBean
    private ServiceConfigProperties serviceConfigProperties;

    @MockBean
    private PedidoGateway pedidoGateway;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(serviceConfigProperties.getBaseURL()).thenReturn("http://teste.com");
        when(serviceConfigProperties.getPedidoURL(any())).thenReturn("http://teste.com/pedido/1");
        doNothing().when(pedidoGateway).update(any(), any());
    }

    @Test
    void getListarAtendimentos() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/producao/atendimentos")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.notNullValue());
    }

    @Test
    void getAtendimento() {
        RestAssured
                .given()
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/producao/atendimentos/4")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(9))
                .body("id", notNullValue())
                .body("idPedido", is(4))
                .body("codigo", is("125"));
    }

    @Test
    void getAtendimentoNotFound() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/producao/atendimentos/0")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getCriarAtendimento() {
        Pedido pedido = new Pedido(1245L, "IT001", "", PedidoStatusEnum.PREPARO);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(toJson(pedido))
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .post("/producao/atendimentos")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void getAtendimentoParaIniciado() {
        when(serviceConfigProperties.getBaseURL()).thenReturn("http://teste.com");
        when(serviceConfigProperties.getPedidoURL(any())).thenReturn("http://teste.com/pedido/1");
        doNothing().when(pedidoGateway).update(any(), any());

        RestAssured
                .given()
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/producao/atendimentos/6/iniciado")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(9))
                .body("id", notNullValue())
                .body("idPedido", is(6))
                .body("codigo", is("123"))
                .body("situacao", is("INICIADO"))
                .body("iniciado", notNullValue())
                .body("concluido", nullValue());
    }

    @Test
    void getAtendimentoParaConcluido() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/producao/atendimentos/2/concluido")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(9))
                .body("id", notNullValue())
                .body("idPedido", is(2))
                .body("codigo", is("124"))
                .body("situacao", is("ENTREGUE"))
                .body("iniciado", nullValue())
                .body("concluido", notNullValue());
    }
}