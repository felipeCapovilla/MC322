package robo.aereo.standart;

import ambiente.Ambiente;
import robo.standart.*;

// TODO: Sobreescrever funcao toString de ambas classes herdadas de RoboAereo && adicionar metodo de verificar obstaculos em torno do robo.


public class RoboAereo extends Robo {
    
    private int altitude;
    private final int altitude_max;

    public RoboAereo(int posicaoX, int posicaoY, Ambiente ambiente,String direcao, String nome, int altitude,int altitude_max){
        
        super(posicaoX, posicaoY,direcao, nome); //Chama o construtor da super-classe Robo.
        this.altitude = altitude;
        this.altitude_max = altitude_max;
    }


    /**
     * Adiciona a altura atual do robo.
     * @param metros Indica quantos metros ele deve subir.
     */
    public void  subir(int metros){
        if(this.altitude + metros > this.altitude_max){ //Verifica se a altura pode ser atingida.
            throw new IllegalArgumentException("A altitude do robo nao pode ultrapassar "+this.altitude_max+"m."); 
        }

        this.altitude+=metros; //Adiciona a altitude.
    }


    /**
     * Subtrai a altura atual do robo.
     * @param metros Indica quantos metros ele vai descer.
     */
    public void descer(int metros){
        if(this.altitude - metros <= 0){ //Verifica viabilidade de nova altitude descendente.
            throw new IllegalArgumentException("A altitude do robo não pode ser <= 0m.");
        }
    
        this.altitude -=metros; 
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        if(altitude < 0){
            this.altitude = 0;
        } else if(altitude > altitude_max){
            this.altitude = altitude_max;
        } else {
            this.altitude = altitude;
        }
    }

    public int getAltitude_max() {
        return altitude_max;
    }

}
