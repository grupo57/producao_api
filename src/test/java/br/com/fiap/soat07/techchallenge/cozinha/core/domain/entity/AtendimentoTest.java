package br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto.ProdutoDTO;

class AtendimentoTest {

    private Atendimento atendimento;

    private Long id = 1L;
    private Long idPedido = 123L;
    private String codigo = "ABC123";
    
    LocalDateTime agora = LocalDateTime.now();
    private LocalDateTime recebido = agora.minusMinutes(5);
    private LocalDateTime iniciado = agora.minusMinutes(8);
    private LocalDateTime preparado = agora.minusMinutes(10);
    private LocalDateTime concluido = agora;
    private Set<ProdutoDTO> produtos = new HashSet<>(List.of(
            new ProdutoDTO(1L, "nome1", "codigo1", TipoProdutoEnum.ACOMPANHAMENTO),
            new ProdutoDTO(2L, "nome2", "codigo2", TipoProdutoEnum.LANCHE)
            ));

    @BeforeEach
    void setUp() {
        atendimento = new Atendimento(id, idPedido, codigo, SituacaoDoAtendimento.ENTREGUE, recebido, iniciado, preparado, concluido, produtos);
    }

    @Test
    void testAtendimentoConstrutorPadrao() {
        // Criando um Atendimento com o construtor padrão
        Atendimento atendimentoPadrao = new Atendimento();

        assertThat(atendimentoPadrao.getSituacao()).isEqualTo(SituacaoDoAtendimento.RECEBIDO);
        assertThat(atendimentoPadrao.getRecebido()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(atendimentoPadrao.getIniciado()).isNull();
        assertThat(atendimentoPadrao.getPreparado()).isNull();
        assertThat(atendimentoPadrao.getConcluido()).isNull();
    }

    @Test
    void testAtendimentoConstrutorComInicioNull() {
        // Criando um Atendimento com o construtor padrão
        Atendimento atendimentoPadrao = new Atendimento(id, idPedido, codigo, null, null, null, produtos);

        assertThat(atendimentoPadrao.getSituacao()).isEqualTo(SituacaoDoAtendimento.RECEBIDO);
        assertThat(atendimentoPadrao.getRecebido()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(atendimentoPadrao.getIniciado()).isNull();
        assertThat(atendimentoPadrao.getPreparado()).isNull();
        assertThat(atendimentoPadrao.getConcluido()).isNull();
    }

    @Test
    void testAtendimentoConstrutor() {
        // Verificando se o construtor inicializa corretamente os campos
        assertThat(atendimento.getId()).isEqualTo(id);
        assertThat(atendimento.getIdPedido()).isEqualTo(idPedido);
        assertThat(atendimento.getCodigo()).isEqualTo(codigo);
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE);
        assertThat(atendimento.getIniciado()).isEqualTo(iniciado);
        assertThat(atendimento.getPreparado()).isEqualTo(preparado);
        assertThat(atendimento.getConcluido()).isEqualTo(concluido);
    }

    @Test
    void testRecebido() {
        // Criando um Atendimento usando o método 'recebido'
        Atendimento atendimentoRecebido = Atendimento.recebido(id, idPedido, codigo, produtos);

        // Verificando se o atendimento foi criado com a situação 'RECEBIDO'
        assertThat(atendimentoRecebido.getSituacao()).isEqualTo(SituacaoDoAtendimento.RECEBIDO);
        assertThat(atendimentoRecebido.getId()).isEqualTo(id);
        assertThat(atendimentoRecebido.getIdPedido()).isEqualTo(idPedido);
        assertThat(atendimentoRecebido.getCodigo()).isEqualTo(codigo);
        assertThat(atendimentoRecebido.getIniciado()).isNull();
        assertThat(atendimentoRecebido.getPreparado()).isNull();
        assertThat(atendimentoRecebido.getConcluido()).isNull();
    }

    @Test
    void testGettersSetters() {
    	
        atendimento = new Atendimento(id, idPedido, codigo, null, null, null, produtos);
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.RECEBIDO);
        assertThat(atendimento.getIniciado()).isNull();
        assertThat(atendimento.getPreparado()).isNull();
        assertThat(atendimento.getConcluido()).isNull();    	

        // Testando o setter para 'iniciado'
        atendimento = new Atendimento(id, idPedido, codigo, null, null, null, produtos);
        atendimento.iniciado();
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.INICIADO);
        assertThat(atendimento.getIniciado()).isNotNull();
        assertThat(atendimento.getPreparado()).isNull();
        assertThat(atendimento.getConcluido()).isNull();

