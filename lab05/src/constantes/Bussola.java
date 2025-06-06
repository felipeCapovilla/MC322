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

    /**
     * Retorna o indice da direção
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Transforma string em direção da bussola
     */
    static public Bussola strToBussola(String dir){
        switch (dir.toLowerCase()) {
            case "oeste":
                return Bussola.OESTE; 

            case "leste":
                return Bussola.LESTE; 

            case "norte":
                return Bussola.NORTE; 
            
            case "sul":
                return Bussola.SUL; 

            default:
                return null;
        }
    }

}
