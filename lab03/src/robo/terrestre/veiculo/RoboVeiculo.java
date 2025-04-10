package robo.terrestre.veiculo;

import constantes.Bussula;
import robo.terrestre.standart.RoboTerrestre;

public class RoboVeiculo extends RoboTerrestre{
    private int passageiros;
    private final int passageiros_maximo;
    private int velocidade;

    
    public RoboVeiculo(String nome,int posicaoX, int posicaoY,Bussula direcao, int velocidadeMaxima, int passageiros_maximo){
        super (nome,posicaoX, posicaoY,direcao, velocidadeMaxima);
        this.passageiros_maximo = passageiros_maximo;
        passageiros = 0;
        velocidade = 0;

    }

    /**
     * Muda a velocidade do veiculo
     * @param velocidade
     */
    public void mudarVelocidade(int velocidade){
        if(velocidade < 0){
            this.velocidade = 0;
        } else if(velocidade > getVelocidadeMaxima()){
            this.velocidade = getVelocidadeMaxima();
        } else {
            this.velocidade = velocidade;    
        }
    }

    /**
     * Vira a direção do robo para direita ou esquerda em relação à direção atual
     * @param direita direita(true) ou esquerda(false)
     */
    public void virar(boolean direita){
        int index = getDirecao().getIndice();

        //direita(1) e esquerda(-1)
        int lado = (direita)? 1: -1;

        //movimento ciclico do index
        setDirecao(Bussula.values()[((index+lado)%4 + 4) % 4]);
    }

    /**
     * Overload <p>
     * Adiciona a velocidade no valor da coordenada atual, apenas no sentido da direção.
     */
    public void mover(boolean frente){
        int marcha = (frente)? 1:-1;

        switch (getDirecao()) {
            case Bussula.NORTE: 
                mover(0, velocidade * marcha);
                break;

            case Bussula.SUL: 
                mover(0, -(velocidade * marcha));
                break;

            case Bussula.LESTE: 
                mover(velocidade * marcha,0);
                break;

            case Bussula.OESTE: 
                mover(-(velocidade * marcha), 0);
                break;
                
            default: 
                throw new AssertionError("Direção inválida");
        }
    }

    /**
     * Diminui o numero de passageiros
     * @param num_passageiros quantidade de passageiros saindo
     */
    public void passageirosSair(int num_passageiros){
        passageiros = Math.max(0, passageiros-num_passageiros);//não sai mais passageiros que estão no veículo
    }

    /**
     * Aumenta o numero de passageiros
     * @param num_passageiros quantidade de passageiros entrando
     */
    public void passageirosEntrar(int num_passageiros){
        passageiros = Math.min(passageiros_maximo, passageiros+num_passageiros); //não entra mais passageiros que a capacidade máxima
    }

    //GETs e SETs

    /**
     * Retorna o valor da variável valocidade
     */
    public int getVelocidade() {
        return velocidade;
    }

    /**
     * Retorna o valor da variável passageiros
     */
    public int getPassageiros() {
        return passageiros;
    }

    /**
     * define o valor da variável passageiros
     */
    public void setPassageiros(int passageiros) {
        if(passageiros < 0){
            this.passageiros = 0;
        } else if(passageiros > passageiros_maximo){
            this.passageiros = passageiros_maximo;
        } else {
            this.passageiros = passageiros;    
        }
        
    }

}
