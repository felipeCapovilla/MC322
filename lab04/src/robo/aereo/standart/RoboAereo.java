package robo.aereo.standart;

import constantes.Bussola;
import java.awt.geom.IllegalPathStateException;
import robo.standart.*;
import sensor.altitude.SensorAltitude;

public class RoboAereo extends Robo {
    
    private int altitude;
    private final int altitude_max;

    public RoboAereo(String nome,int posicaoX, int posicaoY,Bussola direcao, int altitude,int altitude_max){
        
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
            if(this.get_ambiente().get_altura() <= this.altitude+metros) //Verifica se a altura pode ser atingida devido as limitações do ambiente.
                throw new IllegalArgumentException("A altitude do robo '"+this.getNome()+"' não pode ultrapassar os limites do ambiente.");
        }

        if(this.altitude + metros > this.altitude_max){ //Verifica se a altura pode ser atingida por conta das limitações do robo.
            throw new IllegalArgumentException("A altitude do robo '"+this.getNome()+"' não pode ultrapassar "+this.altitude_max+"m."); 
        }

        if(this.get_SensorAltitude() == null){
            throw new IllegalPathStateException("Sensor de altitude não instalado, nao e seguro movimento vertical");
        }

        if(detectarColisoes(get_posicao()[0], get_posicao()[1], (int) get_altitude() + metros)){
            this.altitude+=metros; //Adiciona a altitude.
            this.get_SensorAltitude().set_altitude(this.altitude);
        } else {
            throw new IllegalArgumentException("Posicao ja ocupada");
        }
        
    }

    

    /**
     * Subtrai a altura atual do robo.
     * @param metros Indica quantos metros ele vai descer.
     */
    public void descer(int metros){
        if(this.altitude - metros < 0){ //Verifica viabilidade de nova altitude descendente.
            throw new IllegalArgumentException("A altitude do robo não pode ser < 0m.");
        }

        if(this.get_SensorAltitude() == null){
            throw new IllegalPathStateException("Sensor de altitude não instalado, nao e seguro movimento vertical");
        }
        
        if(detectarColisoes(get_posicao()[0], get_posicao()[1], (int) get_altitude() - metros)){
            this.altitude-=metros; //Adiciona a altitude.
            this.get_SensorAltitude().set_altitude(this.altitude);
        } else {
            throw new IllegalArgumentException("Posicao ja ocupada");
        }
    }

    /**
     * Sobreescreve o metodo adicionar_sensorAltitude pois aqui o robo ja pode ter altitude, a qual e passada pelo sensor.
     */
    @Override
    public void adicionar_sensorAltitude(int raio_alcance, String modelo,double precisao, double altura_maxima){
        SensorAltitude novo_SensorAltitude = new SensorAltitude(raio_alcance,modelo,precisao,altura_maxima);
        novo_SensorAltitude.set_altitude(this.altitude);
        this.set_sensorAltitude(novo_SensorAltitude); 
        sensores.add(novo_SensorAltitude);
    }

    /**
     * Retorna o valor da variável altitude pelo sensor, caso esteja dentro dos limites de seu funcionamento.
     */
    @Override
    public double get_altitude() {

        if(get_SensorAltitude() != null && this.altitude <= this.get_SensorAltitude().get_alturaMaxima()){ //Se a altitude atual pode ser medida pelo sensor.
            return this.get_SensorAltitude().get_altitude();
        }
        else{
            return -1.0; //Caso contrario: retorna um numero incoerente (-1).
        }
    }

    /**
     * Define o valor da variável altitude
     */
    public void set_altitude(int altitude) {
        if(altitude < 0){
            System.out.println("A altitude do robo não pode ser < 0m. Ajustando altura para 0m.");
            this.altitude = 0;
            this.get_SensorAltitude().set_altitude(0);
        } else if(this.get_ambiente() != null && altitude >= this.get_ambiente().get_altura()){
            System.out.println("A altitude não pode ultrapassar o maximo do ambiente. Altura ajustada para "+this.get_ambiente().get_altura()+"m.");
            this.altitude = this.get_ambiente().get_altura();
            this.get_SensorAltitude().set_altitude(this.get_ambiente().get_altura());
        }else if(altitude > altitude_max){
            System.out.println("A altitude não pode ultrapassar o limite do robo. Altura ajustada para "+this.altitude_max+"m.");
            this.altitude = altitude_max;
            this.get_SensorAltitude().set_altitude(altitude_max);
        } else {
            this.altitude = altitude;
            this.get_SensorAltitude().set_altitude(altitude);
        }
    }

    /**
     * Retorna o valor da variável altitude maxima
     */
    public int get_altitude_max() {
        return altitude_max;
    }

}
