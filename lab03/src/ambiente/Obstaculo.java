package ambiente;

import constantes.TipoObstaculo;

public class Obstaculo {
    int posX1, posY1;
    int posX2, posY2;
    int altura;
    TipoObstaculo tipoObstaculo;

    //Obstaculo puntiforme com altura padrão
    public Obstaculo (int posX, int posY, TipoObstaculo tipoObstaculo){
        posX1 = posX;
        posX2 = posX;
        posY1 = posY;
        posY2 = posY;

        this.tipoObstaculo = tipoObstaculo;
        altura = tipoObstaculo.getAlturaPadrao();
    }

    //Obstaculo extenso com altura padrão
    public Obstaculo (int posX1, int posY1, int posX2, int posY2, TipoObstaculo tipoObstaculo){
        this.posX1 = Math.min(posX1, posX2);
        this.posX2 = Math.max(posX2, posX2);
        this.posY1 = Math.min(posY1, posY2);
        this.posY2 = Math.max(posY2, posY2);

        this.tipoObstaculo = tipoObstaculo;
        altura = tipoObstaculo.getAlturaPadrao();
    }

    //Obstaculo extenso com altura personalizada
    public Obstaculo (int posX1, int posY1, int posX2, int posY2, int altura, TipoObstaculo tipoObstaculo){
        this.posX1 = Math.min(posX1, posX2);
        this.posX2 = Math.max(posX2, posX2);
        this.posY1 = Math.min(posY1, posY2);
        this.posY2 = Math.max(posY2, posY2);

        this.tipoObstaculo = tipoObstaculo;
        this.altura = altura;
    }

    /**
     * Retorna se o obstaculo é atravessável
     */
    public boolean Passavel(){
        return tipoObstaculo.isBloqueio();
    }

    /**
     * Retorna o ponto mais próximo da origem
     */
    public int[] getPontoMenor(){
        int[] ponto = {posX1,posY1};
        return ponto;
    }

    /**
     * Retorna o ponto mais longe da origem
     */
    public int[] getPontoMaior(){
        int[] ponto = {posX2,posY2};
        return ponto;
    }

    /**
     * Retorna o valor da variável altura
     */
    public int getAltura() {
        return altura;
    }

    




}
