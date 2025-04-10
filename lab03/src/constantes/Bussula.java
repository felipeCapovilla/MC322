package constantes;

public enum Bussula {
    NORTE(0),
    LESTE(1),
    SUL(2),
    OESTE(3);

    private final int indice;

    Bussula(int indice){
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }

}
