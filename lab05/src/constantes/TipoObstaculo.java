package constantes;

public enum TipoObstaculo {
    PAREDE(5, true),
    PREDIO(60, true),
    PESSOA(2, true),
    ARBUSTO(3, false),
    AVIAO(10, true),
    BURACO(1, true),
    CAIXA(1, true),
    OUTRO(-1, false); //altura variável

    /**
     * A altura representa o nível máximo que o objeto vai obstruir. <p>
     */
    private final int alturaPadrao;
    private final boolean bloqueio;

    TipoObstaculo(int altura, boolean bloqueio){
        this.alturaPadrao = altura;
        this.bloqueio = bloqueio;
    }

    /**
     * Retorna o valor da variável alturaPadrao
     */
    public int getAlturaPadrao() {
        return alturaPadrao;
    }

    /**
     * Retorna um booleano de se o obstaculo bloqueia a passagem
     */
    public boolean isBloqueio() {
        return bloqueio;
    }

    static public TipoObstaculo strToTipoObstaculo(String kind){
        TipoObstaculo[] listaTipoObstaculo = TipoObstaculo.values();

        for(TipoObstaculo tipoObstaculo : listaTipoObstaculo){
            if(tipoObstaculo.toString().equals(kind.toUpperCase())){
                return tipoObstaculo;
            }
        }

        return TipoObstaculo.OUTRO;
    }
}
