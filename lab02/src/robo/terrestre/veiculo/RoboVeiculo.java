package robo.terrestre.veiculo;

import robo.terrestre.standart.RoboTerrestre;

public class RoboVeiculo extends RoboTerrestre{
    private final String[] direcoesList = {"NORTE", "LESTE", "SUL", "OESTE"};
    private int passageiros;
    private final int passageiros_maximo;
    private int velocidade;

    
    public RoboVeiculo(String nome,int posicaoX, int posicaoY,String direcao, int velocidadeMaxima, int passageiros_maximo){
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
     * 
     * @param direita direita(true) ou esquerda(false)
     */
    public void virar(boolean direita){
        int index;
        switch (getDirecao()) {
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
        setDirecao(direcoesList[((index+lado)%4 + 4) % 4]);
    }

    /**
     * Overload <p>
     * Adiciona a velocidade no valor da coordenada atual, apenas no sentido da direção.
     */
    public void mover(boolean frente){
        int marcha = (frente)? 1:-1;

        switch (getDirecao()) {
            case "NORTE": 
                mover(0, velocidade * marcha);
                break;

            case "SUL": 
                mover(0, -(velocidade * marcha));
                break;

            case "LESTE": 
                mover(velocidade * marcha,0);
                break;

            case "OESTE": 
                mover(-(velocidade * marcha), 0);
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

    public int getVelocidade() {
        return velocidade;
    }

    public int getPassageiros() {
        return passageiros;
    }

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
