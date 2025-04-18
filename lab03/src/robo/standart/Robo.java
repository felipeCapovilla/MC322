package robo.standart;

import ambiente.Ambiente;
import constantes.Bussola;
import java.util.ArrayList;
import sensor.altitude.*;
import sensor.standart.Sensor;
import sensor.temperatura.*;


public class Robo
{
    private Ambiente ambiente_atual;
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private SensorTemperatura sensor_temperatura;
    private SensorAltitude sensor_altitude;
    protected final ArrayList<Sensor> sensores;

    /**
     * NORTE, SUL, LESTE, OESTE
     */
    private Bussola direcao;


    /**
     * Construtor da classe Robo.
     */
    public Robo(String nome, int posicaoX, int posicaoY, Bussola direcao) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.ambiente_atual = null;
        this.direcao = direcao;
        sensores = new ArrayList<>();
        this.sensor_altitude = null;
        this.sensor_temperatura = null;
    }

    
    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual.
     * @param deltaX
     * @param deltaY
     */
    public void mover(int deltaX, int deltaY) { 
        if(ambiente_atual!=null &&ambiente_atual.dentroDosLimites(posicaoX + deltaX, posicaoY + deltaY,0)){
            posicaoX += deltaX;
            posicaoY += deltaY;
        } else {
            throw new IllegalArgumentException("Tentativa de mover fora dos limites. Continua na posição (" + posicaoX + "," + posicaoY + ")");
        }
        
    }

    /**
     * Identifica os obstáculos presentes no ambiente atual
     */
    public void identificar_obstaculos(){
        if(ambiente_atual != null){ //apenas verifica se o robo estiver em um ambiente

            System.out.printf("Foram identificados %d obstaculos no ambiente.\n",this.ambiente_atual.get_quantidade_obstaculos());
            
            for(int i=0;i<this.ambiente_atual.get_quantidade_obstaculos();i++){
                System.out.println("Obstaculo"+i+": "+ this.ambiente_atual.getObstaculos().get(i));
            }
        } else {
            System.out.printf("Robo %s não está em um ambiente\n", nome);
        }
        

    }

    public void adicionar_sensorTemperatura(double raio_alcance, String modelo, double precisao, double temperatura_maxima, double temperatura_minima){
        SensorTemperatura novo_sensorTemperatura = new SensorTemperatura(raio_alcance,modelo,precisao,temperatura_maxima,temperatura_minima);
        this.sensor_temperatura = novo_sensorTemperatura;
        sensores.add(novo_sensorTemperatura);
    }

   

    public void adicionar_sensorAltitude(double raio_alcance, String modelo,double precisao, double altura_maxima){
        SensorAltitude novo_SensorAltitude = new SensorAltitude(raio_alcance,modelo,precisao,altura_maxima);
        this.sensor_altitude = novo_SensorAltitude;
        sensores.add(novo_SensorAltitude);
    }

    //GETs e SETs

    /**
     * Seta o novo sensor de temperatura, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorTemperatura(SensorTemperatura novo_sensor){
        this.sensor_temperatura = novo_sensor;
    }

    public void set_sensorAltitude(SensorAltitude novo_sensor){
        this.sensor_altitude  = novo_sensor;
    }
    
    /**
     * @return vetor com duas posicoes, que sao (x,y) do robo.
     */
    public int[] get_posicao(){
        return new int[]{this.posicaoX, this.posicaoY};
    }

    /**
     * Retorna o valor da variável nome
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Adiciona o robo num ambiente.
     * @param novo_ambiente Ambiente a ser adicionado.
     */
    public void set_ambiente(Ambiente novo_ambiente){
        this.ambiente_atual = novo_ambiente;
    }

    /**
     * Retorna o valor da variável ambiente_atual
     */
    public Ambiente get_ambiente(){
        return this.ambiente_atual;
    }

    public double get_altitude(){
        return 0;
    }
    
    /**
     * define o valor da variável nome
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Retorna o objeto sensorTemperatura associado ao robo.
     */
    public SensorTemperatura get_SensorTemperatura(){
        return this.sensor_temperatura;
    }

    /**
     * Retorna o objeto sensorAltitude associado ao robo.
     */
    public SensorAltitude get_SensorAltitude(){
        return this.sensor_altitude;
    }

    /**
     * Retorna o valor da variável direção
     */
    public Bussola getDirecao(){
        return this.direcao;
    }

    /**
     * define o valor da variável direção
     */
    public void setDirecao(Bussola direcao){
        this.direcao = direcao;
        
    }

    @Override
    public String toString() {
        return String.format("%S (%S)", nome, getClass().getSimpleName());
    }


}
