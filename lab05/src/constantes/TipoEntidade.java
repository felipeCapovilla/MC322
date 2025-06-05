package constantes;

public enum TipoEntidade {
    VAZIO('.'),
    ROBO('@'),
    OBSTACULO('#'),
    DESCONHECIDO('?');

    private final char representacao;

    private TipoEntidade(char representacao) {
        this.representacao = representacao;
    }

    public char getRepresentacao() {
        return this.representacao;
    }
}
