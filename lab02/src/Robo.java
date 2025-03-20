public class Robo
{
    String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao;

    public Robo(String nome, int posicaoX, int posicaoY, String direcao) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.direcao = direcao;
        //Construtor da classe Robo.
    }

    public void mover(int deltaX, int deltaY) {
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
        //Adiciona a variacao das coordenadas no valor da coordenada atual.
    }

    public int[] exibirPosicao(){
        return new int[]{this.posicaoX, this.posicaoY};
        //Retorna vetor com duas posicoes, que sao (x,y) do robo.
    }

}
