package robo.standart;

import java.util.Arrays;
import ambiente.Ambiente;

public class Robo
{
    private Ambiente ambiente_atual;
    private String nome;
    private int posicaoX;
    private int posicaoY;
    /**
     * NORTE, SUL, LESTE, OESTE
     */
    private String direcao;

    /**
     * Construtor da classe Robo.
     */
    public Robo(String nome, int posicaoX, int posicaoY, String direcao) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.direcao = direcao;
        this.ambiente_atual = null;

    }


    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual.
     * @param deltaX
     * @param deltaY
     */
    public void mover(int deltaX, int deltaY) { //Usar um polimorfismo em mover.
        if(ambiente_atual.dentroDosLimites(posicaoX + deltaX, posicaoY + deltaY)){
            posicaoX += deltaX;
            posicaoY += deltaY;
        } else {
            throw new IllegalArgumentException("Tentativa de mover fora dos limites. Continua na posição (" + posicaoX + "," + posicaoY + ")");
        }
        
    }

    public void identificar_obstaculos(){
        System.out.printf("Foram identificados %d obstaculos no ambiente.\n",this.ambiente_atual.get_quantidade_obstaculos());
        for(int i=0;i<this.ambiente_atual.get_quantidade_obstaculos();i++){
            System.out.println("Obstaculo"+i+": "+Arrays.toString(this.ambiente_atual.getObstaculos().get(i)));
        }

    }



    /**
     * @return vetor com duas posicoes, que sao (x,y) do robo.
     */
    public int[] get_posicao(){
        return new int[]{this.posicaoX, this.posicaoY};
    }

    /**
     * Retorna o nome do robo.
     * @return
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
     * Retorna ambiente atual.
     */
    public Ambiente get_ambiente(){
        return this.ambiente_atual;
    }

    
    /**
     * Altera nome do robo.
     */
    public void setNome(String nome){
        this.nome = nome;
    }


    /**
     * Retorna direcao robo.
     * @return
     */
    public String getDirecao(){
        return this.direcao;
    }


    /**
     * Ajusta nova direcao robo.
     * @param nova_direcao Nova direcao a ser ajustada.
     */
    public void setDirecao(String direcao){
        this.direcao = direcao;
    }

    @Override
    public String toString() {
        return nome;
    }


}
