package constantes;

public enum Bussola {
    NORTE(0),
    LESTE(1),
    SUL(2),
    OESTE(3);

    private final int indice;

    Bussola(int indice){
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }

}
