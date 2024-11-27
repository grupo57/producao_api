package br.com.fiap.soat07.techchallenge.cozinha.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.techchallenge.cozinha.core.gateway.AtendimentoGateway;
import br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto.ProdutoDTO;

class SearchAtendimentoUseCaseTest {

    private SearchAtendimentoUseCase searchAtendimentoUseCase;

    @SuppressWarnings("unchecked")
	private Set<ProdutoDTO> getProdutos() {
    	return new HashSet<ProdutoDTO>(List.of(
            new ProdutoDTO(1L, "nome1", "codigo1", TipoProdutoEnum.ACOMPANHAMENTO),
            new ProdutoDTO(2L, "nome2", "codigo2", TipoProdutoEnum.LANCHE)
            ));
    }
    
    @Mock
    private AtendimentoGateway atendimentoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks
        searchAtendimentoUseCase = new SearchAtendimentoUseCase(atendimentoGateway);
    }

    @Test
    void testFindById_nullId_shouldThrowIllegalArgumentException() {
        // Testa se lançar IllegalArgumentException quando o id for null
        assertThatThrownBy(() -> searchAtendimentoUseCase.findByPedido(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Obrigatório informar o código do pedido");
    }

    @Test
    void testFindById_validId_shouldCallGateway() {
        // Testa se o método findById do gateway é chamado corretamente
        Long id = 1L;
        Atendimento atendimento = Atendimento.recebido(1L, 1L, "123", getProdutos());
        when(atendimentoGateway.findByPedido(id)).thenReturn(Optional.of(atendimento));

        Optional<Atendimento> result = searchAtendimentoUseCase.findByPedido(id);

        assertThat(result).isPresent().contains(atendimento);
        verify(atendimentoGateway).findByPedido(id);
        // Verifica se o método do gateway foi chamado
    }

    @Test
    void testFindByPedido_nullId_shouldThrowIllegalArgumentException() {
        // Testa se lançar IllegalArgumentException quando o id for null
        assertThatThrownBy(() -> searchAtendimentoUseCase.findByPedido(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Obrigatório informar o código do pedido");
    }

    @Test
    void testFindByPedido_validId_shouldCallGateway() {
        // Testa se o método findByPedido do gateway é chamado corretamente
        Long id = 1L;
        Atendimento atendimento = Atendimento.recebido(1L, 1L, "123", getProdutos());
        when(atendimentoGateway.findByPedido(id)).thenReturn(Optional.of(atendimento));

        Optional<Atendimento> result = searchAtendimentoUseCase.findByPedido(id);

        assertThat(result).isNotEmpty();
        verify(atendimentoGateway).findByPedido(id);  // Verifica se o método do gateway foi chamado
    }

    @Test
    void testFindByData_nullData_shouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> searchAtendimentoUseCase.findByData(null, EnumSet.noneOf(SituacaoDoAtendimento.class)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Obrigatório informar a data");
    }

    @Test
    void testFindByData_validData_shouldCallGateway() {
        // Testa se o método findByData do gateway é chamado corretamente
        LocalDate data = LocalDate.now();
        EnumSet<SituacaoDoAtendimento> situacoes = EnumSet.noneOf(SituacaoDoAtendimento.class);
        Atendimento atendimento = Atendimento.recebido(1L, 1L, "123", getProdutos());
        Collection<Atendimento> atendimentos = List.of(atendimento);  
        when(atendimentoGateway.findByData(data, situacoes)).thenReturn(atendimentos);

        Collection<Atendimento> result = searchAtendimentoUseCase.findByData(data, situacoes);

        assertThat(result).isNotEmpty();
        verify(atendimentoGateway).findByData(data, situacoes);  
    }
}
