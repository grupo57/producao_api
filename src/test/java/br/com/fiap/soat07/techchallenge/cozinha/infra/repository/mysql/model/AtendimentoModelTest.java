package br.com.fiap.soat07.techchallenge.cozinha.infra.repository.mysql.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;

class AtendimentoModelTest {

	private LocalDate data;
	
    @BeforeEach
    void setUp() {
		data = LocalDate.now();
	}
	
	@Test
	void testConstrutorPadrao() {
        // Criando um Pedido com o construtor padrão
		AtendimentoModel atendimento = new AtendimentoModel();

        // Verificando valores iniciais
        assertThat(atendimento.getId()).isNull();
        assertThat(atendimento.getCodigo()).isNull();
        assertThat(atendimento.getData()).isEqualTo(data);
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.RECEBIDO);
    }

	@Test
	void testConstrutor() {
		AtendimentoModel atendimento = new AtendimentoModel(1L, "A1");

        // Verificando valores iniciais
        assertThat(atendimento.getId()).isNull();
        assertThat(atendimento.getCodigo()).isEqualTo("A1");
        assertThat(atendimento.getData()).isEqualTo(data);
        assertThat(atendimento.getSituacao()).isEqualTo(SituacaoDoAtendimento.RECEBIDO);
    }	

    @Test
    void testEquals_MesmoObjeto() {
        AtendimentoModel a1 = new AtendimentoModel(1L, "A1");

        // O mesmo objeto deve ser igual a si mesmo
        assertThat(a1).isEqualTo(a1);
    }

    @Test
    void testEquals_ObjetosCriadosIdenticos() {
    	AtendimentoModel a1 = new AtendimentoModel(1L, "A1");
    	AtendimentoModel a2 = new AtendimentoModel(1L, "A1");

        // Objetos equivalentes (mesmos atributos) devem ser iguais
        assertThat(a1).isEqualTo(a2);
        // propriedade reflexiva
        assertThat(a2).isEqualTo(a1);
    }

    @Test
    void testEquals_ObjetosDiferentes() {
    	AtendimentoModel a1 = new AtendimentoModel(1L, "A1");
    	AtendimentoModel a2 = new AtendimentoModel(2L, "A2");

        // Objetos com atributos diferentes não devem ser iguais
        assertThat(a1).isNotEqualTo(a2);
    }

    @Test
    void testEquals_NullObject() {
    	AtendimentoModel a1 = new AtendimentoModel(1L, "A1");

        // Comparar com null deve retornar false
        assertThat(a1).isNotEqualTo(null);
    }

    @Test
    void testEquals_ClassesDiferentes() {
    	AtendimentoModel a1 = new AtendimentoModel(1L, "A1");
        String differentTypeObject = "Some String";

        // Comparar com um tipo diferente deve retornar false
        assertThat(a1).isNotEqualTo(differentTypeObject);
    }

    @Test
    void testHashCode_ObjetosEquivalentes() {
    	AtendimentoModel a1 = new AtendimentoModel(1L, "A1");
    	AtendimentoModel a2 = new AtendimentoModel(1L, "A1");

        // Objetos equivalentes devem ter o mesmo hashCode
        assertThat(a1.hashCode()).isEqualTo(a2.hashCode());
    }
	
    @Test
    void testToString() {
    	AtendimentoModel atendimento = new AtendimentoModel(1L, "A123");
    	
        String expectedToString = "AtendimentoModel{" +
                "id=null"+
                ", pedido='1'" +
                ", data='"+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(data)+"'" +
                ", codigo='A123'" +
                ", status=RECEBIDO" +
                '}';

        assertThat(atendimento.toString()).isEqualTo(expectedToString);
    }    
}
