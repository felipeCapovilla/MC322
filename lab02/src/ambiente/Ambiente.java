package ambiente;

import java.util.ArrayList;

import robo.standart.Robo;

public class Ambiente {

    private int largura;
    private int altura;
    private final ArrayList<Robo> listaRobos;
    private final ArrayList<int[]> obstaculos;

    /**
     * Cria um plano cartesiano. A extremidade esquerda inferior do ambiente se inicia em (0,0).
     * @param largura X -> [0, largura-1]
     * @param altura Y -> [0, altura-1]
     */
    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        listaRobos = new ArrayList<>();
        obstaculos = new ArrayList<>();

    }

    /**
     * Verifica se o robo esta dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y) {
        return (x <largura &&x >=0)&&(y<altura && y>=0);
    }

    /**
     * Adiciona um objeto da classe Robo na lista de robos no ambiente
     */
    public void adicionarRobo(Robo robo){
        if(!listaRobos.contains(robo)){
            listaRobos.add(robo);
            robo.set_ambiente(this); //Insere o objeto ambiente como ambiente do robo.
        }
    }

    /**
     * Remove um robo específico da lista de robos no ambiente
     */
    public void removerRobo(Robo robo){
        listaRobos.remove(robo);
        robo.set_ambiente(null); //Remove o ambiente da instancia do Robo.
    }

    /**
     * Adiciona um obstáculo no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX, int posY){
        int[] coordenada = {posX, posY};
        if(!obstaculos.contains(coordenada)){
            if(dentroDosLimites(posX, posY)){
                obstaculos.add(coordenada);
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
    }



    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
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
