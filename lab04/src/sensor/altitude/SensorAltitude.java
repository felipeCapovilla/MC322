package sensor.altitude;

import exceptions.*;
import sensor.standart.Sensor;


public class SensorAltitude extends Sensor{

    private double altitude_atual;
    private final double altura_maxima;
    private final double precisao;



    //Construtor.

    /**
     * Construtor do Sensor Temperatura
     * @param raio_alcance input > 0
     * @param modelo
     * @param precisao input > 0
     * @param altura_maxima input > 0
     */
    public SensorAltitude(int raio_alcance, String modelo,double precisao, double altura_maxima) throws ValueOutOfBoundsException{
        super(raio_alcance,modelo);

        if(precisao < 0){
            throw new ValueOutOfBoundsException("precisao < 0");
        } else if(altura_maxima < 0){
            throw new ValueOutOfBoundsException("altura_maxima < 0");
        }

        this.altitude_atual = 0;
        this.precisao = precisao;
        this.altura_maxima = altura_maxima;

    }
    
    /**
     * Construtor do Sensor Temperatura, sem especificar precisao
     * @param raio_alcance input > 0
     * @param modelo
     * @param altura_maxima input > 0
     */
    public SensorAltitude(int raio_alcance, String modelo, double altura_maxima) throws ValueOutOfBoundsException{
        
        super(raio_alcance,modelo);

        if(altura_maxima < 0){
            throw new ValueOutOfBoundsException("altura_maxima < 0");
        }

        this.altitude_atual = 0;
        this.precisao = 0.05*altitude_atual; //Usa precisao generica: 0,5% do valor medido.
        this.altura_maxima = altura_maxima;
    }

    //Metodos.


    /**
     * Altera a altura atual do sensor, quando robo muda altitude.
     */
    public void set_altitude(double nova_altitude) throws ValueOutOfBoundsException{
        if(nova_altitude > this.altura_maxima){
            throw new ValueOutOfBoundsException("altura > " + altura_maxima); 
        }else if (nova_altitude < 0) {
            throw new ValueOutOfBoundsException("altura < 0");
        }else {
            this.altitude_atual = nova_altitude;
        }
        
    }

    /**
     * Retorna altitude atual do sensor portanto do robo.
     */
    public double get_altitude(){
        return this.altitude_atual;
    }

    /**
     * Retorna a altuda maxima suportada pelo sensor.
     */
    public double get_alturaMaxima(){
        return this.altura_maxima;
    }
    /**
     * Retorna a precisao do sensor.
     */
    public double get_precisao(){
        return this.precisao;
    }

    /**
     * Retorna a incerteza associada ao sensor.
     */
    public double get_incerteza(){
        return this.precisao/1.73;
    }

    /**
     * Sobreescreve o metodo monitorar conforme as especificidades do sensor de altitude.
     */
    @Override
    public void monitorar(){
        double delta_h = this.altura_maxima - this.altitude_atual;
        System.out.printf(
            "%s: %n"+
            " Monitoramento Completo!!!%n"+
            " Altitude atual: (%.2f±%.2f)m%n"+
            " Margem restante para elevação de altura ainda disponível: (%.2f±%.2f)m.%n",
            get_modelo(),
            this.altitude_atual,get_incerteza(),
            delta_h,get_incerteza()
        );
        
    }

    /**
     * Sobreescreve o metodo toString para retornar o modelo e altitude atual com incerteza associada.
     */
    @Override
    public String toString(){
        return String.format("%s%nAltitude atual: (%.2f±%.2f)m.%n",get_modelo(),this.altitude_atual,get_incerteza());
    }

    
    
}
