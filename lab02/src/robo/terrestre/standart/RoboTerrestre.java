package robo.terrestre.standart;

import ambiente.Ambiente;
import robo.standart.Robo;

public class RoboTerrestre extends Robo{
    private int velocidadeMaxima;
    
    /**
     * Construtor da classe RoboTerrestre.
     */
    public RoboTerrestre(int posicaoX, int posicaoY, Ambiente ambiente ,String direcao, String nome, int velocidadeMaxima){
        super(posicaoX, posicaoY, ambiente, direcao, nome);
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

        if(velAtual >= velocidadeMaxima){
            deltaX = (int)((deltaX/velAtual) * velocidadeMaxima);
            deltaY = (int)((deltaY/velAtual) * velocidadeMaxima);
        } 

        super.mover(deltaX, deltaY);
        
    }

    
    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }
}
