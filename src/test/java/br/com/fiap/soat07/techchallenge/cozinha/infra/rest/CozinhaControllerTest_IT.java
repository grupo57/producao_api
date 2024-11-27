package br.com.fiap.soat07.techchallenge.cozinha.infra.rest;

//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.BaseMatcher.*;
//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Pedido;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.PedidoStatusEnum;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CozinhaControllerTest_IT extends BaseIT {

	@BeforeEach
	public void beforeEach() {
	}
	
	
    @Test
    void getListarAtendimentos() {
        RestAssured
                .given()
                	.contentType(ContentType.JSON)
                	.log().all()
                    .accept(ContentType.JSON)
                .when()
                    .get("/cozinha/atendimentos")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.notNullValue())
                    ;
    }

    @Test
    void getAtendimento() {
        RestAssured
            .given()
            	.log().all()
                .accept(ContentType.JSON)
            .when()
                .get("/cozinha/atendimentos/4")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(9))
                .body("id", notNullValue())
                .body("idPedido", is(4))                    
                .body("codigo", is("125"))      
                ;
    }    

    @Test
    void getAtendimentoNotFound() {
        RestAssured
            .given()
            	.contentType(ContentType.JSON)
            	.log().all()
                .accept(ContentType.JSON)
            .when()
                .get("/cozinha/atendimentos/0")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                ;
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
                .post("/cozinha/atendimentos")
            .then()
                .statusCode(HttpStatus.CREATED.value())
//                .body("size()", Matchers.equalTo(8))
//                .body("id", notNullValue())
//                .body("idPedido", is(1245))                    
//                .body("codigo", is("IT001"))                    
//                .body("situacao", is("RECEBIDO"))                    
//                .body("inicio", notNullValue())                    
//                .body("concluido", notNullValue())  
                ;
    }

    @Test
    void getAtendimentoParaIniciado() {
    	
        RestAssured
            .given()
            	.log().all()
                .accept(ContentType.JSON)
            .when()
                .get("/cozinha/atendimentos/6/iniciado")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(9))
                .body("id", notNullValue())
                .body("idPedido", is(6))                    
                .body("codigo", is("123"))                    
                .body("situacao", is("INICIADO"))                    
                .body("iniciado", notNullValue())                    
                .body("concluido", nullValue())  
                ;
    }

    @Test
    void getAtendimentoParaConcluido() {
    	
        RestAssured
            .given()
            	.contentType(ContentType.JSON)
            	.log().all()
                .accept(ContentType.JSON)
            .when()
                .get("/cozinha/atendimentos/2/concluido")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(9))
                .body("id", notNullValue())
                .body("idPedido", is(2))                    
                .body("codigo", is("124"))                    
                .body("situacao", is("ENTREGUE"))                    
                .body("iniciado", nullValue())  
                .body("concluido", notNullValue())  
                ;
    }
}