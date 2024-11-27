package br.com.fiap.soat07.techchallenge.cozinha.infra.repository.mysql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.DeleteAll;
import com.ninja_squad.dbsetup.operation.Insert;
import com.ninja_squad.dbsetup.operation.Operation;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;
import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class AtendimentoRepositoryTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
    private AtendimentoRepository atendimentoRepository;

    
    @BeforeEach
    public void setUp() {
    	Operation OperationInsert = Insert.into("atendimento")
    			.columns("id", "id_pedido", "codigo", "data_recebido", "situacao", "data_criacao", "data_ultima_modificacao")
    			.values(1, 1, "123", LocalDateTime.of(2024,11,1,9,0,0), SituacaoDoAtendimento.RECEBIDO, LocalDateTime.of(2024,11,1,9,0,0), LocalDateTime.of(2024,11,1,9,0,0))
    			.values(2, 2, "124", LocalDateTime.of(2024,11,1,10,0,0), SituacaoDoAtendimento.RECEBIDO, LocalDateTime.of(2024,11,1,10,0,0), LocalDateTime.of(2024,11,1,10,0,0))
    			.values(3, 4, "125", LocalDateTime.of(2024,11,1,11,0,0), SituacaoDoAtendimento.RECEBIDO, LocalDateTime.of(2024,11,1,11,0,0), LocalDateTime.of(2024,11,1,11,0,0))
    			.values(4, 6, "123", LocalDateTime.of(2024,11,2,9,0,0), SituacaoDoAtendimento.RECEBIDO, LocalDateTime.of(2024,11,2,9,0,0), LocalDateTime.of(2024,11,2,9,0,0))
    			.build();

    	DbSetup dbSetup = new DbSetup(
    			new DataSourceDestination(dataSource), 
    			Operations.sequenceOf(DeleteAll.from("atendimento"), OperationInsert));

    	dbSetup.launch();
    }    

    @Test
    void testSaveWithNullValue() {
        assertThatThrownBy(() -> {
            Atendimento savedAtendimento = atendimentoRepository.save(null);
        }).hasRootCauseInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Obrigatório preencher o atendimento");
    }
    
    @Test
    void testSaveAndFindById() {
        Atendimento atendimento = Atendimento.recebido(null, 9L, "A123", Collections.emptyList());
    	
        // Salvar um atendimento
        Atendimento savedAtendimento = atendimentoRepository.save(atendimento);

        // Verificar se o atendimento foi salvo corretamente
        assertThat(savedAtendimento).isNotNull();
        assertThat(savedAtendimento.getId()).isNotNull();
        assertThat(savedAtendimento.getCodigo()).isEqualTo("A123");

        // Verificar se o atendimento pode ser encontrado pelo id
        Optional<Atendimento> foundAtendimento = atendimentoRepository.findById(savedAtendimento.getId());
        assertThat(foundAtendimento).isPresent();
        assertThat(foundAtendimento.get().getCodigo()).isEqualTo("A123");
    }

    @Test
    void testFindByPedido() {
        // Criar um atendimento relacionado ao pedido nº 123
        Atendimento savedAtendimento = atendimentoRepository.criar(123L, "A123", Collections.emptySet());

        // Verificar se o atendimento pode ser encontrado por ID de pedido
        Optional<Atendimento> atendimentos = atendimentoRepository.findByPedido(123L);
        assertThat(atendimentos).isNotEmpty();
        assertThat(atendimentos.get().getCodigo()).isEqualTo("A123");
    }

    @Test
    void testFindByPedidoNull() {
        Optional<Atendimento> atendimentos = atendimentoRepository.findByPedido(null);
        assertThat(atendimentos).isEmpty();
    }

    @Test
    void testSaveAndDatabaseValidation() {
        // Salvar um atendimento
    	Atendimento atendimento = Atendimento.recebido(null, 123L, "123X", Collections.emptyList());
    	Atendimento savedAtendimento = atendimentoRepository.save(atendimento);
        assertThat(savedAtendimento.getCodigo()).isEqualTo("123X");

        // Validar que o atendimento foi inserido na tabela AtendimentoModel
        Optional<Atendimento> op = atendimentoRepository.findById(savedAtendimento.getId());
        assertThat(op).isPresent();
        assertThat(op.get().getCodigo()).isEqualTo("123X");
    }

    @Test
    void testUpdateAndDatabaseValidation() {
        // Salvar um atendimento
    	Atendimento atendimento = Atendimento.recebido(null, 123L, "123X", Collections.emptyList());
    	Atendimento savedAtendimento = atendimentoRepository.save(atendimento);
        assertThat(savedAtendimento.getCodigo()).isEqualTo("123X");

        // Validar que o atendimento foi inserido na tabela AtendimentoModel
        Optional<Atendimento> op = atendimentoRepository.findById(savedAtendimento.getId());
        assertThat(op).isPresent();
        assertThat(op.get().getCodigo()).isEqualTo("123X");
    }

    @Test
    void testFindBySituacao() {
        // Salvar um atendimento
//        Atendimento savedAtendimento = atendimentoRepository.save(atendimento);

        // Buscar atendimentos com situação RECEBIDO
        Collection<Atendimento> atendimentos = atendimentoRepository.find(SituacaoDoAtendimento.RECEBIDO, 1, 10);
        assertThat(atendimentos).hasSize(4);
//        assertThat(atendimentos.iterator().next().getCodigo()).isEqualTo("A123");
    }

    
}
