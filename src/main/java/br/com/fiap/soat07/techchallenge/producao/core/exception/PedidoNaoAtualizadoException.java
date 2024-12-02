package br.com.fiap.soat07.techchallenge.producao.core.exception;

public class PedidoNaoAtualizadoException extends BusinessException {
    private static final long serialVersionUID = 1369928619481103297L;

    private static final String MESSAGE = "Pedido %d não foi atualizado";
    private final long id;

    public PedidoNaoAtualizadoException(long id) {
        super(String.format(MESSAGE, id));
        this.id = id;
    }

    public long getPedidoId() {
        return id;
    }
}