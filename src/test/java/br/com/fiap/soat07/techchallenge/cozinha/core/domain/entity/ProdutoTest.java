package br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.TipoProdutoEnum;

class ProdutoTest {

	@Test
	void testConstrutor_Padrao() {
        // Criando um Produto com o construtor padrão
		Produto produtoPadrao = new Produto();

        // Verificando valores iniciais
        assertThat(produtoPadrao.getId()).isNull();
        assertThat(produtoPadrao.getNome()).isNull();
        assertThat(produtoPadrao.getCodigo()).isNull();
        assertThat(produtoPadrao.getTipoProduto()).isNull();
	}

    @Test
    void testEquals_MesmoObjeto() {
        Produto produto1 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);

        // O mesmo objeto deve ser igual a si mesmo
        assertThat(produto1).isEqualTo(produto1);
    }

    @Test
    void testEquals_ComparandoComNull() {
        Produto produto1 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);

        // Testa a comparação com um objeto nulo
        assertThat(produto1).isNotEqualTo(null); 
    }

    @Test
    void testEquals_ObjetosCriadosIdenticos() {
        Produto produto1 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);
        Produto produto2 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);

        // Objetos equivalentes (mesmos atributos) devem ser iguais
        assertThat(produto1).isEqualTo(produto2);
        // propriedade reflexiva
        assertThat(produto2).isEqualTo(produto1);
    }

    @Test
    void testEquals_ObjetosDiferentes() {
        Produto produto1 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);
        Produto produto2 = new Produto("002", "Produto B", TipoProdutoEnum.LANCHE);

        // Objetos com atributos diferentes não devem ser iguais
        assertThat(produto1).isNotEqualTo(produto2);
    }

    @Test
    void testEquals_NullObject() {
        Produto produto1 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);

        // Comparar com null deve retornar false
        assertThat(produto1).isNotEqualTo(null);
    }

    @Test
    void testEquals_ClassesDiferentes() {
        Produto produto1 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);
        String differentTypeObject = "Some String";

        // Comparar com um tipo diferente deve retornar false
        assertThat(produto1).isNotEqualTo(differentTypeObject);
    }

    @Test
    void testHashCode_ObjetosEquivalentes() {
        Produto produto1 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);
        Produto produto2 = new Produto("001", "Produto A", TipoProdutoEnum.ACOMPANHAMENTO);

        // Objetos equivalentes devem ter o mesmo hashCode
        assertThat(produto1.hashCode()).isEqualTo(produto2.hashCode());
    }
	
}
