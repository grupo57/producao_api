package br.com.fiap.soat07.techchallenge.cozinha.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.gateway.AtendimentoGateway;

class UpdateAtendimentoSituacaoConcluidoUseCaseTest {

    private UpdateAtendimentoSituacaoConcluidoUseCase updateAtendimentoSituacaoConcluido;

    @Mock
    private AtendimentoGateway atendimentoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateAtendimentoSituacaoConcluido = new UpdateAtendimentoSituacaoConcluidoUseCase(atendimentoGateway);
    }

    @Test
    void testExecute_parametroNull() {
        assertThatThrownBy(() -> updateAtendimentoSituacaoConcluido.execute(null))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testExecute_recebidoParaFinalizado() {
		Atendimento atendimentoRecebido = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		Atendimento atendimentoConcluido = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimentoConcluido.entregue();
		
        // Testa se a situação é atualizada quando o atendimento está em RECEBIDO
        when(atendimentoGateway.save(atendimentoRecebido)).thenReturn(atendimentoConcluido);

        Atendimento result = updateAtendimentoSituacaoConcluido.execute(atendimentoRecebido);

        assertThat(result).isNotNull();
        assertThat(result.getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE); 
        verify(atendimentoGateway).save(atendimentoRecebido);
    }

    @Test
    void testExecute_iniciadoParaConcluido() {
		Atendimento atendimentoIniciado = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimentoIniciado.iniciado();
		
		// Testa a transição de INICIADO para CONCLUIDO
        when(atendimentoGateway.save(atendimentoIniciado)).thenReturn(atendimentoIniciado);

        Atendimento result = updateAtendimentoSituacaoConcluido.execute(atendimentoIniciado);

        assertThat(result).isNotNull();
        assertThat(result.getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE);  
//        verifyNoInteractions(atendimentoGateway);  
    }    
    
    @Test
    void testExecute_finalizadoParaConcluido() {
		Atendimento atendimentoFinalizado = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimentoFinalizado.entregue();
		
        Atendimento result = updateAtendimentoSituacaoConcluido.execute(atendimentoFinalizado);

        assertThat(result).isNotNull();
        assertThat(result.getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE);  
        verifyNoInteractions(atendimentoGateway);  
    }
    
    @Test
    void testExecute_canceladoParaConcluido_naoDeveriaAtualizar() {
		Atendimento atendimentoCancelado = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimentoCancelado.cancelado();

		// Testa se o atendimento não é alterado quando já está na situação CANCELADO (transição inválida)
        assertThatThrownBy(() -> updateAtendimentoSituacaoConcluido.execute(atendimentoCancelado))
        	.isInstanceOf(IllegalStateException.class)
        	.hasMessage("atendimento cancelado");
    }    


}