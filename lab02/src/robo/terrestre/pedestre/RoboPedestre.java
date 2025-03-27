package robo.terrestre.pedestre;

import ambiente.Ambiente;
import robo.terrestre.standart.*;

public class RoboPedestre extends RoboTerrestre{
    
    private int peso;


    public RoboPedestre(int posicaoX, int posicaoY, Ambiente ambiente,String direcao, String nome, int velocidadeMaxima){
        super (posicaoX, posicaoY, ambiente, direcao, nome, velocidadeMaxima);
        peso = 0;

    }

    /**
     * Overload <p>
     * Move o robo pedestre em dois modos:
     * <p>
     * correr-> até velocidade máxima <p>
     * caminhar-> até 0.6 * velocidade máxima
     * @param correr correr(true) ou caminhar(false)
     */
    public void mover(boolean correr, int deltaX, int deltaY){
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
        if(velAtual >= (getVelocidadeMaxima()*fator_movimento)){
            deltaX = (int)((deltaX/velAtual) * (getVelocidadeMaxima()*fator_movimento));
            deltaY = (int)((deltaY/velAtual) * (getVelocidadeMaxima()*fator_movimento));

        } 
        
        super.mover(deltaX, deltaY);
    }

    /**
     * Fator do peso faz andar mais devagar <p>
     * peso = 1 -> 1 * velocidade <p>
     * peso = 10 -> 0,5 * velocidade <p>
     * ...
     */
    public void setPeso(int peso) {
        this.peso = Math.max(peso, 0);
    }

    public int getPeso() {
        return peso;
    }

}
