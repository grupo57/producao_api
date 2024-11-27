package br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SituacaoDoAtendimentoTest {

	@Test
	void testIsNaoEncerrado() {
		assertThat(SituacaoDoAtendimento.INICIADO.isEncerrado()).isFalse();
		assertThat(SituacaoDoAtendimento.RECEBIDO.isEncerrado()).isFalse();
	}

	@Test
	void testIsEncerrado() {
		assertThat(SituacaoDoAtendimento.CANCELADO.isEncerrado()).isTrue();
		assertThat(SituacaoDoAtendimento.ENTREGUE.isEncerrado()).isTrue();
	}

}
