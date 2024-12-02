package br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PedidoStatusEnumTest {

    @Test
    @DisplayName("Test get(SituacaoDoAtendimento); when 'CANCELADO'; then return 'CANCELADO'")
    void testGet_whenCancelado_thenReturnCancelado() {
        // Arrange, Act and Assert
        assertEquals(PedidoStatusEnum.CANCELADO, PedidoStatusEnum.get(SituacaoDoAtendimento.CANCELADO));
    }

    @Test
    @DisplayName("Test get(SituacaoDoAtendimento); when 'ENTREGUE'; then return 'FINALIZADO'")
    void testGet_whenEntregue_thenReturnFinalizado() {
        // Arrange, Act and Assert
        assertEquals(PedidoStatusEnum.FINALIZADO, PedidoStatusEnum.get(SituacaoDoAtendimento.ENTREGUE));
    }

    @Test
    @DisplayName("Test get(SituacaoDoAtendimento); when 'INICIADO'; then return 'PREPARO'")
    void testGet_whenIniciado_thenReturnPreparo() {
        // Arrange, Act and Assert
        assertEquals(PedidoStatusEnum.PREPARO, PedidoStatusEnum.get(SituacaoDoAtendimento.INICIADO));
    }

    @Test
    @DisplayName("Test get(SituacaoDoAtendimento); when 'PREPARADO'; then return 'PRONTO'")
    void testGet_whenPreparado_thenReturnPronto() {
        // Arrange, Act and Assert
        assertEquals(PedidoStatusEnum.PRONTO, PedidoStatusEnum.get(SituacaoDoAtendimento.PREPARADO));
    }

    @Test
    @DisplayName("Test get(SituacaoDoAtendimento); when 'RECEBIDO'; then return 'PREPARO'")
    void testGet_whenRecebido_thenReturnPreparo() {
        // Arrange, Act and Assert
        assertEquals(PedidoStatusEnum.PREPARO, PedidoStatusEnum.get(SituacaoDoAtendimento.RECEBIDO));
    }
}
