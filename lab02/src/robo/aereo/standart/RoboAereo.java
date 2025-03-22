package robo.aereo.standart;

import robo.standart.*;

// TODO: Sobreescrever funcao toString de ambas classes herdadas de RoboAereo && adicionar metodo de verificar obstaculos em torno do robo.


public class RoboAereo extends Robo {
    
    private int altitude;
    private final int altitude_max;

    public RoboAereo(String nome,int posicaoX, int posicaoY, String direcao,int altitude,int altitude_max){
        
        super(nome, posicaoX, posicaoY, direcao); //Chama o construtor da super-classe Robo.
        this.altitude = altitude;
        this.altitude_max = altitude_max;
    }


    /**
     * Adiciona a altura atual do robo.
     * @param metros Indica quantos metros ele deve subir.
     */
    public int subir(int metros){
        if(this.altitude + metros > this.altitude_max){ //Verifica se a altura pode ser atingida.
            throw new IllegalArgumentException("A altitude do robo nao pode ultrapassar "+this.altitude_max+"m."); 
        }

        this.altitude+=metros; //Adiciona a altitude.
        return this.altitude; 

    }


    /**
     * Subtrai a altura atual do robo.
     * @param metros Indica quantos metros ele vai descer.
     */
    public int descer(int metros){
        if(this.altitude - metros <= 0){ //Verifica viabilidade de nova altitude descendente.
            throw new IllegalArgumentException("A altitude do robo não pode ser <= 0m.");
        }
    
        this.altitude -=metros; 
        return this.altitude; //Retorna nova altitude.
    }

}
