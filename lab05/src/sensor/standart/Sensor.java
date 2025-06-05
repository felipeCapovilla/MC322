package sensor.standart;


public abstract class Sensor{

    private final int raio_alcance;
    private final String modelo;
    private static int quantidade_sensores;

    //Construtores


    public Sensor(int raio_alcance, String modelo){
        this.raio_alcance = (raio_alcance < 0)? -raio_alcance:raio_alcance;
        this.modelo = modelo;
        quantidade_sensores++;
    }

    /**
     * Sobrecarrega o construtor no caso de ausencia de modelo.
     */
    public Sensor(int raio_alcance){
        this.raio_alcance = (raio_alcance < 0)? -raio_alcance:raio_alcance;
        quantidade_sensores++;
        this.modelo = String.format("GGG-%02d",quantidade_sensores);
    }

    //Metodos

    /**
     * Monitora o estado do sensor
     */
    public void monitorar(){}


    //Metodos get's. 


    /**
     * Returona quantidade de sensores ativos.
     */
    public static int get_quantidadeSensores(){
        return quantidade_sensores;
    }

    /**
     * Retrorna raio de alcance maximo do sensor.
     */
    public int get_raioAlcance(){
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
        return String.format("Sensor: %s.%n Raio de alcance maximo: %dfm.%n",this.modelo,this.raio_alcance);
    }

}

