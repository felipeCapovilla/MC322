package robo.terrestre.pedestre;

import robo.terrestre.standart.*;

public class RoboPedestre extends RoboTerrestre{
    
    private int peso;


    public RoboPedestre(String nome, int posicaoX, int posicaoY, String direcao, int velocidadeMaxima){
        super (nome, posicaoX, posicaoY, direcao, velocidadeMaxima);
        peso = 0;

    }

    public void moverAndar(boolean correr, int deltaX, int deltaY){
        //Decide para correr ou caminhar
        double fator_movimento = (correr)? 1 : 0.6;

        //fator do peso faz andar mais devagar
        //peso = 1 -> 1 * velocidade
        //peso = 10 -> 0,5 * velocidade
        deltaX = (int)(deltaX / ((peso/10)+1));
        deltaY = (int)(deltaY / ((peso/10)+1));

        //Verificar velocidade atual
        double velAtual = Math.sqrt((deltaX*deltaX + deltaY*deltaY));

        //Não mover mais rapido que a velocidade maxima
        if(velAtual <= (velocidadeMaxima*fator_movimento)){
            super.mover(deltaX, deltaY);

        } else {
            int newdeltaX = (int)((deltaX/velAtual) * (velocidadeMaxima*fator_movimento));
            int newdeltaY = (int)((deltaY/velAtual) * (velocidadeMaxima*fator_movimento));

            super.mover(newdeltaX, newdeltaY);
        }
    }

    public void setPeso(int peso) {
        this.peso = Math.max(peso, 0);
    }

    





}
