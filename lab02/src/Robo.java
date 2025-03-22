public class Robo
{
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao;

    /**
     * Construtor da classe Robo.
     */
    public Robo(String nome, int posicaoX, int posicaoY, String direcao) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.direcao = direcao;
    }


    /**
     * Retorna o nome do robo.
     * @return
     */
    public String get_nome(){
        return this.nome;
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
    protected void mover(int deltaX, int deltaY) { //Usar um polimorfismo em mover.
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }


    /**
     * @return vetor com duas posicoes, que sao (x,y) do robo.
     */
    protected int[] get_posicao(){
        return new int[]{this.posicaoX, this.posicaoY};
    }

    //FAZER CLASSE IDENTIFICAR_OBSTACULOS()

}
