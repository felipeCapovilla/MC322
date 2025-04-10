package sensor.altitude;
import sensor.standart.*;

public class SensorAltitude extends Sensor{

    private double altitude_atual;
    private final double precisao;


    //Construtor.

    public SensorAltitude(double raio_alcance, String modelo,double altitude_atual,double precisao){
        
        super(raio_alcance,modelo);
        this.altitude_atual = altitude_atual;
        this.precisao = precisao;
    }
    
    //Sobrecarga o construtor no caso de ausencia de 'precisao'.
    public SensorAltitude(double raio_alcance, String modelo,double altitude_atual){
        
        super(raio_alcance,modelo);
        this.altitude_atual = altitude_atual;
        this.precisao = 0.05*altitude_atual; //Usa precisao generica: 0,5% do valor medido.
    }
    
    //Metodos.


    /**
     * Altera a altura atual do sensor, quando robo muda altitude.
     */
    public void set_altitude(double nova_altitude){
        this.altitude_atual = nova_altitude;
    }

    /**
     * Retorna altitude atual do sensor portanto do robo.
     */
    public double get_altitude(){
        return this.altitude_atual;
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
     * Sobreescreve o metodo toString para retornar o modelo e altitude atual com incerteza associada.
     */
    @Override
    public String toString(){
        return String.format("%s%nAltitude atual: (%.2f±%.2f)m.%n",this.modelo,this.altitude_atual,get_incerteza());
    }

    
    
}
