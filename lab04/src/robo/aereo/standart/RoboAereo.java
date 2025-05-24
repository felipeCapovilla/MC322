package robo.aereo.standart;

import constantes.*;
import exceptions.*;
import interfaces.*;
import robo.standart.*;
import sensor.altitude.SensorAltitude;

public class RoboAereo extends Robo implements Battery {
    
    private int altitude;
    private final int altitude_max;
    private int bateria = 100;
    private final int carregarBateria = 20;

    public RoboAereo(String nome,int posicaoX, int posicaoY,Bussola direcao, int altitude,int altitude_max) throws ValueOutOfBoundsException{
        
        super(nome,posicaoX, posicaoY, altitude, direcao); //Chama o construtor da super-classe Robo.

        if(altitude < 0){
            throw new ValueOutOfBoundsException("altitude: " + altitude);
        }
        if(altitude_max < 0){
            throw new ValueOutOfBoundsException("altitude máxima: " + altitude_max);
        }

        this.altitude = altitude;
        this.altitude_max = altitude_max;
    }


    /**
     * Adiciona a altura atual do robo.
     * @param metros Indica quantos metros ele deve subir.
     */
    public void subir(int metros) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException, SensorMissingException, ValueOutOfBoundsException{
        int pesoBateria = 10;//quanto gasta de bateria
        
        if(this.altitude + metros > this.altitude_max){ //Verifica se a altura pode ser atingida por conta das limitações do robo.
            throw new ValueOutOfBoundsException("altitude acima da máxima: " + ((altitude + metros) - this.altitude_max)+"m."); 
        }

        if(this.get_SensorAltitude() == null){
            throw new SensorMissingException("Sensor de altitude");
        }

        if(detectarColisoes(getX(), getY(), (int) get_altitude() + metros)){
            descarregar(pesoBateria);

            mover(0, 0, altitude);
            this.altitude+=metros; //Adiciona a altitude.
            this.get_SensorAltitude().set_altitude(this.altitude);
        } else {
            throw new ColisaoException(String.format("(%d,%d,%d)", getX(), getY(), (int) get_altitude() + metros));
        }
        
    }

    

    /**
     * Subtrai a altura atual do robo.
     * @param metros Indica quantos metros ele vai descer.
     */
    public void descer(int metros) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException, SensorMissingException{
        int pesoBateria = 10;//quanto gasta de bateria

        if(this.get_SensorAltitude() == null){
            throw new SensorMissingException("Sensor de altitude");
        }
        
        if(detectarColisoes(getX(), getY(), (int) get_altitude() - metros)){
            descarregar(pesoBateria);

            mover(0, 0, -metros);
            this.altitude-=metros; //Adiciona a altitude.
            this.get_SensorAltitude().set_altitude(this.altitude);
        } else {
            throw new ColisaoException(String.format("(%d,%d,%d)", getX(), getY(), (int) get_altitude() + metros));
        }
    }

    /**
     * Move o Robo no plano XY
     */
    public void mover(int deltaX, int deltaY) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException {
        int pesoBateria = 5;//quanto gasta de bateria

        if(pesoBateria <= bateria){
            //Evitar de mover mesmo com bateria baixa ou perder bateria em um movimento inválido
            super.mover(deltaX, deltaY, 0);
            descarregar(pesoBateria);
        } else {
            descarregar(pesoBateria);//joga o erro de lowBattery
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

    @Override
    public void executarTarefa() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executarTarefa'");
    }


    @Override
    public void carregar() {
        bateria = Math.min(bateria + carregarBateria, 100);
    }

    @Override
    public void descarregar(int descarga) throws LowBatteryException{
        if(bateria >= descarga){
            bateria -= descarga;
        } else {
            throw new LowBatteryException(bateria + "%");
        }
    }

    @Override
    public int getBateria() {
        return bateria;
    }

}
