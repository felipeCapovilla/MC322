package ambiente;

import java.util.ArrayList;
import robo.standart.Robo;

public class Ambiente {

    private int largura;
    private int comprimento;
    private int altura;
    private int quantidade_robos_ativos;
    private int quantidade_obstaculos;
    private final ArrayList<Robo> listaRobos;
    private final ArrayList<int[]> obstaculos;
    

    /**
     * Cria um plano cartesiano. A extremidade esquerda inferior do ambiente se inicia em (0,0).
     * @param largura X -> [0, largura-1]
     * @param comprimento Y -> [0, comprimento-1]
     * @param altura Z -> [0, altura-1]
     */
    public Ambiente(int largura, int comprimento, int altura) {
        this.largura = largura;
        this.comprimento = comprimento;
        this.altura = altura;
        this.quantidade_robos_ativos =0;
        this.quantidade_obstaculos =0;
        listaRobos = new ArrayList<>();
        obstaculos = new ArrayList<>();

    }

    /**
     * Verifica se a coordenada esta dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y, int z) {
        return (x <this.largura &&x >=0)&&(y<this.comprimento && y>=0)&&(z>=0 &&z<this.altura);
    }

    /**
     * Adiciona um objeto da classe Robo na lista de robos no ambiente
     */
    public void adicionarRobo(Robo robo){
        if(robo.get_posicao()[0] > this.largura || robo.get_posicao()[1] > this.comprimento)
            throw new IllegalArgumentException("O robo '"+ robo.getNome()+"' não pode ser adicionado ao ambiente pois esta fora dos limites do ambiente.");

        if(!listaRobos.contains(robo)){ //evitar duplicatas
            listaRobos.add(robo);
            robo.set_ambiente(this); //Insere o objeto ambiente como ambiente do robo.
            this.quantidade_robos_ativos++;
        }
    }

    /**
     * Remove um robo específico da lista de robos no ambiente
     */
    public void removerRobo(Robo robo){
        if(listaRobos.remove(robo)){ //tenta remover o robo da lista, se o robo estava na lista => true
            robo.set_ambiente(null); //Remove o ambiente da instancia do Robo.
            this.quantidade_robos_ativos--;
        }
    }

    /**
     * Adiciona um obstáculo no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX, int posY){
        int[] coordenada = {posX, posY};
        if(!obstaculos.contains(coordenada)){ //evitar duplicatas
            if(dentroDosLimites(posX, posY,0)){
                obstaculos.add(coordenada);
                this.quantidade_obstaculos++;
            } else {
                throw new IllegalArgumentException(String.format("Posição inválida (%d,%d)", posX, posY));
            }
            
        }
    }

    /**
     * Remove um obstáculo na posição introduzida
     */
    public void removerObstaculo(int posX, int posY){
        int[] coordenada = {posX, posY};
        if(obstaculos.remove(coordenada)){ //tenta remover a coordenada da lista, se ela estava na lista => true
            this.quantidade_obstaculos--;
        }
    }


    //GETs e SETs
    
    /**
     * Retorna o valor da variável quantidade_obstaculos
     */
    public int get_quantidade_obstaculos(){
        return this.quantidade_obstaculos;
    }

    /**
     * Retorna o valor da variável robos_ativos
     */
    public int get_robos_ativos(){
        return this.quantidade_robos_ativos;
    }

    /**
     * Retorna o valor da variável altura
     */
    public int getLargura() {
        return largura;
    }

    /**
     * define o valor da variável largura
     */
    public void setLargura(int largura) {
        this.largura = largura;
    }

    /**
     * Retorna o valor da variável comprimento
     */
    public int get_comprimento(){
        return this.comprimento;
    }

    /**
     * define o valor da variável comprimento
     */
    public void set_comprimento(int novo_comprimento){
        this.comprimento = novo_comprimento;
    }

    /**
     * Retorna o valor da variável altura
     */
    public int getAltura() {
        return altura;
    }

    /**
     * define o valor da variável altura
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * Retorna a variável listaRobos
     */
    public ArrayList<Robo> getListaRobos() {
        return listaRobos;
    }

    /**
     * Retorna a lista de obstaculos
     */
    public ArrayList<int[]> getObstaculos() {
        return obstaculos;
    }
    

}
