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
     * @param altura Y -> [0, altura-1]
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
     * Verifica se o robo esta dentro do ambiente
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

        if(!listaRobos.contains(robo)){
            listaRobos.add(robo);
            robo.set_ambiente(this); //Insere o objeto ambiente como ambiente do robo.
            this.quantidade_robos_ativos++;
        }
    }

    /**
     * Remove um robo específico da lista de robos no ambiente
     */
    public void removerRobo(Robo robo){
        listaRobos.remove(robo);
        robo.set_ambiente(null); //Remove o ambiente da instancia do Robo.
        this.quantidade_robos_ativos--;
    }

    /**
     * Adiciona um obstáculo no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX, int posY){
        int[] coordenada = {posX, posY};
        if(!obstaculos.contains(coordenada)){
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
        obstaculos.remove(coordenada);
        this.quantidade_obstaculos--;
    }

    public int get_quantidade_obstaculos(){
        return this.quantidade_obstaculos;
    }

    public int get_robos_ativos(){
        return this.quantidade_robos_ativos;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int get_comprimento(){
        return this.comprimento;
    }

    public void set_comprimento(int novo_comprimento){
        this.comprimento = novo_comprimento;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public ArrayList<Robo> getListaRobos() {
        return listaRobos;
    }

    public ArrayList<int[]> getObstaculos() {
        return obstaculos;
    }
    

}
