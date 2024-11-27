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

class UpdateAtendimentoSituacaoIniciadoUseCaseTest {

    private UpdateAtendimentoSituacaoIniciadoUseCase updateAtendimentoSituacaoIniciado;

    @Mock
    private AtendimentoGateway atendimentoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateAtendimentoSituacaoIniciado = new UpdateAtendimentoSituacaoIniciadoUseCase(atendimentoGateway);
    }

    @Test
    void testExecute_parametroNull() {
        assertThatThrownBy(() -> updateAtendimentoSituacaoIniciado.execute(null))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testExecute_recebidoParaIniciado() {
		Atendimento atendimentoRecebido = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		Atendimento atendimentoIniciado = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
    	atendimentoIniciado.iniciado();
		
        // Testa se a situação é atualizada quando o atendimento está em RECEBIDO
        when(atendimentoGateway.save(atendimentoRecebido)).thenReturn(atendimentoIniciado);

        Atendimento result = updateAtendimentoSituacaoIniciado.execute(atendimentoRecebido);

        assertThat(result).isNotNull();
        assertThat(result.getSituacao()).isEqualTo(SituacaoDoAtendimento.INICIADO); 
        verify(atendimentoGateway).save(atendimentoRecebido);
    }

    @Test
    void testExecute_iniciadoParaIniciado() {
		Atendimento atendimentoIniciado = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimentoIniciado.iniciado();
		
		// Testa a transição de INICIADO para INICIADO		
        when(atendimentoGateway.save(atendimentoIniciado)).thenReturn(atendimentoIniciado);

        Atendimento result = updateAtendimentoSituacaoIniciado.execute(atendimentoIniciado);

        assertThat(result).isNotNull();
        assertThat(result.getSituacao()).isEqualTo(SituacaoDoAtendimento.INICIADO);  
        verifyNoInteractions(atendimentoGateway);  
    }    
    
    @Test
    void testExecute_finalizadoParaIniciado_naoDeveriaAtualizar() {
		Atendimento atendimentoFinalizado = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimentoFinalizado.entregue();
		
		// Testa se o atendimento não é alterado quando já está na situação FINALIZADO (transição inválida)
        assertThatThrownBy(() -> updateAtendimentoSituacaoIniciado.execute(atendimentoFinalizado))
        	.isInstanceOf(IllegalStateException.class)
        	.hasMessage("atendimento já finalizado");
    }
    
    @Test
    void testExecute_canceladoParaIniciado_naoDeveriaAtualizar() {
		Atendimento atendimentoCancelado = new Atendimento(1L, 1L, "123", null, null, null, Collections.emptyList());
		atendimentoCancelado.cancelado();

		// Testa se o atendimento não é alterado quando já está na situação CANCELADO (transição inválida)
        assertThatThrownBy(() -> updateAtendimentoSituacaoIniciado.execute(atendimentoCancelado))
        	.isInstanceOf(IllegalStateException.class)
        	.hasMessage("atendimento cancelado");
    }    

}