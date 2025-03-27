package robo.standart;

import ambiente.Ambiente;

public class Robo
{
    private String nome;
    private int posicaoX;
    private int posicaoY;
    /**
     * NORTE, SUL, LESTE, OESTE
     */
    private String direcao;
    private Ambiente ambiente_atual;

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
     * Retorna o nome do robo.
     * @return
     */
    public String get_nome(){
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
     * @param novo_nome Novo nome a atualizar.
     */
    public String set_nome(String novo_nome){
        this.nome = novo_nome;
        return ("Novo nome alterado para:"+this.nome);
    }


    /**
     * Retorna direcao robo.
     * @return
     */
    public String get_direcao(){
        return this.direcao;
    }


    /**
     * Ajusta nova direcao robo.
     * @param nova_direcao Nova direcao a ser ajustada.
     * @return
     */
    public String set_direcao(String nova_direcao){
        this.direcao = nova_direcao;
        return ("Nova direcao setada para:"+this.direcao);
    }


    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual.
     * @param deltaX
     * @param deltaY
     */
    public void mover(int deltaX, int deltaY) { 
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }


    /**
     * @return vetor com duas posicoes, que sao (x,y) do robo.
     */
    public int[] exibir_posicao(){
        return new int[]{this.posicaoX, this.posicaoY};
    }

 

}
