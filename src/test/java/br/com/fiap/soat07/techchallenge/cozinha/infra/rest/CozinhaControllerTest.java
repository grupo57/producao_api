package br.com.fiap.soat07.techchallenge.cozinha.infra.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.exception.PedidoJaAtendidoException;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.CreateAtendimentoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.SearchAtendimentoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.UpdateAtendimentoSituacaoConcluidoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.core.usecase.UpdateAtendimentoSituacaoIniciadoUseCase;
import br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto.PedidoDTO;
import br.com.fiap.soat07.techchallenge.cozinha.infra.service.CozinhaService;

//@SpringBootTest
@RestClientTest(CozinhaController.class)
class CozinhaControllerTest {

	@Autowired 
	private CozinhaController controller;
	
    @MockBean
    private CozinhaService cozinhaService;

    @MockBean
    private CreateAtendimentoUseCase createAtendimentoUseCase;

    @MockBean
    private SearchAtendimentoUseCase searchAtendimentoUseCase;

    @MockBean
    private UpdateAtendimentoSituacaoIniciadoUseCase updateAtendimentoIniciado;
    
    @MockBean
    private UpdateAtendimentoSituacaoConcluidoUseCase updateAtendimentoConcluido;
    
    
	@BeforeEach
    void setUp() {
		// Mock dos serviços
        when(cozinhaService.getCreateAtendimentoUseCase()).thenReturn(createAtendimentoUseCase);
        when(cozinhaService.getSearchAtendimentoUseCase()).thenReturn(searchAtendimentoUseCase);
        when(cozinhaService.getUpdateAtendimentoSituacaoIniciado()).thenReturn(updateAtendimentoIniciado);
        when(cozinhaService.getUpdateAtendimentoSituacaoConcluido()).thenReturn(updateAtendimentoConcluido);
	}

	@Test
	void testGetListar() {
		// given
		Atendimento atendimento = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList()); 
		Collection<Atendimento> atendimentos = Arrays.asList(atendimento);

		// when
		when(searchAtendimentoUseCase.find()).thenReturn(atendimentos);

		// then
        var response = controller.listarAtendimentosAbertos();
        assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(atendimentos);
	}

	
	@Test
	void testGetListarErro500() throws Exception {
		// given
		// when
		when(searchAtendimentoUseCase.find()).thenThrow(new RuntimeException("Mensagem de erro"));

		// then
		var response = controller.listarAtendimentosAbertos();
        assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Mensagem de erro");
	}
	
	@Test
	void testCreatePostNull() throws Exception {
		// given
		// when
		// then
        var response = controller.createAtendimento(null);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("Obrigatório informar o pedido");
	}

	@Test
	void testCreatePost() throws Exception {
		// given
		Atendimento atendimento = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList()); 
		PedidoDTO pedido = new PedidoDTO();
		
		// when
		when(createAtendimentoUseCase.execute(any(PedidoDTO.class))).thenReturn(atendimento);

		// then
        var response = controller.createAtendimento(pedido);
        assertThat(response.getStatusCode().value()).isEqualTo(201);
		assertThat(response.getHeaders().getLocation()).isEqualTo(URI.create("/cozinha/atendimentos/1"));
	}

	@Test
	void testCreatePostJaExiste() throws Exception {
		// given
		PedidoDTO pedido = new PedidoDTO();
		
		// when
		when(createAtendimentoUseCase.execute(any(PedidoDTO.class))).thenThrow(new PedidoJaAtendidoException(1));

		// then
        var response = controller.createAtendimento(pedido);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("Pedido 1 já foi atendido");
	}
	
	@Test
	void testCreatePostErro500() throws Exception {
		// given
		PedidoDTO pedido = new PedidoDTO();
		
		// when
		when(createAtendimentoUseCase.execute(any(PedidoDTO.class))).thenThrow(new RuntimeException("Mensagem de erro"));

		// then
        var response = controller.createAtendimento(pedido);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Mensagem de erro");
	}
	
	//
	
	@Test
	void testGetParametroNull() throws Exception {
		// given
		// when
		// then
        var response = controller.get(null);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("Obrigatório informar o número do atendimento");
	}
	
	@Test
	void testGet() throws Exception {
		// given
		Atendimento atendimento = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList()); 
		
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.of(atendimento));

		// then
        var response = controller.get(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(atendimento);
	}

	@Test
	void testGetErro404() throws Exception {
		// given
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.empty());

		// then
		var response = controller.get(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
	}
	
	@Test
	void testGetErro500() throws Exception {
		// given
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenThrow(new RuntimeException("Mensagem de erro"));

		// then
        var response = controller.get(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Mensagem de erro");
	}	
	
	//
	
	@Test
	void testIniciadoParametroNull() throws Exception {
		// given
		// when
		// then
        var response = controller.iniciado(null);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("Obrigatório informar o número do atendimento");
	}	

	@Test
	void testIniciado() throws Exception {
		// given
		Atendimento atendimento = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList()); 
		atendimento.iniciado();
		
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.of(atendimento));
		when(updateAtendimentoIniciado.execute(any(Atendimento.class))).thenReturn(atendimento);

		// then
        var response = controller.iniciado(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(atendimento);
		assertThat(((Atendimento)response.getBody()).getSituacao()).isEqualTo(SituacaoDoAtendimento.INICIADO);
	}

	@Test
	void testIniciadoErro404() throws Exception {
		// given
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.empty());

		// then
		var response = controller.iniciado(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
	}
	
	@Test
	void testIniciadoErro500_ErroNaBusca() throws Exception {
		// given
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenThrow(new RuntimeException("Mensagem de erro"));

		// then
        var response = controller.iniciado(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Mensagem de erro");
	}	

	@Test
	void testIniciadoErro500_ErroNoUpdate() throws Exception {
		// given
		Atendimento atendimento = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimento.iniciado();
		
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.of(atendimento));
		when(updateAtendimentoIniciado.execute(any(Atendimento.class))).thenThrow(new RuntimeException("Mensagem de erro"));

		// then
        var response = controller.iniciado(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Mensagem de erro");
	}	

	
	// CONLUIDO
	
	@Test
	void testConcluidoParametroNull() throws Exception {
		// given
		// when
		// then
        var response = controller.entregue(null);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("Obrigatório informar o número do atendimento");
	}	

	@Test
	void testConcluido() throws Exception {
		// given
		Atendimento atendimento = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimento.entregue();
		
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.of(atendimento));
		when(updateAtendimentoConcluido.execute(any(Atendimento.class))).thenReturn(atendimento);

		// then
        var response = controller.entregue(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(atendimento);
		assertThat(((Atendimento)response.getBody()).getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE);
	}

	@Test
	void testConcluidoErro404() throws Exception {
		// given
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.empty());

		// then
		var response = controller.entregue(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
	}
	
	@Test
	void testConcluidoErro500_ErroNaBusca() throws Exception {
		// given
		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenThrow(new RuntimeException("Mensagem de erro"));

		// then
        var response = controller.entregue(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Mensagem de erro");
	}	

	@Test
	void testConcluidoErro500_ErroNoUpdate() throws Exception {
		// given
		Atendimento atendimento = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimento.iniciado();

		// when
		when(searchAtendimentoUseCase.findByPedido(any(Long.class))).thenReturn(Optional.of(atendimento));
		when(updateAtendimentoConcluido.execute(any(Atendimento.class))).thenThrow(new RuntimeException("Mensagem de erro"));

		// then
        var response = controller.entregue(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Mensagem de erro");
	}	
	
	
}
