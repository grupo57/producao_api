package br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration;

public enum PedidoStatusEnum {
	
	PREPARO(2),
	PRONTO(3),
	FINALIZADO(4),
	CANCELADO(5);
	
	private int step;
	
	private PedidoStatusEnum(int step){
		this.step = step;
	}

	public int getStep() {
		return step;
	}


	public boolean isCancelado() {
		return this == PedidoStatusEnum.CANCELADO;
	}

	public boolean isFinalizado() {
		return this == PedidoStatusEnum.FINALIZADO;
	}

}