package robo.standart;

import java.util.ArrayList;

import ambiente.Ambiente;
import ambiente.Obstaculo;
import constantes.Bussola;
import java.util.Arrays;
import sensor.standart.Sensor;
import sensor.temperatura.SensorTemperatura;
import sensor.altitude.SensorAltitude;
import sensor.espacial.SensorEspacial;


public class Robo
{
    private Ambiente ambiente_atual;
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private SensorTemperatura sensor_temperatura;
    private SensorAltitude sensor_altitude;
    private SensorEspacial sensor_espacial;
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
        sensores = new ArrayList<Sensor>();
        this.sensor_altitude = null;
        this.sensor_temperatura = null;
        this.sensor_espacial = null;
    }

    
    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual.
     * @param deltaX
     * @param deltaY
     */
    public void mover(int deltaX, int deltaY) { 
        if(ambiente_atual!=null &&ambiente_atual.dentroDosLimites(posicaoX + deltaX, posicaoY + deltaY,(int) get_altitude())){
            if(detectarColisoes(posicaoX + deltaX, posicaoY + deltaY, (int) get_altitude())){
                posicaoX += deltaX;
                posicaoY += deltaY;
            } else {
                throw new IllegalArgumentException("Posicao ja ocupada");
            }
                
        } else {
            throw new IllegalArgumentException("Tentativa de mover fora dos limites. Continua na posição (" + posicaoX + "," + posicaoY + ")");
        }
        
    }

    /**
     * Verifica se o espaco verificado esta livre
     * @param X
     * @param Y
     * @return
     */
    public boolean detectarColisoes(int X, int Y, int Z){
        return !identificar_obstaculos(X, Y, Z) && !identificar_robos(X, Y, Z);
    }

    /**
     * Identifica se a posicao esta ocupado por um obstaculo
     */
    private boolean identificar_obstaculos(int X, int Y, int Z){
        if(ambiente_atual != null){ //apenas verifica se o robo estiver em um ambiente
            ArrayList<Obstaculo> obstaculos = ambiente_atual.getObstaculos();

            for(int i=0;i<this.ambiente_atual.get_quantidade_obstaculos();i++){
                if(!ambiente_atual.getObstaculos().get(i).Passavel() && ambiente_atual.getObstaculos().get(i).estaDentro(X, Y) && obstaculos.get(i).getAltura() > Z){
                    return true;
                }
            }

            return false;
        } else {
            System.out.printf("Robo %s não está em um ambiente\n", nome);
            return false;
        }
    }

    /**
     * Identifica se a posicao esta ocupado por um robo
     */
    private boolean identificar_robos(int X, int Y, int Z){
        if(ambiente_atual != null){ //apenas verifica se o robo estiver em um ambiente
            int[] pos = {X, Y};
            ArrayList<Robo> listaRobos = ambiente_atual.getListaRobos();
            
            for(int i=0;i<this.ambiente_atual.get_robos_ativos();i++){

                if(Arrays.equals(ambiente_atual.getListaRobos().get(i).get_posicao(), pos) && listaRobos.get(i).get_altitude() == Z){
                    return true;
                }
            }

            return false;
        } else {
            System.out.printf("Robo %s não está em um ambiente\n", nome);
            return false;
        }
    }

    /**
     * Adicionar um sensor de temperatura no robo
     */
    public void adicionar_sensorTemperatura(int raio_alcance, String modelo, double precisao, double temperatura_maxima, double temperatura_minima){
        SensorTemperatura novo_sensorTemperatura = new SensorTemperatura(raio_alcance,modelo,precisao,temperatura_maxima,temperatura_minima);
        this.sensor_temperatura = novo_sensorTemperatura;
        sensores.add(novo_sensorTemperatura);
    }

   
    /**
     * Adicionar um sensor de altitude no robo
     */
    public void adicionar_sensorAltitude(int raio_alcance, String modelo,double precisao, double altura_maxima){
        SensorAltitude novo_SensorAltitude = new SensorAltitude(raio_alcance,modelo,precisao,altura_maxima);
        this.sensor_altitude = novo_SensorAltitude;
        sensores.add(novo_SensorAltitude);
    }

    /**
     * Adicionar um sensor espacial no robo
     */
    public void adicionar_sensorEspacial(int raio_alcance, String modelo){
        SensorEspacial novo_SensorEspacial = new SensorEspacial(raio_alcance,modelo);
        this.sensor_espacial = novo_SensorEspacial;
        sensores.add(novo_SensorEspacial);

    }

    //GETs e SETs

    /**
     * Seta o novo sensor de temperatura, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorTemperatura(SensorTemperatura novo_sensor){
        this.sensor_temperatura = novo_sensor;
    }

    /**
     * Seta o novo sensor de altitude, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorAltitude(SensorAltitude novo_sensor){
        this.sensor_altitude  = novo_sensor;
    }

    /**
     * Seta o novo sensor espacial, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorEspacial(SensorEspacial novo_sensor){
        this.sensor_espacial = novo_sensor;
    }
    
    /**
     * @return vetor com duas posicoes, que sao (x,y) do robo.
     */
    public int[] get_posicao(){
        return new int[]{this.posicaoX, this.posicaoY};
    }

    /**
     * Retorna a altura do robo
     */
    public double get_altitude(){
        return 0;
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
     * Retorna o objeto sensorEspacial associado ao robo.
     */
    public SensorEspacial get_SensorEspacial(){
        return this.sensor_espacial;
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
        return String.format("%s [%s]", getClass().getSimpleName(), nome);
    }


}
