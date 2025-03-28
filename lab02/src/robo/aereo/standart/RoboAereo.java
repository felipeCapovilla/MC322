package robo.aereo.standart;

import robo.standart.*;

public class RoboAereo extends Robo {
    
    private int altitude;
    private final int altitude_max;

    public RoboAereo(String nome,int posicaoX, int posicaoY,String direcao, int altitude,int altitude_max){
        
        super(nome,posicaoX, posicaoY,direcao); //Chama o construtor da super-classe Robo.
        this.altitude = altitude;
        this.altitude_max = altitude_max;
    }


    /**
     * Adiciona a altura atual do robo.
     * @param metros Indica quantos metros ele deve subir.
     */
    public void subir(int metros){
        
        if(this.get_ambiente() != null){ //Faz a verificação ambiente apenas se o robo estiver em um.
            if(this.get_ambiente().getAltura() < this.altitude+metros) //Verifica se a altura pode ser atingida devido as limitações do ambiente.
                throw new IllegalArgumentException("A altitude do robo '"+this.getNome()+"' não pode ultrapassar os limites do ambiente.");
        }

        if(this.altitude + metros > this.altitude_max){ //Verifica se a altura pode ser atingida por conta das limitações do robo.
            throw new IllegalArgumentException("A altitude do robo '"+this.getNome()+"' não pode ultrapassar "+this.altitude_max+"m."); 
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

    public int get_altitude() {
        return altitude;
    }

    public void set_altitude(int altitude) {
        if(altitude < 0){
            System.out.println("A altitude do robo não pode ser < 0m. Ajustando altura para 0m.");
            this.altitude = 0;
        } else if(altitude > this.get_ambiente().getAltura()){
            System.out.println("A altitude não pode ultrapassar o maximo do ambiente. Altura ajustada para "+this.get_ambiente().getAltura()+"m.");
            this.altitude = this.get_ambiente().getAltura();
        }else if(altitude > altitude_max){
            System.out.println("A altitude não pode ultrapassar o limite do robo. Altura ajustada para "+this.altitude_max+"m.");
            this.altitude = altitude_max;
        } else {
            this.altitude = altitude;
        }
    }

    public int get_altitude_max() {
        return altitude_max;
    }

}
