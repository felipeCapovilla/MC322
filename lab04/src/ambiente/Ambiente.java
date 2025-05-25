package ambiente;

import constantes.TipoEntidade;
import constantes.TipoObstaculo;
import exceptions.*;
import interfaces.Comunicavel;
import interfaces.Entidade;
import java.util.ArrayList;
import java.util.stream.Collectors;

import central_comunicacao.CentralComunicacao;
import robo.standart.Robo;
import sensor.standart.Sensor;

public class Ambiente {

    private int largura;
    private int comprimento;
    private int altura;

    private int quantidade_robos_ativos;
    private int quantidade_obstaculos;

    private final ArrayList<Entidade> entidades;

    final CentralComunicacao comunicacao = new CentralComunicacao();

    /**
     * [X] [Y] [Z]
     */
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

    /**
     * Adiciona o caracter da entidade no mapa
     * @param ent entidade adicionada
     */
    private void adicionarNoMapa(Entidade ent) throws NullPointerException{
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
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Adiciona um objeto que implementa Entidade na lista de entidades no ambiente <p/>
     * Returns: <p/>
     * true se não contém o elemento
     */
    public boolean  adicionarEntidade(Entidade ent) throws NullPointerException{
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
        if(ent == null){
            return false;
        }


        if(entidades.contains(ent)){

            //Remove Obstáculos 1x1
            if(ent instanceof Obstaculo){
                for(int curZ = 0; curZ < ent.getZ(); curZ++){
                    mapa[ent.getX()][ent.getY()][curZ] = TipoEntidade.VAZIO;
                }
            } else {
                //Remove outras entidades
                mapa[ent.getX()][ent.getY()][ent.getZ()] = TipoEntidade.VAZIO;
            }
            

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
        //TODO ajustar para obstáculos passáveis
        return (mapa[x][y][z] != TipoEntidade.VAZIO);
    }

    /**
     * Adiciona um objeto da classe Robo na lista de robos no ambiente
     */
    public void adicionarRobo(Robo robo) throws NullPointerException, PointOutOfMapException, ColisaoException{
        //Parametro ponteiro null
        if(robo == null){
            throw new NullPointerException();
        }

        //Fora dos limites do mapa
        if(!dentroDosLimites(robo.getX(), robo.getY(), robo.getZ()))
            throw new PointOutOfMapException(String.format("%s (%d,%d,%d)", robo.getID(), robo.getX(), robo.getY(), robo.getZ()));

        //Detectar se está ocupado
        if(estaOcupado(robo.getX(), robo.getY(), robo.getZ())){
            throw new ColisaoException(String.format("%s (%d,%d,%d)", robo.getID(), robo.getX(), robo.getY(), robo.getZ()));
        }

        if(adicionarEntidade(robo)){
            this.quantidade_robos_ativos++;
            robo.set_ambiente(this);

            if(robo instanceof Comunicavel){
                ((Comunicavel) robo).set_CentralComunicao(comunicacao);
            }
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
    public void adicionarObstaculo(int posX, int posY, TipoObstaculo tipoObstaculo) throws ColisaoException, PointOutOfMapException{
        Obstaculo obstaculo = new Obstaculo(posX, posY, tipoObstaculo);

        //Verificar se esta dentro dos limites do embiente
        if(!dentroDosLimites(obstaculo.getX(), obstaculo.getY(), obstaculo.getZ())){
            throw new PointOutOfMapException(String.format("(%d,%d,%d)", obstaculo.getX(), obstaculo.getY(), obstaculo.getZ()));
        }



        //verificar se o obstáculo novo não se sobrepõe em outro já existente ou um robo
        for(Entidade ent : entidades){
            if(ent instanceof Obstaculo){
                if(((Obstaculo) ent).estaDentro(posX, posY, 0)){
                    Obstaculo obst = (Obstaculo) ent;
                    throw new ColisaoException(String.format("%s (%d,%d,0) to (%d,%d,%d)",obst.getTipo().toString(), obst.getX(), obst.getY(), obst.getPontoMaior()[0], obst.getPontoMaior()[1] ,obst.getZ()));
                }

            } else if(ent instanceof Robo){
                if(obstaculo.estaDentro(ent.getX(), ent.getY(),0)){
                    throw new ColisaoException(String.format("%s (%d,%d,%d)",((Robo) ent).getID(), ent.getX(), ent.getY(), ent.getZ()));
                }
            }
        }

        //Adicionar obstáculo
        if(adicionarEntidade(obstaculo)){
            this.quantidade_obstaculos++;
        }

    }

    /**
     * Adiciona um obstáculo extenso com altura padrão no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX1, int posY1, int posX2, int posY2, TipoObstaculo tipoObstaculo) throws ColisaoException, PointOutOfMapException{
        Obstaculo obstaculo = new Obstaculo(posX1, posY1, posX2, posY2, tipoObstaculo);

        //Verificar se esta dentro dos limites do embiente
        if(!(
            dentroDosLimites(obstaculo.getPontoMenor()[0], obstaculo.getPontoMenor()[1], 0) &&
            dentroDosLimites(obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1], obstaculo.getZ())
        )) {
            throw new PointOutOfMapException(String.format("(%d,%d,0) to (%d,%d,%d)", obstaculo.getX(), obstaculo.getY(), obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1],obstaculo.getZ()));
        } 


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
                
                Obstaculo obst = (Obstaculo )ent;
                throw new ColisaoException(String.format("%s (%d,%d,0) to (%d,%d,%d)",obst.getTipo().toString(), obst.getX(), obst.getY(), obst.getPontoMaior()[0], obst.getPontoMaior()[1] ,obst.getZ()));

            } else if(ent instanceof Robo){
                if(obstaculo.estaDentro(ent.getX(), ent.getY(), 0)){
                    throw new ColisaoException(String.format("%s (%d,%d,%d)",((Robo) ent).getID(), ent.getX(), ent.getY(), ent.getZ()));
                }
            }
        }

        //Adicionar obstáculo
        if(adicionarEntidade(obstaculo)){
            this.quantidade_obstaculos++;
        }
       
    }

    /**
     * Adiciona um obstáculo extenso com altura personalizada no ambiente na posição introduzida
     */
    public void adicionarObstaculo(int posX1, int posY1, int posX2, int posY2, int altura, TipoObstaculo tipoObstaculo) throws ColisaoException, PointOutOfMapException{
        Obstaculo obstaculo = new Obstaculo(posX1, posY1, posX2, posY2, altura, tipoObstaculo);

        //Verificar se esta dentro dos limites do embiente
        if(!(
            dentroDosLimites(obstaculo.getPontoMenor()[0], obstaculo.getPontoMenor()[1], 0) &&
            dentroDosLimites(obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1], obstaculo.getZ())
        )) {
            throw new PointOutOfMapException(String.format("(%d,%d,0) to (%d,%d,%d)", obstaculo.getX(), obstaculo.getY(), obstaculo.getPontoMaior()[0], obstaculo.getPontoMaior()[1],obstaculo.getZ()));
        } 


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
                
                Obstaculo obst = (Obstaculo )ent;
                throw new ColisaoException(String.format("%s (%d,%d,0) to (%d,%d,%d)",obst.getTipo().toString(), obst.getX(), obst.getY(), obst.getPontoMaior()[0], obst.getPontoMaior()[1] ,obst.getZ()));

            } else if(ent instanceof Robo){
                if(obstaculo.estaDentro(ent.getX(), ent.getY(), 0)){
                    throw new ColisaoException(String.format("%s (%d,%d,%d)",((Robo) ent).getID(), ent.getX(), ent.getY(), ent.getZ()));
                }
            }
        }

        //Adicionar obstáculo
        if(adicionarEntidade(obstaculo)){
            this.quantidade_obstaculos++;
        }
    }

    /**
     * Remove um obstáculo na posição introduzida
     */
    public void removerObstaculo(int posX, int posY, int posZ){
        for (Obstaculo obst : getObstaculos()) {
            
            if(obst.estaDentro(posX, posY, posZ)) {
                removerEntidade(obst);
                this.quantidade_obstaculos--;
            
                break;
            }
        }
    }

    /**
     * Move uma entidade no ambiente
     */
    public void moverEntidade(Entidade ent, int novoX, int novoY, int novoZ) throws ColisaoException, PointOutOfMapException{
        if(!dentroDosLimites(novoX, novoY, novoZ)){
            throw new PointOutOfMapException(String.format("(%d,%d,%d)", novoX, novoY, novoZ));
        }

        if(!estaOcupado(novoX, novoY, novoZ)){
            //TODO ajustar para obstáculos passáveis
            mapa[ent.getX()][ent.getY()][ent.getZ()] = TipoEntidade.VAZIO;

            mapa[novoX][novoY][novoZ] = ent.getTipo();
        } else {
            throw new ColisaoException(String.format("(%d,%d,%d)", novoX, novoY, novoZ));
        }
    }

    /**
     * Imprime todos os sensores que os robos LIGADOS possuem
     */
    public void executarSensores(){
        int robos_ligados = 0;

        for(Robo robo : getListaRobos()){
            if(robo.isLigado()){
                robos_ligados++;
                System.out.printf("->%S:\n", robo);
                Sensor sensortemp;
    
                sensortemp = robo.get_SensorEspacial();
                if(sensortemp != null){
                    System.out.printf("\t -Sensor Espacial %s\n", sensortemp.get_modelo());
                }
                
                sensortemp = robo.get_SensorAltitude();
                if(sensortemp != null){
                    System.out.printf("\t -Sensor Altitude %s\n", sensortemp.get_modelo());
                }
    
                sensortemp = robo.get_SensorTemperatura();
                if(sensortemp != null){
                    System.out.printf("\t -Sensor Temperatura %s\n", sensortemp.get_modelo());
                }
            }

        }
        if(robos_ligados == 0){
            System.out.println("Nao ha robos LIGADOS");
        }
    }

    /**
     * Verifica se há colisões entre as entidades
     */
    public void verificarColisoes(){
        for (Entidade entPrincipal : entidades) {
            for (Entidade entComparada : entidades) {

                if(!entPrincipal.equals(entComparada)){

                    if(entPrincipal instanceof Obstaculo){
                        if(entComparada instanceof Obstaculo){
                            if(((Obstaculo )entPrincipal).getPontoMenor()[0] > ((Obstaculo) entComparada).getPontoMaior()[0] || ((Obstaculo) entComparada).getPontoMenor()[0] > ((Obstaculo )entPrincipal).getPontoMaior()[0]){
                                //Verifica se um obstáculo está totalmente a direita ou esquerda do outro
                                continue;
                            }
                            if (((Obstaculo )entPrincipal).getPontoMenor()[1] > ((Obstaculo) entComparada).getPontoMaior()[1] || ((Obstaculo) entComparada).getPontoMenor()[1] > ((Obstaculo )entPrincipal).getPontoMaior()[1]) {
                                //Verifica se um obstáculo está totalmente acima ou abaixo do outro
                                continue;
                            }

                            Obstaculo obst = (Obstaculo )entPrincipal;
                            throw new ColisaoException(String.format("%s (%d,%d,0) to (%d,%d,%d)",obst.getTipo().toString(), obst.getX(), obst.getY(), obst.getPontoMaior()[0], obst.getPontoMaior()[1] ,obst.getZ()));

                        } else {
                            if(((Obstaculo) entPrincipal).estaDentro(entComparada.getX(), entComparada.getY(), entComparada.getZ())){

                                Obstaculo obst = (Obstaculo )entPrincipal;
                                throw new ColisaoException(String.format("%s (%d,%d,0) to (%d,%d,%d)",obst.getTipo().toString(), obst.getX(), obst.getY(), obst.getPontoMaior()[0], obst.getPontoMaior()[1] ,obst.getZ()));
                            }
                        }

                    } else {
                        if(entComparada instanceof Obstaculo){

                            if(((Obstaculo) entComparada).estaDentro(entPrincipal.getX(), entPrincipal.getY(), entPrincipal.getZ())){

                                throw new ColisaoException(String.format("%s (%d,%d,%d)", ((Robo) entPrincipal).getID(), entPrincipal.getX(), entPrincipal.getY(), entPrincipal.getZ()));
                            }

                        } else {
                            if((entPrincipal.getX() == entComparada.getX()) && (entPrincipal.getY() == entComparada.getY()) && (entPrincipal.getZ() == entComparada.getZ())){
                                throw new ColisaoException(String.format("%s (%d,%d,%d)", ((Robo) entPrincipal).getID(), entPrincipal.getX(), entPrincipal.getY(), entPrincipal.getZ()));

                            }
                        }

                    }
                }
            }
        }
    }


    /**
     * Imprime o mapa do ambiente na altura desejada
     * @param altura
     */
    public void visualizarAmbiente(int z) throws PointOutOfMapException{
        if(z < 0 || z >= altura){
            throw new PointOutOfMapException("altura = " + z);
        } else{

        }
        for(int y = comprimento-1; y >= 0; y--){
            for(int x = 0; x < largura; x++){
                System.out.print(mapa[x][y][z].getRepresentacao());
            }
            System.out.println("");
        }
        System.out.println("LEGENDA");
        System.out.println(TipoEntidade.VAZIO.getRepresentacao() + " - espaco vazio");
        System.out.println(TipoEntidade.OBSTACULO.getRepresentacao() + " - obstaculo");
        System.out.println(TipoEntidade.ROBO.getRepresentacao() + " - robo");


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

    public TipoEntidade[][][] getMapa() {
        return mapa;
    }

    /**
     * Retorna a central de comunicação
     */
    public CentralComunicacao getComunicacao() {
        return comunicacao;
    }

}