        // Testando o setter para 'preparado'
        atendimento = new Atendimento(id, idPedido, codigo, null, null, null, produtos);
        atendimento.iniciado();
        atendimento.preparado();
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.PREPARADO);
        assertThat(atendimento.getIniciado()).isNotNull();
        assertThat(atendimento.getPreparado()).isNotNull();
        assertThat(atendimento.getConcluido()).isNull();
        
        // Testando o setter para 'concluido'
        atendimento = new Atendimento(id, idPedido, codigo, null, null, null, produtos);
        atendimento.iniciado();
        atendimento.preparado();
        atendimento.entregue();
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE);
        assertThat(atendimento.getIniciado()).isNotNull();
        assertThat(atendimento.getPreparado()).isNotNull();
        assertThat(atendimento.getConcluido()).isNotNull();

        // Testando o setter para 'cancelado'
        atendimento = new Atendimento(id, idPedido, codigo, null, null, null, produtos);
        atendimento.iniciado();
        atendimento.cancelado();
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.CANCELADO);
        assertThat(atendimento.getIniciado()).isNotNull();
        assertThat(atendimento.getConcluido()).isNotNull();
    }
    
    @Test
    void testSeEstaFinalizadoNaoPodeCancelar() {
        // Testando o setter para 'concluido'
        atendimento.entregue();
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE);
        assertThat(atendimento.getConcluido()).isNotNull();

        // Testando o setter para 'cancelado'
        atendimento.cancelado();
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.ENTREGUE);
        assertThat(atendimento.getConcluido()).isNotNull();
    }
    
    @Test
    void testToString() {
    	atendimento = new Atendimento(id, idPedido, codigo, null, null, null, produtos);
        String expectedString = "Atendimento{id=" + id + ", pedido='" + idPedido + "', codigo='" + codigo + "', situacao='RECEBIDO'}";

        assertThat(atendimento.toString()).isEqualTo(expectedString);
    }
    
    @Test
    void testEquals_MesmoObjeto() {
    	
    	// O mesmo objeto deve ser igual a si mesmo
        assertThat(atendimento).isEqualTo(atendimento);  // Espera que os objetos sejam iguais
    }

    @Test
    void testEquals_ObjetosCriadosIdenticos() {
        Atendimento atendimento1 = new Atendimento(id, idPedido, codigo, null, null, null, produtos);
        Atendimento atendimento2 = new Atendimento(id, idPedido, codigo, null, null, null, produtos);

        // Objetos equivalentes (mesmos atributos) devem ser iguais
        assertThat(atendimento1).isEqualTo(atendimento2);
        // propriedade reflexiva
        assertThat(atendimento2).isEqualTo(atendimento1);
    }

    @Test
    void testEquals_NullObject() {
        Atendimento atendimento1 = new Atendimento(id, idPedido, codigo, null, null, null, produtos);

        // Testa a comparação com um objeto nulo
        assertThat(atendimento1).isNotEqualTo(null);  
    }

    @Test
    void testEquals_ClassesDiferentes() {
        Atendimento atendimento1 = new Atendimento(id, idPedido, codigo, null, null, null, produtos);

        // Testa a comparação com um objeto de classe diferente
        String outroObjeto = "Um outro objeto qualquer";
        assertThat(atendimento1).isNotEqualTo(outroObjeto);  
    }

    @Test
    void testHashCode_ObjetosIguais() {
        Atendimento atendimento1 = new Atendimento(id, idPedido, "DEF", null, null, null, produtos);
        Atendimento atendimento2 = new Atendimento(id, idPedido, "DEF", null, null, null, produtos);

        assertThat(atendimento1.hashCode()).isEqualTo(atendimento2.hashCode());
    }

    @Test
    void testHashCode_ObjetosDiferentes() {
        Atendimento atendimento1 = new Atendimento(id, idPedido, "ABC", null, null, null, produtos);
        Atendimento atendimento2 = new Atendimento(id, idPedido, "DEF", null, null, null, produtos);

        assertThat(atendimento1.hashCode()).isNotEqualTo(atendimento2.hashCode());
    }

    @Test
    void testHashCode_Consistente() {
        Atendimento atendimento1 = new Atendimento(id, idPedido, "ABC", null, null, null, produtos);

        // Testa se o hashCode é consistente (não deve mudar entre chamadas)
        int hashCode1 = atendimento1.hashCode();
        int hashCode2 = atendimento1.hashCode();
        assertThat(hashCode1).isEqualTo(hashCode2);  
    }
    
}
