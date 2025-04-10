package robo.aereo.turista;


import constantes.Bussula;
import robo.aereo.standart.*;
public class RoboVoadorTurista extends RoboAereo{

    private int numero_passageiros;
    private final int capacidade_maxima;
    private String cidade_turistica;
    private boolean em_passeio;

    public RoboVoadorTurista(String nome,int posicaoX, int posicaoY, Bussula direcao, int altitude,int altitude_max,int capacidade_maxima){
        super(nome,posicaoX,posicaoY,direcao,altitude,altitude_max);

        this.capacidade_maxima = capacidade_maxima;
        this.numero_passageiros = 0;
        this.cidade_turistica = "";
        this.em_passeio = false;

    }


    /**
     * Inicia o turismo do robo.
     * @param numero_passageiros Indica a quantidade de passageiros atual presentes.
     * @param cidade_turistica Indica o destino do passeio turistico do Robo.
     */
    public void inciar_passeio(int numero_passageiros, String cidade_turistica){
        if(numero_passageiros > this.capacidade_maxima){ //Verifica se a quantidade de passageiros e permitida.
            throw new IllegalArgumentException("A capacidade maxima de "+this.capacidade_maxima+"passageiros, foi excedida. \n Circulação não permitida.");
        }
        this.numero_passageiros = numero_passageiros;
        this.cidade_turistica = cidade_turistica;
        this.em_passeio = true;
    }   



    //GETs e SETs

    /**
     * Retorna se o robo esta em servico.
     */
    public boolean get_status(){
        return this.em_passeio;
    }


    /**
     * Retorna o destino turistico do robo.
     */
    public String get_destino(){
        return this.cidade_turistica;
    }

    /**
     * Retorna o numero de passageiros no robo.
     */
    public int get_numero_passageiros() {
        return numero_passageiros;
    }

    /**
     * define o valor da variável numero_passageiros
     */
    public void set_numero_passageiros(int numero_passageiros) {
        if(numero_passageiros < 0){
            this.numero_passageiros = 0;
        } else if (numero_passageiros > capacidade_maxima){
            this.numero_passageiros = capacidade_maxima;
        } else {
            this.numero_passageiros = numero_passageiros;
        }
    }
}