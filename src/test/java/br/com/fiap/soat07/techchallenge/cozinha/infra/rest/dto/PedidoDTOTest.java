package br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.TipoProdutoEnum;

class PedidoDTOTest {

    private PedidoDTO pedidoDTO;
    // Usado para serialização e desserialização JSON
    private ObjectMapper objectMapper;  

    @BeforeEach
    void setUp() {
        // Inicializa o pedidoDTO e o ObjectMapper antes de cada teste
        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        pedidoDTO.setCliente("Cliente A");
        pedidoDTO.setCodigo("P123");

        Set<ProdutoDTO> produtos = new HashSet<>();
        ProdutoDTO produtoDTO = new ProdutoDTO(1L, "Produto A", "P123", TipoProdutoEnum.ACOMPANHAMENTO);
        produtos.add(produtoDTO);
        pedidoDTO.setProdutos(produtos);

        objectMapper = new ObjectMapper();
    }

    @Test
    void testGettersAndSetters() {
        // Testa os getters e setters
        pedidoDTO.setId(2L);
        pedidoDTO.setCliente("Cliente B");
        pedidoDTO.setCodigo("P456");

        Set<ProdutoDTO> produtos = new HashSet<>();
        ProdutoDTO produtoDTO = new ProdutoDTO(2L, "Produto B", "P456", TipoProdutoEnum.ACOMPANHAMENTO);
        produtos.add(produtoDTO);
        pedidoDTO.setProdutos(produtos);

        assertThat(pedidoDTO.getId()).isEqualTo(2L);
        assertThat(pedidoDTO.getCliente()).isEqualTo("Cliente B");
        assertThat(pedidoDTO.getCodigo()).isEqualTo("P456");
        assertThat(pedidoDTO.getProdutos()).hasSize(1);
        assertThat(pedidoDTO.getProdutos().iterator().next().getNome()).isEqualTo("Produto B");
    }

    @Test
    void testSerializacaoJson() throws Exception {
        String json = objectMapper.writeValueAsString(pedidoDTO);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"cliente\":\"Cliente A\"");
        assertThat(json).contains("\"codigo\":\"P123\"");
        assertThat(json).contains("\"produtos\":[");
        assertThat(json).contains("\"nome\":\"Produto A\"");
        assertThat(json).contains("\"tipo\":\"ACOMPANHAMENTO\"");
    }

    @Test
    void testDesserializacaoJson() throws Exception {
        // Testa a desserialização de JSON para PedidoDTO
        String json = "{\"id\":1,\"cliente\":\"Cliente A\",\"codigo\":\"A123\",\"produtos\":[{\"id\":1,\"nome\":\"Produto A\",\"codigo\":\"A123\",\"tipo\":\"ACOMPANHAMENTO\"}]}";
        PedidoDTO pedido = objectMapper.readValue(json, PedidoDTO.class);

        assertThat(pedido.getId()).isEqualTo(1L);
        assertThat(pedido.getCliente()).isEqualTo("Cliente A");
        assertThat(pedido.getCodigo()).isEqualTo("A123");
        assertThat(pedido.getProdutos()).hasSize(1);
        ProdutoDTO produto = pedido.getProdutos().iterator().next();
        assertThat(produto.getNome()).isEqualTo("Produto A");
        assertThat(produto.getTipo()).isEqualTo(TipoProdutoEnum.ACOMPANHAMENTO);
    }

    @Test
    void testJsonIgnoreProperties_whenUnknownField_shouldNotThrowException() throws Exception {
        // Testa se a anotação @JsonIgnoreProperties funciona e ignora campos desconhecidos
        String jsonWithUnknownField = "{\"id\":1,\"cliente\":\"Cliente A\",\"codigo\":\"A123\",\"produtos\":[{\"id\":1,\"nome\":\"Produto A\",\"codigo\":\"A123\",\"tipo\":\"ACOMPANHAMENTO\"}],\"campoDesconhecido\":\"valor\"}";

        PedidoDTO pedido = objectMapper.readValue(jsonWithUnknownField, PedidoDTO.class);

        assertThat(pedido.getId()).isEqualTo(1L);
        assertThat(pedido.getCliente()).isEqualTo("Cliente A");
        assertThat(pedido.getCodigo()).isEqualTo("A123");
        assertThat(pedido.getProdutos()).hasSize(1);
        ProdutoDTO produto = pedido.getProdutos().iterator().next();
        assertThat(produto.getNome()).isEqualTo("Produto A");
        assertThat(produto.getTipo()).isEqualTo(TipoProdutoEnum.ACOMPANHAMENTO);
        // O campo desconhecido é ignorado e não causa erro
    }

    @Test
    void testProdutosDefaultEmpty() {
        // Testa se o método getProdutos retorna uma coleção vazia quando produtos é null
        pedidoDTO.setProdutos(null);

        assertThat(pedidoDTO.getProdutos()).isEmpty();
    }
}
