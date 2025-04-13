package sensor.temperatura;
import sensor.standart.*;

public class SensorTemperatura extends Sensor{

    private double temperatura;
    private final double precisao;

    //Construtores.

    public SensorTemperatura(double raio_alcance, String modelo,double temperatura, double precisao){
        super(raio_alcance,modelo);
        this.temperatura = temperatura;
        this.precisao = precisao;
    }
    //Sobrecarrega construtor no caso de ausencia de precisao.
    public SensorTemperatura(double raio_alcance, String modelo,double temperatura){
        super(raio_alcance,modelo);
        this.temperatura = temperatura;
        this.precisao = 0.05*temperatura; //Considera 5% da quantidade medida.
    }


    //Metodos

    /**
     * Retorna precisao do sensor.
     */
    public double get_precisao(){
        return this.precisao;
    }

    /**
     * Retorna incerteza associada a medida do sensor.
     */
    public double get_incerteza(){
        return this.precisao/1.73;
    }

    /**
     * Altera temperatura do sensor.
     */
    public void set_temperatura(double nova_temperatura){
        this.temperatura = nova_temperatura;
    }
    
    /**
     * Retorna temperatura medida pelo sensor em Kelvin.
     */
    public double get_temperaturaKelvin(){
        return this.temperatura;
    }

    /**
     * Retorna temperatura medida pelo sensor em Celcius.
     */
    public double get_temperaturaCelcius(){
        return (this.temperatura-273.15);
    }

    /**
     * Retorna temperatura medida pelo sensor em Fahrenheit.
     */
    public double get_temperaturaFahrenheit(){
        return (this.temperatura-273.15)*9/5+32;
    }

    /**
     * Sobreecreve a funcao 'monitorar', fazendo o sensor verificar as condicoes de temperatura.
     */
    @Override
    public void monitorar(double temperatura_minima, double temperatura_maxima){
        if(this.temperatura < temperatura_minima || this.temperatura > temperatura_maxima){ //Verifica a condicao do Robo em relacao a temperatura.
            System.out.printf("WARNING%n Sensor %s detectou um perigo critico! %n A temperatura atual e de %.2fK o que ultrapassa o limite de intervalo permitido. %nPotencial de grandes danos.%n",this.modelo,this.temperatura);
        }else{
            System.out.printf("%s: Nao ha risco detectado.%n ",this.modelo);
        }

    }

    /**
     * Estiliza a impressao do sensor, imprimindo suas medidas.
     */
    @Override
    public String toString(){
        return String.format(
        "%n****************************************%n"+
        "Relatorio do sensor %s: %n"+
        "Temperatura atual: (%.2f±%.2f)K%n"+
        "%t Em Celcius: (%.2f±%.2f)°C%n"+
        "%t Em Fahrenheit: (%.2f±%.2f)°F%n"+
        "Raio de alcance do %s: %.2f m"+
        "%n****************************************%n",
        this.modelo,
        this.get_temperaturaKelvin,this.incerteza,
        this.get_temperaturaCelcius,this.incerteza,
        this.get_temperaturaFahrenheit,this.incerteza,
        this.modelo,tis.raio_alcance;
        )
    }
}