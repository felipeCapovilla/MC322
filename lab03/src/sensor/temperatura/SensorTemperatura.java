package sensor.temperatura;

import sensor.standart.*;

public class SensorTemperatura extends Sensor{

    private double temperatura;
    private final double precisao;
    private final double temperatura_maxima;
    private final double temperatura_minima;

    //Construtores.

    public SensorTemperatura(double raio_alcance, String modelo, double precisao, double temperatura_maxima,double temperatura_minima){
        super(raio_alcance,modelo);
        this.precisao = precisao;
        this.temperatura =0;
        this.temperatura_maxima = temperatura_maxima;
        this.temperatura_minima = temperatura_minima;
    }
    //Sobrecarrega construtor no caso de ausencia de precisao.
    public SensorTemperatura(double raio_alcance, String modelo,double temperatura_maxima,double temperatura_minima){
        super(raio_alcance,modelo);
        this.temperatura = 0;
        this.precisao = 0.05*temperatura; //Considera 5% da quantidade medida.
        this.temperatura_maxima=temperatura_maxima;
        this.temperatura_minima=temperatura_minima;
    
    }


    //Metodos

    /**
     * Retorna precisao do sensor.
     */
    public double get_precisao(){
        return this.precisao;
    }

    /**
     * Retorna a temperatura maxima suportada pelo sensor.
     */
    public double get_temperaturaMaxima(){
        return this.temperatura_maxima;
    }

    /**
     * Retorna a temperatura minima suportada pelo sensor.
     */
    public double get_temperaturaMinima(){
        return this.temperatura_minima;
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
    public void monitorar(){
        if(this.temperatura < this.temperatura_minima || this.temperatura > this.temperatura_maxima){ //Verifica a condicao do Robo em relacao a temperatura.
            System.out.printf("WARNING%n Sensor %s detectou um perigo critico! %n A temperatura atual e de %.2fK o que ultrapassa o limite de intervalo permitido. %n Potencial de grandes danos.%n",get_modelo(),this.temperatura);
        }else{
            System.out.printf("%s: Nao ha risco detectado.%n ",get_modelo());
        }

    }

    /**
     * Estiliza a impressao do sensor, imprimindo suas medidas.
     */
    @Override
    public String toString(){
        return String.format("""
                             
                             ****************************************
                             Relatorio do sensor %s: 
                             Temperatura atual: (%.2f\u00b1%.2f)K
                             \t Em Celcius: (%.2f\u00b1%.2f)\u00b0C
                             \t Em Fahrenheit: (%.2f\u00b1%.2f)\u00b0F
                             Raio de alcance do %s: %.2f m
                             ****************************************
                             """,
        get_modelo(),
        get_temperaturaKelvin(),get_incerteza(),
        get_temperaturaCelcius(),get_incerteza(),
        get_temperaturaFahrenheit(),get_incerteza(),
        get_modelo(),get_raioAlcance()
        );
    }
}