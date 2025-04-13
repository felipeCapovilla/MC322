package robo.standart;

import java.util.ArrayList;

import ambiente.Ambiente;
import constantes.Bussula;
import sensor.standart.Sensor;
import sensor.temperatura.*;


public class Robo
{
    private Ambiente ambiente_atual;
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private final ArrayList<Sensor> sensores;

    /**
     * NORTE, SUL, LESTE, OESTE
     */
    private Bussula direcao;


    /**
     * Construtor da classe Robo.
     */
    public Robo(String nome, int posicaoX, int posicaoY, Bussula direcao) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.ambiente_atual = null;
        this.direcao = direcao;
        sensores = new ArrayList<>();
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

    public void adicionar_sensorTemperatura(double raio_alcance, String modelo, double temperatura,double precisao){
        SensorTemperatura novo_sensor = new SensorTemperatura(raio_alcance,modelo,temperatura,precisao;)
    }

    //GETs e SETs

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
    
    /**
     * define o valor da variável nome
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Retorna o valor da variável direção
     */
    public Bussula getDirecao(){
        return this.direcao;
    }

    /**
     * define o valor da variável direção
     */
    public void setDirecao(Bussula direcao){
        this.direcao = direcao;
        
    }

    @Override
    public String toString() {
        return nome;
    }


}
