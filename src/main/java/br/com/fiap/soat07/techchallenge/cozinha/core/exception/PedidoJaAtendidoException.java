package br.com.fiap.soat07.techchallenge.cozinha.core.exception;

public class PedidoJaAtendidoException extends BusinessException {
    private static final long serialVersionUID = 1369928619481103297L;

    private static final String MESSAGE = "Pedido %d já foi atendido";
    private final long id;

    public PedidoJaAtendidoException(long id) {
        super(String.format(MESSAGE, id));
        this.id = id;
    }

    public long getPedidoId() {
        return id;
    }
}