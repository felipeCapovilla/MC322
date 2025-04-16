package sensor.standart;


public class Sensor{

    private final double raio_alcance;
    private final String modelo;
    private static int quantidade_sensores;


    //Construtores


    public Sensor(double raio_alcance, String modelo){
        this.raio_alcance = raio_alcance;
        this.modelo = modelo;
        quantidade_sensores++;
    }

    /**
     * Sobrecarrega o construtor no caso de ausencia de modelo.
     */
    public Sensor(double raio_alcance){
        this.raio_alcance = raio_alcance;
        quantidade_sensores++;
        this.modelo = String.format("GGG-%02d",quantidade_sensores);
    }

    //Metodos


    /**
     * Metodo generico, ainda nao ha especificidade no sensor.
     */
    public void monitorar(){
        System.out.printf("Monitorando ambiente num raio de %.2f metros.",this.raio_alcance);
    }


    //Metodos get's. 


    /**
     * Returona quantidade de sensores ativos.
     */
    public int get_quantidadeSensores(){
        return quantidade_sensores;
    }

    /**
     * Retrorna raio de alcance maximo do sensor.
     */
    public double get_raioAlcance(){
        return this.raio_alcance;
    }

    /**
     * Retorna modelo do sensor.
     */
    public String get_modelo(){
        return this.modelo;
    }


    //Sobreescritas

    /**
     * Sobreescreve metodo toString estilizando-o.
     */
    @Override
    public String toString(){
        return String.format("Sensor: %s.%n Raio de alcance maximo: %.2fm.%n",this.modelo,this.raio_alcance);
    }

}

