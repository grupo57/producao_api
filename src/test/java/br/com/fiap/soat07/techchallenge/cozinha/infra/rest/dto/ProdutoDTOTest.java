package br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.TipoProdutoEnum;

class ProdutoDTOTest {

    private ProdutoDTO produtoDTO;
    private ObjectMapper objectMapper;  

    @BeforeEach
    void setUp() {
        produtoDTO = new ProdutoDTO(1L, "Produto A", "A123", TipoProdutoEnum.ACOMPANHAMENTO);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGettersAndSetters() {
        produtoDTO.setId(2L);
        produtoDTO.setNome("Produto B");
        produtoDTO.setCodigo("P456");
        produtoDTO.setTipo(TipoProdutoEnum.ACOMPANHAMENTO);

        assertThat(produtoDTO.getId()).isEqualTo(2L);
        assertThat(produtoDTO.getNome()).isEqualTo("Produto B");
        assertThat(produtoDTO.getCodigo()).isEqualTo("P456");
        assertThat(produtoDTO.getTipo()).isEqualTo(TipoProdutoEnum.ACOMPANHAMENTO);
    }

    @Test
    void testSerializacaoJson() throws Exception {
        // Testa a serialização do ProdutoDTO para JSON
        String json = objectMapper.writeValueAsString(produtoDTO);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"nome\":\"Produto A\"");
        assertThat(json).contains("\"codigo\":\"A123\"");
        assertThat(json).contains("\"tipo\":\"ACOMPANHAMENTO\"");
    }

    @Test
    void testDesserializacaoJson() throws Exception {
        // Testa a desserialização de JSON para ProdutoDTO
        String json = "{\"id\":1,\"nome\":\"Produto A\",\"codigo\":\"A123\",\"tipo\":\"ACOMPANHAMENTO\"}";
        ProdutoDTO produto = objectMapper.readValue(json, ProdutoDTO.class);

        assertThat(produto.getId()).isEqualTo(1L);
        assertThat(produto.getNome()).isEqualTo("Produto A");
        assertThat(produto.getCodigo()).isEqualTo("A123");
        assertThat(produto.getTipo()).isEqualTo(TipoProdutoEnum.ACOMPANHAMENTO);
    }

    @Test
    void testJsonIgnoreProperties_whenUnknownField_shouldNotThrowException() throws Exception {
        // Testa se a anotação @JsonIgnoreProperties funciona e ignora campos desconhecidos
        // Não lança exceção, o campo desconhecido é ignorado
        String jsonWithUnknownField = "{\"id\":1,\"nome\":\"Produto A\",\"codigo\":\"A123\",\"tipo\":\"ACOMPANHAMENTO\",\"campoDesconhecido\":\"valor\"}";

        ProdutoDTO produto = objectMapper.readValue(jsonWithUnknownField, ProdutoDTO.class);

        assertThat(produto.getId()).isEqualTo(1L);
        assertThat(produto.getNome()).isEqualTo("Produto A");
        assertThat(produto.getCodigo()).isEqualTo("A123");
        assertThat(produto.getTipo()).isEqualTo(TipoProdutoEnum.ACOMPANHAMENTO);
    }
}
