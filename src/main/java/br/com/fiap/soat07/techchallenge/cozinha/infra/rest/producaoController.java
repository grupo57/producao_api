package br.com.fiap.soat07.techchallenge.producao.infra.rest;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat07.techchallenge.producao.core.domain.entity.Atendimento;
import br.com.fiap.soat07.techchallenge.producao.core.exception.PedidoJaAtendidoException;
import br.com.fiap.soat07.techchallenge.producao.infra.rest.dto.PedidoDTO;
import br.com.fiap.soat07.techchallenge.producao.infra.service.producaoService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Tag(name = "producao", description = "producao")
@RestController
@RequestMapping
public class producaoController {

    private final producaoService producaoService;

    public producaoController(producaoService producaoService) {
        this.producaoService = producaoService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de atedimento em ABERTO", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/atendimentos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> listarAtendimentosAbertos() {
        try {
            Collection<Atendimento> atendimentos = producaoService.getSearchAtendimentoUseCase().find();
            return ResponseEntity.status(200).body(atendimentos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria um atendimento com base num pedido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "400", description = "Pedido já possui atendimento"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(value = "/atendimentos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> createAtendimento(@RequestBody final PedidoDTO pedidoDTO) {
        if (pedidoDTO == null)
            return ResponseEntity.status(400).body("Obrigatório informar o pedido");

        Atendimento atendimento = null;
        try {
            atendimento = producaoService.getCreateAtendimentoUseCase().execute(pedidoDTO);
        } catch (PedidoJaAtendidoException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

        return ResponseEntity.created(URI.create("/producao/atendimentos/" + atendimento.getIdPedido())).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recupera o atendimento associado ao número do pedido informado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/atendimentos/{id}")
    public ResponseEntity<?> get(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().body("Obrigatório informar o número do atendimento");

        producaoService.getSearchAtendimentoUseCase()
                .find().forEach(System.err::println);

        try {
            Optional<Atendimento> atendimentoOp = producaoService.getSearchAtendimentoUseCase()
                    .findByPedido(id);
            if (atendimentoOp.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(atendimentoOp.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Altera a situação de um atendimento para INICIADO", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/atendimentos/{id}/iniciado")
    @Transactional
    public ResponseEntity<?> iniciado(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().body("Obrigatório informar o número do atendimento");

        producaoService.getSearchAtendimentoUseCase().find().stream().forEach(System.err::println);
        try {
            Optional<Atendimento> atendimentoOp = producaoService.getSearchAtendimentoUseCase()
                    .findByPedido(id);
            if (atendimentoOp.isEmpty())
                return ResponseEntity.notFound().build();
            Atendimento atendimento = atendimentoOp.get();
            atendimento = producaoService.getUpdateAtendimentoSituacaoIniciado().execute(atendimento);
            return ResponseEntity.ok(atendimento);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Altera a situação de um atendimento para PREPARADO", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/atendimentos/{id}/preparado")
    @Transactional
    public ResponseEntity<?> preparado(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().body("Obrigatório informar o número do atendimento");

        producaoService.getSearchAtendimentoUseCase().find().stream().forEach(System.err::println);
        try {
            Optional<Atendimento> atendimentoOp = producaoService.getSearchAtendimentoUseCase()
                    .findByPedido(id);
            if (atendimentoOp.isEmpty())
                return ResponseEntity.notFound().build();
            Atendimento atendimento = atendimentoOp.get();
            atendimento = producaoService.getUpdateAtendimentoSituacaoIniciado().execute(atendimento);
            return ResponseEntity.ok(atendimento);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Altera a situação de um atendimento para ENTREGUE", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Atendimento.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/atendimentos/{id}/concluido")
    @Transactional
    public ResponseEntity<?> entregue(@PathVariable final Long id) {
        if (id == null)
            return ResponseEntity.badRequest().body("Obrigatório informar o número do atendimento");

        try {
            Optional<Atendimento> atendimentoOp = producaoService.getSearchAtendimentoUseCase()
                    .findByPedido(id);
            if (atendimentoOp.isEmpty())
                return ResponseEntity.notFound().build();
            Atendimento atendimento = atendimentoOp.get();
            atendimento = producaoService.getUpdateAtendimentoSituacaoConcluido().execute(atendimento);
            return ResponseEntity.ok(atendimento);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
