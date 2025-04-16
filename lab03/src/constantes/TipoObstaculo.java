package constantes;

public enum TipoObstaculo {
    PAREDE(5, true),
    PREDIO(60, true),
    PESSOA(2, true),
    ARBUSTO(3, false),
    AVIAO(10, true),
    BURACO(0, true),
    OUTRO(-1, false); //altura variável

    private final int alturaPadrao;
    private final boolean bloqueio;

    TipoObstaculo(int altura, boolean bloqueio){
        this.alturaPadrao = altura;
        this.bloqueio = bloqueio;
    }

    public int getAlturaPadrao() {
        return alturaPadrao;
    }

    public boolean isBloqueio() {
        return bloqueio;
    }
}
