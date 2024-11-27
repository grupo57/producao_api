package br.com.fiap.soat07.techchallenge.cozinha.core.exception;

public class AtendimentoNotFoundException extends ResourceNotFoundException {
    private static final long serialVersionUID = 1369928619481103297L;
	
    private static final String MESSAGE = "Não foi encontrado um atendimento com o Id:%d";
    public AtendimentoNotFoundException(long id ) {
        super(String.format(MESSAGE, id));
    }

}