package br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.PedidoStatusEnum;

class PedidoTest {

	@Test
	void testConstrutorPadrao() {
        // Criando um Pedido com o construtor padrão
        Pedido pedidoPadrao = new Pedido();

        // Verificando valores iniciais
        assertThat(pedidoPadrao.getId()).isNull();
        assertThat(pedidoPadrao.getNome()).isNull();
        assertThat(pedidoPadrao.getCodigo()).isNull();
        assertThat(pedidoPadrao.getProdutos()).isEmpty();
        assertThat(pedidoPadrao.getStatus()).isEqualTo(PedidoStatusEnum.PREPARO);
    }

	@Test
	void testConstrutor() {
        Pedido pedido = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.FINALIZADO);

        // Verificando valores iniciais
        assertThat(pedido.getId()).isEqualTo(1L);
        assertThat(pedido.getNome()).isEqualTo("Cliente");
        assertThat(pedido.getCodigo()).isEqualTo("1");
        assertThat(pedido.getProdutos()).isEmpty();
        assertThat(pedido.getStatus()).isEqualTo(PedidoStatusEnum.FINALIZADO);
    }	
	
    @Test
    void testEquals_MesmoObjeto() {
        Pedido pedido1 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);

        // O mesmo objeto deve ser igual a si mesmo
        assertThat(pedido1).isEqualTo(pedido1);
    }

    @Test
    void testEquals_ObjetosCriadosIdenticos() {
        Pedido pedido1 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);
        Pedido pedido2 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);

        // Objetos equivalentes (mesmos atributos) devem ser iguais
        assertThat(pedido1).isEqualTo(pedido2);
        // propriedade reflexiva
        assertThat(pedido2).isEqualTo(pedido1);
    }

    @Test
    void testEquals_ObjetosDiferentes() {
        Pedido pedido1 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);
        Pedido pedido2 = new Pedido(1L, "2", "Cliente", PedidoStatusEnum.PREPARO);

        // Objetos com atributos diferentes não devem ser iguais
        assertThat(pedido1).isNotEqualTo(pedido2);
    }

    @Test
    void testEquals_NullObject() {
        Pedido pedido1 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);

        // Comparar com null deve retornar false
        assertThat(pedido1).isNotEqualTo(null);
    }

    @Test
    void testEquals_ClassesDiferentes() {
        Pedido pedido1 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);
        String differentTypeObject = "Some String";

        // Comparar com um tipo diferente deve retornar false
        assertThat(pedido1).isNotEqualTo(differentTypeObject);
    }

    @Test
    void testHashCode_ObjetosEquivalentes() {
        Pedido pedido1 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);
        Pedido pedido2 = new Pedido(1L, "1", "Cliente", PedidoStatusEnum.PREPARO);

        // Objetos equivalentes devem ter o mesmo hashCode
        assertThat(pedido1.hashCode()).isEqualTo(pedido2.hashCode());
    }
	
}
