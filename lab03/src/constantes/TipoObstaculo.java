package constantes;

public enum TipoObstaculo {
    PAREDE(5, true),
    PREDIO(60, true),
    PESSOA(2, true),
    ARBUSTO(3, false),
    AVIAO(10, true),
    BURACO(0, true),
    OUTRO(-1, false); //altura variável

    /**
     * A altura representa o nível máximo que o objeto vai obstruir. <p>
     * Ou seja, um objeto com altura 0 não significa que não imperá nada, mas sim que impedirá o nível 0 do ambiente
     */
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
