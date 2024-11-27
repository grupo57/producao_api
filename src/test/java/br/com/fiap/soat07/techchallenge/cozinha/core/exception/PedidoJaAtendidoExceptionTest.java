package br.com.fiap.soat07.techchallenge.cozinha.core.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PedidoJaAtendidoExceptionTest {

	@Test
	void testGetIdPedido() {
		PedidoJaAtendidoException exception = new PedidoJaAtendidoException(12L);

		assertThat(exception.getPedidoId()).isEqualTo(12L);
	}

	@Test
	void testGetMessage() {
		PedidoJaAtendidoException exception = new PedidoJaAtendidoException(12L);

		assertThat(exception.getMessage()).isEqualTo("Pedido 12 já foi atendido");
	}

}
