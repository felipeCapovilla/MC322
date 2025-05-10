package ambiente;

import constantes.TipoObstaculo;
import java.util.ArrayList;
import java.util.Arrays;
import robo.standart.Robo;

public class Ambiente {

    private int largura;
    private int comprimento;
    private int altura;
    private int quantidade_robos_ativos;
    private int quantidade_obstaculos;
    private final ArrayList<Robo> listaRobos;
    private final ArrayList<Obstaculo> obstaculos;
    

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

        //Detectar se há obstaculos na posição
        for(int i=0;i<this.get_quantidade_obstaculos();i++){
            if(!getObstaculos().get(i).Passavel() && getObstaculos().get(i).estaDentro(robo.get_posicao()[0], robo.get_posicao()[1]) && obstaculos.get(i).getAltura() > robo.get_altitude()){
                throw new IllegalArgumentException("O robo '"+ robo.getNome()+"' não pode ser adicionado ao ambiente pois a posicao ja esta ocupada.");
            }
        }

        //Detectar se há robos na posição
        for(int i=0;i<this.get_robos_ativos();i++){
            if(Arrays.equals(getListaRobos().get(i).get_posicao(), robo.get_posicao()) && listaRobos.get(i).get_altitude() == robo.get_altitude()){
                throw new IllegalArgumentException("O robo '"+ robo.getNome()+"' não pode ser adicionado ao ambiente pois a posicao ja esta ocupada.");
            }
        }


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
     * Adiciona um obstáculo puntiforme com altura padrão no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX, int posY, TipoObstaculo tipoObstaculo){
        Obstaculo obstaculo = new Obstaculo(posX, posY, tipoObstaculo);

        //verificar se o obstáculo novo não se sobrepõe em outro já existente
        for(Obstaculo obst : obstaculos){
            if(obst.estaDentro(posX, posY)){
                throw new IllegalArgumentException("Posicao ja tem obstaculo");
            }
        }

        //Verifica se há robo no espaço
        for(Robo rob: listaRobos){
            if(obstaculo.estaDentro(rob.get_posicao()[0], rob.get_posicao()[1])){
                throw new IllegalArgumentException("Posicao ja tem robo");
            }
        }

        //Adicionar obstáculo
        if(
            dentroDosLimites(obstaculo.getPontoMenor()[0], obstaculo.getPontoMenor()[1], obstaculo.getAltura())
        ) {
            obstaculos.add(obstaculo);
            this.quantidade_obstaculos++;
        } else{
            throw new IllegalArgumentException("Posição inválida");
        }
    }

    /**
     * Adiciona um obstáculo extenso com altura padrão no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX1, int posY1, int posX2, int posY2, TipoObstaculo tipoObstaculo){
        Obstaculo obstaculo = new Obstaculo(posX1, posY1, posX2, posY2, tipoObstaculo);

        //verificar se o obstáculo novo não se sobrepõe em outro já existente
        for(Obstaculo obst : obstaculos){
            if(obst.getPontoMenor()[0] > obstaculo.getPontoMaior()[0] || obstaculo.getPontoMenor()[0] > obst.getPontoMaior()[0]){
                //Verifica se um obstáculo está totalmente a direita ou esquerda do outro
                continue;
            }
            if (obst.getPontoMenor()[1] > obstaculo.getPontoMaior()[1] || obstaculo.getPontoMenor()[1] > obst.getPontoMaior()[1]) {
                //Verifica se um obstáculo está totalmente acima ou abaixo do outro
                continue;
            }

            throw new IllegalArgumentException("Posicao ja tem obstaculo");
        }

        //Verifica se há robo no espaço
        for(Robo rob: listaRobos){
            if(obstaculo.estaDentro(rob.get_posicao()[0], rob.get_posicao()[1])){
                throw new IllegalArgumentException("Posicao ja tem robo");
            }
        }

        //Adicionar obstáculo
        if(
            dentroDosLimites(obstaculo.getPontoMenor()[0], obstaculo.getPontoMenor()[1], 0) &&
            dentroDosLimites(obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1], obstaculo.getAltura())
        ) {
            obstaculos.add(obstaculo);
            this.quantidade_obstaculos++;
        } else{
            throw new IllegalArgumentException("Posição inválida");
        }
    }

    /**
     * Adiciona um obstáculo extenso com altura personalizada no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX1, int posY1, int posX2, int posY2, int altura, TipoObstaculo tipoObstaculo){
        Obstaculo obstaculo = new Obstaculo(posX1, posY1, posX2, posY2, altura, tipoObstaculo);

        //verificar se o obstáculo novo não se sobrepõe em outro já existente
        for(Obstaculo obst : obstaculos){
            if(obst.getPontoMenor()[0] > obstaculo.getPontoMaior()[0] || obstaculo.getPontoMenor()[0] > obst.getPontoMaior()[0]){
                //Verifica se um obstáculo está totalmente a direita ou esquerda do outro
                continue;
            }
            if (obst.getPontoMenor()[1] > obstaculo.getPontoMaior()[1] || obstaculo.getPontoMenor()[1] > obst.getPontoMaior()[1]) {
                //Verifica se um obstáculo está totalmente acima ou abaixo do outro
                continue;
            }

            throw new IllegalArgumentException("Posicao ja tem obstaculo");
        }

        //Verifica se há robo no espaço
        for(Robo rob: listaRobos){
            if(obstaculo.estaDentro(rob.get_posicao()[0], rob.get_posicao()[1])){
                throw new IllegalArgumentException("Posicao ja tem robo");
            }
        }

        //Adicionar obstáculo
        if(
            dentroDosLimites(obstaculo.getPontoMenor()[0], obstaculo.getPontoMenor()[1], 0) &&
            dentroDosLimites(obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1], obstaculo.getAltura())
         ) {
            obstaculos.add(obstaculo);
            this.quantidade_obstaculos++;
        } else{
            throw new IllegalArgumentException("Posição inválida");
        }
    }

    /**
     * Remove um obstáculo na posição introduzida
     */
    public void removerObstaculo(int posX, int posY){
        for (Obstaculo obst : obstaculos) {
            if(
                (obst.getPontoMenor()[0] <= posX && obst.getPontoMaior()[0] >= posX) &&
                (obst.getPontoMenor()[1] <= posY && obst.getPontoMaior()[1] >= posY)
             ) {
                obstaculos.remove(obst);
                this.quantidade_obstaculos--;
            
                break;
            }
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
     * Retorna o valor da variável largura (X)
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
     * Retorna o valor da variável comprimento (Y)
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
     * Retorna o valor da variável altura (Z)
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
    public ArrayList<Obstaculo> getObstaculos() {
        return obstaculos;
    }
    

}
