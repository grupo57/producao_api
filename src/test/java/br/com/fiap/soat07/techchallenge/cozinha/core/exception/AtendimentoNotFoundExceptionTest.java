package br.com.fiap.soat07.techchallenge.cozinha.core.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AtendimentoNotFoundExceptionTest {

	
	@Test
	void testGetMessage() {
		AtendimentoNotFoundException exception = new AtendimentoNotFoundException(12L);

		assertThat(exception.getMessage()).isEqualTo("Não foi encontrado um atendimento com o Id:12");
	}


}
