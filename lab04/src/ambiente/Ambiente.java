package ambiente;

import constantes.TipoEntidade;
import constantes.TipoObstaculo;
import interfaces.Entidade;
import java.util.ArrayList;
import java.util.stream.Collectors;
import robo.standart.Robo;

public class Ambiente {

    private int largura;
    private int comprimento;
    private int altura;

    private int quantidade_robos_ativos;
    private int quantidade_obstaculos;

    private final ArrayList<Entidade> entidades;

    private TipoEntidade[][][] mapa; // [largura][comprimento][altura]
    

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

        entidades = new ArrayList<>();

        inicializarMapa(largura, comprimento, altura);
    }

    /**
     * Inicializa um mapa do ambiente vazio
     */
    private void inicializarMapa(int largura, int comprimento, int altura){
        mapa = new TipoEntidade[largura][comprimento][altura];

        for(int i = 0; i < largura; i++){
            for(int j = 0; j < comprimento; j++){
                for(int k = 0; k < altura; k++){
                    mapa[i][j][k] = TipoEntidade.VAZIO;
                }
            }
        }
    }

    private void adicionarNoMapa(Entidade ent){
        if(ent != null){
            if(ent instanceof Obstaculo){
                Obstaculo obst = (Obstaculo) ent;
    
                for(int z = 0; z < obst.getZ(); z++){
                    for(int y = obst.getY(); y <= obst.getPontoMaior()[1]; y++){
                        for(int x = obst.getX(); x <= obst.getPontoMaior()[0]; x++){
                            mapa[x][y][z] = ent.getTipo();
                        }
                    }
                }
    
            } else {
                mapa[ent.getX()][ent.getY()][ent.getZ()] = ent.getTipo();
            }
        }
    }

    /**
     * Adiciona um objeto que implementa Entidade na lista de entidades no ambiente <p/>
     * Returns: <p/>
     * true se não contém o elemento
     */
    public boolean  adicionarEntidade(Entidade ent){
        if(!entidades.contains(ent)){
            entidades.add(ent);

            adicionarNoMapa(ent);
            return true;
        }

        return false;
    }

    /**
     * Remove um objeto que implementa Entidade na lista de entidades no ambiente <p/>
     * Returns: <p/>
     * true se contém o elemento
     */
    public boolean removerEntidade(Entidade ent){
        if(entidades.contains(ent)){
            mapa[ent.getX()][ent.getY()][ent.getZ()] = TipoEntidade.VAZIO;

            entidades.remove(ent);

            return true;
        }

        return false;
    }

    /**
     * Verifica se a coordenada esta dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y, int z) {
        return (x <this.largura &&x >=0)&&(y<this.comprimento && y>=0)&&(z>=0 &&z<this.altura);
    }

    /**
     * Verifica se o ponto está ocupado
     */
    public boolean estaOcupado(int x, int y, int z){
        return (mapa[x][y][z] != TipoEntidade.VAZIO);
    }

    /**
     * Adiciona um objeto da classe Robo na lista de robos no ambiente
     */
    public void adicionarRobo(Robo robo){
        if(!dentroDosLimites(robo.getX(), robo.getY(), robo.getZ()))
            throw new IllegalArgumentException("O robo '"+ robo.getNome()+"' não pode ser adicionado ao ambiente pois esta fora dos limites do ambiente.");

        //Detectar se está ocupado
        if(estaOcupado(robo.getX(), robo.getY(), robo.getZ())){
            throw new IllegalArgumentException("O robo '"+ robo.getNome()+"' não pode ser adicionado ao ambiente pois a posicao ja esta ocupada.");
        }

        if(adicionarEntidade(robo)){
            this.quantidade_robos_ativos++;
            robo.set_ambiente(this);
        }
    }

    /**
     * Remove um robo específico da lista de robos no ambiente
     */
    public void removerRobo(Robo robo){
        if(removerEntidade(robo)){ //tenta remover o robo da lista, se o robo estava na lista => true
            robo.set_ambiente(null); //Remove o ambiente da instancia do Robo.
            this.quantidade_robos_ativos--;
        }
    }

    /**
     * Adiciona um obstáculo puntiforme com altura padrão no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX, int posY, TipoObstaculo tipoObstaculo){
        Obstaculo obstaculo = new Obstaculo(posX, posY, tipoObstaculo);

        //verificar se o obstáculo novo não se sobrepõe em outro já existente ou um robo
        for(Entidade ent : entidades){
            if(ent instanceof Obstaculo){
                if(((Obstaculo) ent).estaDentro(posX, posY)){
                    throw new IllegalArgumentException("Posicao ja tem obstaculo");
                }

            } else if(ent instanceof Robo){
                if(obstaculo.estaDentro(((Robo) ent).get_posicao()[0], ((Robo) ent).get_posicao()[1])){
                    throw new IllegalArgumentException("Posicao ja tem robo");
                }
            }
        }

        //Adicionar obstáculo
        if(dentroDosLimites(obstaculo.getX(), obstaculo.getY(), obstaculo.getZ())) {
            if(adicionarEntidade(obstaculo)){
                this.quantidade_obstaculos++;
            }
        } else{
            throw new IllegalArgumentException("Posição inválida");
        }
    }

    /**
     * Adiciona um obstáculo extenso com altura padrão no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX1, int posY1, int posX2, int posY2, TipoObstaculo tipoObstaculo){
        Obstaculo obstaculo = new Obstaculo(posX1, posY1, posX2, posY2, tipoObstaculo);

        //verificar se o obstáculo novo não se sobrepõe em outro já existente ou um robo
        for(Entidade ent : entidades){
            if(ent instanceof Obstaculo){
                if(((Obstaculo )ent).getPontoMenor()[0] > obstaculo.getPontoMaior()[0] || obstaculo.getPontoMenor()[0] > ((Obstaculo )ent).getPontoMaior()[0]){
                    //Verifica se um obstáculo está totalmente a direita ou esquerda do outro
                    continue;
                }
                if (((Obstaculo )ent).getPontoMenor()[1] > obstaculo.getPontoMaior()[1] || obstaculo.getPontoMenor()[1] > ((Obstaculo )ent).getPontoMaior()[1]) {
                    //Verifica se um obstáculo está totalmente acima ou abaixo do outro
                    continue;
                }
    
                throw new IllegalArgumentException("Posicao ja tem obstaculo");

            } else if(ent instanceof Robo){
                if(obstaculo.estaDentro(((Robo) ent).get_posicao()[0], ((Robo) ent).get_posicao()[1])){
                    throw new IllegalArgumentException("Posicao ja tem robo");
                }
            }
        }

        //Adicionar obstáculo
        if(
            dentroDosLimites(obstaculo.getPontoMenor()[0], obstaculo.getPontoMenor()[1], 0) &&
            dentroDosLimites(obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1], obstaculo.getZ())
        ) {
            if(adicionarEntidade(obstaculo)){
                this.quantidade_obstaculos++;
            }
        } else{
            throw new IllegalArgumentException("Posição inválida");
        }
    }

    /**
     * Adiciona um obstáculo extenso com altura personalizada no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX1, int posY1, int posX2, int posY2, int altura, TipoObstaculo tipoObstaculo){
        Obstaculo obstaculo = new Obstaculo(posX1, posY1, posX2, posY2, altura, tipoObstaculo);

        //verificar se o obstáculo novo não se sobrepõe em outro já existente ou um robo
        for(Entidade ent : entidades){
            if(ent instanceof Obstaculo){
                if(((Obstaculo )ent).getPontoMenor()[0] > obstaculo.getPontoMaior()[0] || obstaculo.getPontoMenor()[0] > ((Obstaculo )ent).getPontoMaior()[0]){
                    //Verifica se um obstáculo está totalmente a direita ou esquerda do outro
                    continue;
                }
                if (((Obstaculo )ent).getPontoMenor()[1] > obstaculo.getPontoMaior()[1] || obstaculo.getPontoMenor()[1] > ((Obstaculo )ent).getPontoMaior()[1]) {
                    //Verifica se um obstáculo está totalmente acima ou abaixo do outro
                    continue;
                }
    
                throw new IllegalArgumentException("Posicao ja tem obstaculo");

            } else if(ent instanceof Robo){
                if(obstaculo.estaDentro(((Robo) ent).get_posicao()[0], ((Robo) ent).get_posicao()[1])){
                    throw new IllegalArgumentException("Posicao ja tem robo");
                }
            }
        }

        //Adicionar obstáculo
        if(
            dentroDosLimites(obstaculo.getPontoMenor()[0], obstaculo.getPontoMenor()[1], 0) &&
            dentroDosLimites(obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1], obstaculo.getZ())
        ) {
            if(adicionarEntidade(obstaculo)){
                this.quantidade_obstaculos++;
            }
        } else{
            throw new IllegalArgumentException("Posição inválida");
        }
    }

    /**
     * Remove um obstáculo na posição introduzida
     */
    public void removerObstaculo(int posX, int posY, int posz){
        for (Entidade ent : entidades) {
            if(ent instanceof Obstaculo){
                if(
                (((Obstaculo) ent).getPontoMenor()[0] <= posX && ((Obstaculo) ent).getPontoMaior()[0] >= posX) &&
                (((Obstaculo) ent).getPontoMenor()[1] <= posY && ((Obstaculo) ent).getPontoMaior()[1] >= posY) &&
                (((Obstaculo) ent).getZ() > posz)
             ) {
                removerEntidade(ent);
                this.quantidade_obstaculos--;
            
                break;
            }
            }
            
        }
    }

    public void moverEntidade(Entidade ent, int novoX, int novoY, int novoZ){
        if(!estaOcupado(novoX, novoY, novoZ)){
            mapa[ent.getX()][ent.getY()][ent.getZ()] = TipoEntidade.VAZIO;

            mapa[novoX][novoY][novoZ] = ent.getTipo();
        }
    }

    //TODO ver o que significa
    public void executarSensores(){}
    public void verificarColisoes(){}

    public void visualizarAmbiente(int z){
        for(int y = 0; y < comprimento; y++){
            for(int x = 0; x < largura; x++){
                System.out.print(mapa[x][y][z].getRepresentacao());
            }
            System.out.println("");
        }
        System.out.println("");
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
    public int get_largura() {
        return largura;
    }

    /**
     * define o valor da variável largura
     */
    public void set_largura(int largura) {
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
    public int get_altura() {
        return altura;
    }

    /**
     * define o valor da variável altura
     */
    public void set_altura(int altura) {
        this.altura = altura;
    }

    /**
     * Retorna a lista de entidades
     */
    public ArrayList<Entidade> getEntidades() {
        return entidades;
    }

    /**
     * Retorna a lista de Robo
     */
    public ArrayList<Robo> getListaRobos(){
        return entidades.stream() //Aumenta a quantidade de operações possíveis com uma ArrayList
            .filter(roboAtual -> roboAtual instanceof Robo) //Filtra os elemento para pegar apenas os Robo
            .map(roboAtual -> (Robo) roboAtual) //Cast dos elementos filtrados
            .collect(Collectors.toCollection(ArrayList::new)); //Volta a ser um ArrayList
    }
    
    /**
     * Retorna a lista de Obstaculo
     */
    public ArrayList<Obstaculo> getObstaculos(){
        return entidades.stream() //Aumenta a quantidade de operações possíveis com uma ArrayList
            .filter(obstaculoAtual -> obstaculoAtual instanceof Obstaculo) //Filtra os elemento para pegar apenas os Obstaculo
            .map(obstaculoAtual -> (Obstaculo) obstaculoAtual) //Cast dos elementos filtrados
            .collect(Collectors.toCollection(ArrayList::new)); //Volta a ser um ArrayList
    }

}
