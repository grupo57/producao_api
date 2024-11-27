package br.com.fiap.soat07.techchallenge.cozinha.core.domain.enumeration;

import java.util.Collection;
import java.util.EnumSet;

public enum SituacaoDoAtendimento {
    RECEBIDO,
    INICIADO,
    PREPARADO,
    ENTREGUE,
    CANCELADO;

    /**
     * Lista de situações associadas ao Atendimento quem não está concluído
     * @return Collection
     */
    public static Collection<SituacaoDoAtendimento> emAberto() {
        return EnumSet.of(RECEBIDO, INICIADO, PREPARADO);
    }

    /**
     * O atendimento é considerado encerrado quando não há mais o que fazer.
     * @return boolean
     */
    public boolean isEncerrado() {
        return this.equals(SituacaoDoAtendimento.ENTREGUE) || this.equals(SituacaoDoAtendimento.CANCELADO);
    }

}
