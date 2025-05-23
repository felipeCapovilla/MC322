package robo.terrestre.pedestre;

import ambiente.Obstaculo;
import constantes.*;
import exceptions.*;
import interfaces.Destructible;
import java.util.Random;
import robo.terrestre.standart.*;

public class RoboPedestre extends RoboTerrestre implements Destructible{
    
    private int peso;

    //Variáveis da tarefa
    private int numCaixasTotal = 0;
    private int numCaixasPegas = 0;

    //Variáveis da interface
    private final int vidaMax = 5;
    private int vida;

    //private ArrayList<Obstaculo> caixasNoMapa = new ArrayList<>();


    public RoboPedestre(String nome,int posicaoX, int posicaoY, Bussola direcao, int velocidadeMaxima){
        super (nome,posicaoX, posicaoY, direcao,velocidadeMaxima);
        peso = 0;
        vida = vidaMax;
    }

    /**
     * Overload <p>
     * Move o robo pedestre em dois modos:
     * <p>
     * correr-> até velocidade máxima <p>
     * caminhar-> até 0.6 * velocidade máxima
     * @param correr correr(true) ou caminhar(false)
     */
    public void mover(boolean correr, int deltaX, int deltaY) throws ZeroLifePointsException, NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException{
        if(vida <= 0){
            throw new ZeroLifePointsException();
        }

        //Decide para correr ou caminhar
        double fator_movimento = (correr)? 1 : 0.6;

        //fator do peso faz andar mais devagar
        //peso = 1 -> 1 * velocidade
        //peso = 10 -> 0,5 * velocidade
        deltaX = (int)(deltaX / ((peso/10)+1));
        deltaY = (int)(deltaY / ((peso/10)+1));

        //Verificar velocidade atual
        double velAtual = Math.sqrt((deltaX*deltaX + deltaY*deltaY));

        //Não mover mais rapido que a velocidade maxima
        if(velAtual >= (getVelocidadeMaxima()*fator_movimento)){
            deltaX = (int)((deltaX/velAtual) * (getVelocidadeMaxima()*fator_movimento));
            deltaY = (int)((deltaY/velAtual) * (getVelocidadeMaxima()*fator_movimento));

        }

        //Tarefa
        if(isTarefaAtiva() && get_ambiente().dentroDosLimites(getX()+deltaX, getY()+deltaY, getZ())){
            if(get_ambiente().getMapa()[getX()+deltaX][getY()+deltaY][getZ()] == TipoEntidade.OBSTACULO){
                for (Obstaculo obst : get_ambiente().getObstaculos()) {
                    if(obst.estaDentro(getX()+deltaX,getY()+deltaY,getZ())){
                        System.err.println("Caixa coletada!");
                        numCaixasPegas++;
                        setPeso( getPeso() + 5);

                        get_ambiente().removerEntidade(obst);
                    }
                }
            }

            if(numCaixasPegas == numCaixasTotal){
                finalizarTarefa();
            }
        }
        
        //Movimentação
        super.mover(deltaX, deltaY);
    }

    /**
     * Repara 2 ponto de vida para o robo
     */
    public void reparar() throws RoboDesligadoException{
        if(!isLigado()){
            throw new RoboDesligadoException();
        }

        int curaVida = 2;

        repairLife(curaVida);
    }

    //Tarefa
    @Override
    public void executarTarefa(){
        setTarefaAtiva(true);
        Random random = new Random();

        numCaixasTotal = random.nextInt(7) + 1;
        numCaixasPegas = 0;

        int caiX;
        int caiY;
        boolean running;

        for(int i = 0; i<numCaixasTotal; i++){
            do { 
                try {
                    caiX = random.nextInt(get_ambiente().get_largura());
                    caiY = random.nextInt(get_ambiente().get_comprimento());

                    get_ambiente().adicionarObstaculo(caiX, caiY, TipoObstaculo.CAIXA);

                    running = false;
                } catch (ColisaoException | PointOutOfMapException e) {
                    running = true;
                }
            } while (running);
        }

        System.out.println("Tarefa Iniciada!");
        detectarCaixas();
    }

    public void finalizarTarefa(){
        System.out.println("Tarefa Finalizada!");
        setTarefaAtiva(false);
        setPeso(0);
    }

    private void detectarCaixas(){
        for(Obstaculo obst : get_ambiente().getObstaculos()){
            if(obst.getTipoObstaculo() == TipoObstaculo.CAIXA){
                System.out.println(String.format("CAIXA EM: (%d,%d)", obst.getX(), obst.getY()));
            }
        }
    }



    /**
     * define o valor da variável largura <p>
     * <p>
     * Fator do peso faz andar mais devagar <p>
     * peso = 1 -> 1 * velocidade <p>
     * peso = 10 -> 0,5 * velocidade <p>
     * ...
     */
    public void setPeso(int peso) {
        this.peso = Math.max(peso, 0);
    }

    /**
     * Retorna o valor da variável peso
     */
    public int getPeso() {
        return peso;
    }

    public int getCaixasTotal() {
        return numCaixasTotal;
    }

    public int getCaixasPegas() {
        return numCaixasPegas;
    }

    //Interface
    @Override
    public void takeDamage(int damage) {
        vida = Math.max(0, vida-damage);
    }

    @Override
    public void repairLife(int repair) {
        vida = Math.min(vidaMax, vida + repair);
    }

    @Override
    public int getVida(){
        return vida;
    }

}
