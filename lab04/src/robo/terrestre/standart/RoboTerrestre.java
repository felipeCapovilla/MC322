package robo.terrestre.standart;

import constantes.Bussola;
import robo.standart.Robo;

public class RoboTerrestre extends Robo{
    private int velocidadeMaxima;
    
    /**
     * Construtor da classe RoboTerrestre.
     */
    public RoboTerrestre(String nome,int posicaoX, int posicaoY ,Bussola direcao, int velocidadeMaxima){
        super(nome,posicaoX, posicaoY, 0,direcao);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual, sendo a variação máxima igual à velocidadeMaxima.
     * @param deltaX
     * @param deltaY
     */
    @Override
    public void mover(int deltaX, int deltaY){
        double velAtual = Math.sqrt((deltaX*deltaX + deltaY*deltaY));

        if(velAtual >= velocidadeMaxima){ //altera o deslocamento para o máximo possível que é menor do que a velocidade máxima
            deltaX = (int)((deltaX/velAtual) * velocidadeMaxima);
            deltaY = (int)((deltaY/velAtual) * velocidadeMaxima);
        } 

        super.mover(deltaX, deltaY);
        
    }

    //GETs e SETs

    /**
     * Retorna o valor da variável velocidadeMaxima
     */
    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    /**
     * define o valor da variável velocidadeMaxima
     */
    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = Math.max(0, velocidadeMaxima);
    }

    @Override
    public String getDescricao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescricao'");
    }

    @Override
    public void executarTarefa() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executarTarefa'");
    }
}
