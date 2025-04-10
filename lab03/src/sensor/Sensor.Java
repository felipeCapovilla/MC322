public class Sensor(){

    //Ambas variaveis 'final' -> fixas.
    private final double raio_alcance;
    private final String modelo;


    public Sensor(double raio_alcance, String modelo){
        this.raio_alcance = raio_alcance;
        this.modelo = modelo;
    }
    /**
     * Sobrecarga do construtor, caso nao seja enviado o modelo seta um padrao.
     * 
     */
    public Sensor(double raio_alcance){
        this.raio_alcance = raio_alcance;
        this.modelo = "AA-000";
    }


    
    /**
     * Método generico -> sensor ainda não tem especificidade.
     */
    public String monitorar(){
        System.out.printf("Monitorando num raio de %.2f metros.",this.raio_alcance);

    }   

    /**
     * Métodos gets.
     */
    public double get_raioAlcance(){
        return this.raio_alcance;
    }

    public String get_modelo(){
        return this.modelo;
    }


    /**
     * Sobreescrita do método padrão toString estilizado.
     */
    @Override
    public void toString(){
        System.out.printf("Sensor: %s%n Raio de Operação: %.2f",this.modelo,this.raio_alcance);
    }






}