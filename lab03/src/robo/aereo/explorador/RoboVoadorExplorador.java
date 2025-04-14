package robo.aereo.explorador;


import constantes.Bussula;
import robo.aereo.standart.*;
import sensor.temperatura.SensorTemperatura;

public class RoboVoadorExplorador extends RoboAereo {

    private int temperatura_atual;
    private int pressao_atual;
    private final int velocidade_max;
    private int velocidade_atual;
    private String planeta_atual;
    private boolean em_missao;


    public RoboVoadorExplorador(String nome,int posicaoX, int posicaoY,Bussula direcao,int altitude,int altitude_max,int velocidade_max){
        
        super(nome,posicaoX,posicaoY,direcao,altitude,altitude_max); //Inicializa as variaveis da classe herdada.
        
        this.velocidade_max = velocidade_max;
        
        //Inicia o robo como 'inativo'.
        this.pressao_atual = 0;
        this.temperatura_atual =0;
        this.velocidade_atual =0;
        this.planeta_atual = "";
        this.em_missao = false;
    }

    /** 
     * Inicia a atividade do robo.
     * @param pressao_atual Pressao atual do ambiente em que esta.
     * @param temperatura_atual Temperatura do ambiente que esta.
     * @param velocidade_atual Velocidade atual.
     * @param planeta Qual planeta esta o robo.
    */
    public void iniciar_exploracao(int pressao_atual, int temperatura_atual, int velocidade_atual, String planeta){

        //Verificação de valores inválidos
        if(velocidade_atual > this.velocidade_max){ //Verifica se velocidade respeita limites do robo.
            throw new IllegalArgumentException("A velocidade do Robo não pode ultrapassar "+this.velocidade_max+"m/s"); //Se nao: lança erro.
        } else if (temperatura_atual < 0) { //Verifica se a temperatura é plausivel.
            throw new IllegalArgumentException("A temperatura nao pode ser menor que 0K"); //Se nao: lança erro.
        }

        //Se tudo estiver válido
        this.velocidade_atual = velocidade_atual; 
        this.temperatura_atual = temperatura_atual; 
        this.em_missao = true; 
        this.pressao_atual = pressao_atual;
        this.planeta_atual = planeta;
    }

     /**
     * Finaliza a missao e libera o robo para uso.
     */
    public void finalizar_exploracao(){
        this.em_missao = false;
        this.planeta_atual = "";
        this.pressao_atual=0;
        this.temperatura_atual=0;
        this.velocidade_atual=0;
    }

    //GETs e SETs

    /**
     * Altera a temperatura atual percebida pelo robo.
     * @param nova_temperatura Nova temperatura a ser setada.
     */
    public void set_temperatura(int nova_temperatura){ 
        if(nova_temperatura < 0){ //Verifica se a temperatura é plausivel.
            throw new IllegalArgumentException("A temperatura nao pode ser menor que 0K"); //Se nao: lanca erro.
        }
        this.temperatura_atual = nova_temperatura; //Se for: seta o valor.
        this.get_SensorTemperatura().set_temperatura(nova_temperatura); //Altera a informacao no sensor.
    }

    /**
     * Sobreescrever o metodo de adicionarSensor de temperatura pois aqui o robo tem temperatura definida.
     */
    @Override
    public void adicionar_sensorTemperatura(double raio_alcance, String modelo, double precisao, double temperatura_maxima, double temperatura_minima){
        SensorTemperatura novo_sensorTemperatura = new SensorTemperatura(raio_alcance,modelo,precisao,temperatura_maxima,temperatura_minima);
        novo_sensorTemperatura.set_temperatura(this.temperatura_atual);
        this.set_sensorTemperatura(novo_sensorTemperatura); 
        sensores.add(novo_sensorTemperatura);
    }

    /**
     * Altera a pressao atual percebida pelo robo.
     * @param nova_pressao O novo valor da variavel pressao.
     */
    public void set_pressao(int nova_pressao){
        this.pressao_atual = nova_pressao;
    }


    /**
     * Altera a velocidade atual do robo.
     * @param nova_velocidade O novo valor da velocidade.
     */
    public void set_velocidade(int nova_velocidade){ 
        if(nova_velocidade > this.velocidade_max){ //Verifica se nova_velocidade e permitida dentros dos limites.
            throw new IllegalArgumentException("A velocidade maxima nao deve exceder "+this.velocidade_max+"m/s."); //Se nao: gera erro.
        }
        this.velocidade_atual = nova_velocidade; //Se sim: seta velocidade.
    }


    /**
     * Retorna temperatura atual percebida pelo robo pelo sensor.
     */
    public double get_temperatura(){
        return this.get_SensorTemperatura().get_temperaturaKelvin(); 
    }


     /**
     * Retorna pressao atual percebida pelo robo.
     */
    public int get_pressao(){
        return this.pressao_atual;
    }


     /**
     * Retorna velocidade atual do robo.
     */
    public int get_velocidade(){
        return this.velocidade_atual;
    }


     /**
     * Retorna o planeta sendo explorado pelo robo.
     */
    public String get_planeta(){
        return this.planeta_atual;
    }


     /**
     * Retorna se o robo esta sendo usado.
     */
    public boolean status_missao(){
        return this.em_missao;
    }


    
}


