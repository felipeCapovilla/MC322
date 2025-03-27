package robo.terrestre.veiculo;

import robo.terrestre.standart.RoboTerrestre;

public class RoboVeiculo extends RoboTerrestre{
    private final String[] direcoesList = {"NORTE", "LESTE", "SUL", "OESTE"};
    private int passageiros;
    private final int passageiros_maximo;
    private int velocidade_atual;

    
    public RoboVeiculo(String nome, int posicaoX, int posicaoY, String direcao, int velocidadeMaxima, int passageiros_maximo){
        super (nome, posicaoX, posicaoY, direcao, velocidadeMaxima);
        this.passageiros_maximo = passageiros_maximo;
        passageiros = 0;
        velocidade_atual = 0;

    }

    /**
     * Muda a velocidade do veiculo
     * @param nova_velocidade
     */
    public void mudarVelocidade(int nova_velocidade){
        velocidade_atual = Math.min(nova_velocidade, velocidadeMaxima);
    }

    /**
     * 
     * @param direita direita(true) ou esquerda(false)
     */
    public void virar(boolean direita){
        int index;
        switch (get_direcao()) {
            case "NORTE":
                index = 0;
                break;

            case "LESTE":
                index = 1;
                break;

            case "SUL":
                index = 2;
                break;

            case "OESTE":
                index = 3;
                break;

            default:
                throw new AssertionError();
        }

        //direita(1) e esquerda(-1)
        int lado = (direita)? 1: -1;

        //movimento ciclico do index
        set_direcao(direcoesList[((index+lado)%4 + 4) % 4]);
    }

    /**
     * Adiciona a velocidade no valor da coordenada atual, apenas no sentido da direção.
     */
    public void moverSentido(boolean frente){
        int marcha = (frente)? 1:-1;

        switch (get_direcao()) {
            case "NORTE": 
                mover(0, velocidade_atual * marcha);
                break;

            case "SUL": 
                mover(0, -(velocidade_atual * marcha));
                break;

            case "LESTE": 
                mover(velocidade_atual * marcha,0);
                break;

            case "OESTE": 
                mover(-(velocidade_atual * marcha), 0);
                break;
                
            default: 
                throw new AssertionError("Direção inválida");
        }
    }

    /**
     * Diminui o n umero de passageiros
     */
    public void passageirosSair(int num_passageiros){
        passageiros = Math.max(0, passageiros-num_passageiros);
    }

    /**
     * Aumenta o numero de passageiros
     */
    public void passageirosEntrar(int num_passageiros){
        passageiros = Math.min(passageiros_maximo, passageiros+num_passageiros);
    }

    public int getVelocidade_atual() {
        return velocidade_atual;
    }

    public int getPassageiros() {
        return passageiros;
    }

}
