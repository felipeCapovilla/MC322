package ambiente;

import constantes.TipoEntidade;
import constantes.TipoObstaculo;
import exceptions.*;
import interfaces.Entidade;

public class Obstaculo implements Entidade{
    final private int posX1, posY1;
    final private int posX2, posY2;
    /**
     * A altura representa o nível máximo que o objeto vai obstruir. <p>
     * Ou seja, um objeto com altura 0 não significa que não imperá nada, mas sim que impedirá o nível 0 do ambiente
     */
    final private int altura;
    final private TipoObstaculo tipoObstaculo;

    private final TipoEntidade tipoEntidade = TipoEntidade.OBSTACULO;

    /**
     * Obstaculo puntiforme com altura padrão
     * @param posX
     * @param posY
     * @param tipoObstaculo
     */
    public Obstaculo (int posX, int posY, TipoObstaculo tipoObstaculo){
        posX1 = posX;
        posX2 = posX;
        posY1 = posY;
        posY2 = posY;

        this.tipoObstaculo = tipoObstaculo;
        altura = tipoObstaculo.getAlturaPadrao();
    }

    /**
     * Obstaculo extenso com altura padrão
     * @param posX1
     * @param posY1
     * @param posX2
     * @param posY2
     * @param tipoObstaculo
     */
    public Obstaculo (int posX1, int posY1, int posX2, int posY2, TipoObstaculo tipoObstaculo){
        this.posX1 = Math.min(posX1, posX2);
        this.posX2 = Math.max(posX1, posX2);
        this.posY1 = Math.min(posY1, posY2);
        this.posY2 = Math.max(posY1, posY2);

        this.tipoObstaculo = tipoObstaculo;
        altura = tipoObstaculo.getAlturaPadrao();
    }

    /**
     * Obstaculo extenso com altura personalizada
     * @param posX1
     * @param posY1
     * @param posX2
     * @param posY2
     * @param altura
     * @param tipoObstaculo
     */
    public Obstaculo (int posX1, int posY1, int posX2, int posY2, int altura, TipoObstaculo tipoObstaculo) throws ValueOutOfBoundsException{
        this.posX1 = Math.min(posX1, posX2);
        this.posX2 = Math.max(posX1, posX2);
        this.posY1 = Math.min(posY1, posY2);
        this.posY2 = Math.max(posY1, posY2);

        this.tipoObstaculo = tipoObstaculo;

        if(altura < 0){
            throw new ValueOutOfBoundsException("altura < 0");
        }
        this.altura = altura;
    }

    /**
     * Retorna se o obstaculo é atravessável
     */
    public boolean Passavel(){
        return !tipoObstaculo.isBloqueio();
    }

    /**
     * Retorna se um ponto está dentro do obstáculo
     */
    public boolean estaDentro(int x, int y, int z){
        return (posX1 <= x && posX2 >= x) 
            && (posY1 <= y && posY2 >= y)
            && (z < altura && z >= 0);
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
     * Retorna o tipo do obstáculo
     */
    @Override
    public String toString(){
        return String.format(tipoObstaculo.toString());
    }

    /**
     * retorna o menor ponto X
     */
    @Override
    public int getX() {
        return posX1;
    }

    /**
     * retorna o menor ponto Y
     */
    @Override
    public int getY() {
        return posY1;
    }

    /**
     * Retorna o valor da variável altura
     */
    @Override
    public int getZ() {
        return  altura;
    }

    @Override
    public TipoEntidade getTipo() {
        return tipoEntidade;
    }

    @Override
    public String getDescricao() {
        return "Obstáculo é um objeto imóvel que pode impedir a passagem de outras entidades";
    }

    @Override
    public char getRepresentacao() {
        return tipoEntidade.getRepresentacao();
    }
}
