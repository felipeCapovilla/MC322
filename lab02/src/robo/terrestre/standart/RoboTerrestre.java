package robo.terrestre.standart;

import robo.standart.Robo;

public class RoboTerrestre extends Robo{
    protected int velocidadeMaxima;
    
    /**
     * Construtor da classe RoboTerrestre.
     */
    public RoboTerrestre(String nome, int posicaoX, int posicaoY, String direcao, int velocidadeMaxima){
        super(nome, posicaoX, posicaoY, direcao);
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

        if(velAtual <= velocidadeMaxima){
            super.mover(deltaX, deltaY);
        } else {

            int newdeltaX = (int)((deltaX/velAtual) * velocidadeMaxima);
            int newdeltaY = (int)((deltaY/velAtual) * velocidadeMaxima);

            super.mover(newdeltaX, newdeltaY);
        }
    }
}
