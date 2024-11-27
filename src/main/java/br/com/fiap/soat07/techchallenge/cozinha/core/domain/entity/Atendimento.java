package br.com.fiap.soat07.techchallenge.cozinha.core.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration.SituacaoDoAtendimento;
import br.com.fiap.soat07.techchallenge.cozinha.infra.rest.dto.ProdutoDTO;

public class Atendimento {
    private Long id;
    private Long idPedido;
    private String codigo;
    private SituacaoDoAtendimento situacao;
    private LocalDateTime recebido;
    private LocalDateTime iniciado;
    private LocalDateTime preparado;
    private LocalDateTime concluido;

    //
    protected Atendimento() {
    	this.situacao = SituacaoDoAtendimento.RECEBIDO;
        this.recebido = LocalDateTime.now();
    }
    public Atendimento(Long id, Long idPedido, String codigo, SituacaoDoAtendimento situacao, LocalDateTime recebido, LocalDateTime iniciado, LocalDateTime preparado, LocalDateTime concluido, Collection<ProdutoDTO> produtos) {
        this();
        this.id = id;
        this.idPedido = idPedido;
        this.codigo = codigo;
        this.situacao = situacao;
        this.recebido = recebido;
        this.iniciado = iniciado;
        this.preparado = preparado;
        this.concluido = concluido;
    }
    public Atendimento(Long id, Long idPedido, String codigo, LocalDateTime iniciado, LocalDateTime preparado, LocalDateTime concluido, Collection<ProdutoDTO> produtos) {
        this();
        this.id = id;
        this.idPedido = idPedido;
        this.codigo = codigo;
        this.iniciado = iniciado;
        this.preparado = preparado;
        this.concluido = concluido;
    }

    public static Atendimento recebido(Long id, Long idPedido, String codigo, Collection<ProdutoDTO> produtos) {
        return new Atendimento(id, idPedido, codigo, SituacaoDoAtendimento.RECEBIDO, LocalDateTime.now(), null, null, null, produtos);
    }

    public Long getId() {
        return id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public String getCodigo() {
        return codigo;
    }

    public SituacaoDoAtendimento getSituacao() {
        return situacao;
    }
    
    public void iniciado() {
    	switch (getSituacao()) {
		case RECEBIDO -> {
	    	this.situacao = SituacaoDoAtendimento.INICIADO;
	    	this.iniciado = LocalDateTime.now();
		}
		case INICIADO -> {}
		case PREPARADO -> {}
		case CANCELADO -> {}
		case ENTREGUE -> {}
		}
    }
    
    public void preparado() {
    	switch (getSituacao()) {
		case RECEBIDO -> {
	    	this.situacao = SituacaoDoAtendimento.PREPARADO;
	    	this.preparado = LocalDateTime.now();
		}
		case INICIADO -> {
	    	this.situacao = SituacaoDoAtendimento.PREPARADO;
	    	this.preparado = LocalDateTime.now();
		}
		case PREPARADO -> {
	    	this.situacao = SituacaoDoAtendimento.PREPARADO;
	    	this.preparado = LocalDateTime.now();
		}
		case CANCELADO -> {}
		case ENTREGUE -> {}
		}
    }
    
    public void entregue() {
    	switch (getSituacao()) {
		case RECEBIDO -> {
			this.situacao = SituacaoDoAtendimento.ENTREGUE;
	    	this.concluido = LocalDateTime.now();
		}
		case INICIADO -> {
			this.situacao = SituacaoDoAtendimento.ENTREGUE;
	    	this.concluido = LocalDateTime.now();
		}
		case PREPARADO -> {
	    	this.situacao = SituacaoDoAtendimento.ENTREGUE;
	    	this.concluido = LocalDateTime.now();
		}
		case CANCELADO -> {}
		case ENTREGUE -> {}
    	}
    }

    public void cancelado() {
    	switch (getSituacao()) {
		case RECEBIDO -> {
			this.situacao = SituacaoDoAtendimento.CANCELADO;
	    	this.concluido = LocalDateTime.now();
		}
		case INICIADO -> {
			this.situacao = SituacaoDoAtendimento.CANCELADO;
	    	this.concluido = LocalDateTime.now();
		}
		case PREPARADO -> {
	    	this.situacao = SituacaoDoAtendimento.CANCELADO;
	    	this.concluido = LocalDateTime.now();
		}		
		case CANCELADO -> {}
		case ENTREGUE -> {}
    	}
    }

    public LocalDateTime getRecebido() {
		return recebido;
	}
    
    public LocalDate getData() {
    	return getRecebido().toLocalDate();
    }

    public LocalDateTime getIniciado() {
        return iniciado;
    }

    public LocalDateTime getPreparado() {
        return preparado;
    }

    public LocalDateTime getConcluido() {
        return concluido;
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(getCodigo(), getData());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atendimento other = (Atendimento) obj;
		return Objects.equals(getCodigo(), other.getCodigo()) && Objects.equals(getData(), other.getData());
	}
	
	@Override
    public String toString() {
        return "Atendimento{" +
                "id=" + id +
                ", pedido='" + getIdPedido() + '\'' +
                ", codigo='" + getCodigo() + '\'' +
                ", situacao='" + getSituacao() + '\'' +
                '}';
    }
}
