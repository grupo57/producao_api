package br.com.fiap.soat07.techchallenge.cozinha.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.techchallenge.cozinha.core.exception.PedidoJaAtendidoException;
import br.com.fiap.soat07.techchallenge.cozinha.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto.PedidoDTO;
import br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto.ProdutoDTO;

class CreateAtendimentoUseCaseTest {

    @Mock
    private AtendimentoGateway atendimentoGateway;

    @InjectMocks
    private CreateAtendimentoUseCase createAtendimentoUseCase;

    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        pedidoDTO.setCodigo("123");
        pedidoDTO.setCliente("Cliente X");
        pedidoDTO.setProdutos(new HashSet(List.of(
                new ProdutoDTO(1L, "nome1", "codigo1", TipoProdutoEnum.ACOMPANHAMENTO),
                new ProdutoDTO(2L, "nome2", "codigo2", TipoProdutoEnum.LANCHE)
                )));
    }

    @Test
    void givenNullPedidoDTO_whenExecute_thenThrowsIllegalArgumentException() {
        // Quando o pedidoDTO for null, deve lançar IllegalArgumentException
        assertThatThrownBy(() -> createAtendimentoUseCase.execute(null))
                .isInstanceOf(IllegalArgumentException.class);    }

    @Test
    void givenEmptyCodigo_whenExecute_thenThrowsIllegalArgumentException() {
        pedidoDTO.setCodigo("");

        // Ação & Validação
        assertThatThrownBy(() -> createAtendimentoUseCase.execute(pedidoDTO))
        		.isInstanceOf(IllegalArgumentException.class)
        		.hasMessage("Obrigatório informar o codigo do pedido");
    }
    
    @Test
    void givenNullCodigo_whenExecute_thenThrowsIllegalArgumentException() {
        pedidoDTO.setCodigo(null);
        
        assertThatThrownBy(() -> createAtendimentoUseCase.execute(pedidoDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Obrigatório informar o codigo do pedido");
    }    

    @Test
    void givenPedidoJaAtendido_whenExecute_thenThrowsPedidoJaAtendidoException() {
        // Simula que o pedido já foi atendido
        when(atendimentoGateway.findByPedido(pedidoDTO.getId())).thenReturn(Optional.of(
        		Atendimento.recebido(1L, pedidoDTO.getId(), pedidoDTO.getCodigo(), pedidoDTO.getProdutos()))
        		);
        
        assertThatThrownBy(() -> createAtendimentoUseCase.execute(pedidoDTO))
                .isInstanceOf(PedidoJaAtendidoException.class)
                .hasMessage("Pedido 1 já foi atendido");        
    }

    @Test
    void givenValidPedido_whenExecute_thenCreatesAtendimento() {
        // Mocking comportamento do gateway para criar o atendimento
        when(atendimentoGateway.findByPedido(pedidoDTO.getId()))
        		.thenReturn(Optional.empty());
        when(atendimentoGateway.criar(anyLong(), anyString(), anySet()))
            	.thenReturn(Atendimento.recebido(1L, pedidoDTO.getId(), pedidoDTO.getCodigo(), pedidoDTO.getProdutos()));

        // Ação
        Atendimento atendimento = createAtendimentoUseCase.execute(pedidoDTO);

        // Validação
        // Verifica se o atendimento foi criado corretamente
        assertThat(atendimento).isNotNull();
        verify(atendimentoGateway).criar(
        		eq(1L),
                eq("Cliente X 123"), 
                eq(pedidoDTO.getProdutos())
        );
    }

    @Test
    void givenValidPedidoWithoutCliente_whenExecute_thenUsesCodigoAsCliente() {
        // Pedido sem cliente
        pedidoDTO.setCliente(null);

        // Mocking comportamento do gateway para criar o atendimento
        LocalDateTime inicio = LocalDateTime.now();
        when(atendimentoGateway.findByPedido(pedidoDTO.getId())).thenReturn(Optional.empty());
        when(atendimentoGateway.criar(anyLong(), anyString(), anySet()))
                .thenReturn(Atendimento.recebido(1L, pedidoDTO.getId(), pedidoDTO.getCodigo(), pedidoDTO.getProdutos()));

        // Ação
        Atendimento atendimento = createAtendimentoUseCase.execute(pedidoDTO);

        // Validação
        verify(atendimentoGateway).criar(
        		eq(pedidoDTO.getId()), 
        		eq("123"), 
        		eq(pedidoDTO.getProdutos()));
        assertThat(atendimento).isNotNull();
    }
}
