package br.com.fiap.soat07.techchallenge.producao.core.domain.enumeration;

public enum PedidoStatusEnum {

	PREPARO,
	PRONTO,
	FINALIZADO,
	CANCELADO;

	public static PedidoStatusEnum get(SituacaoDoAtendimento situacao) {
		return switch (situacao) {
			case RECEBIDO -> PREPARO;
			case INICIADO -> PREPARO;
			case PREPARADO -> PRONTO;
			case ENTREGUE -> FINALIZADO;
			case CANCELADO -> CANCELADO;
		};
	}

}