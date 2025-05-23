package robo.terrestre.veiculo;

import constantes.*;
import exceptions.*;
import interfaces.*;
import java.util.Random;
import robo.standart.Robo;
import robo.terrestre.standart.RoboTerrestre;

public class RoboVeiculo extends RoboTerrestre implements Destructible, Attacker{
    private int passageiros;
    private final int passageiros_maximo;
    private int velocidade;

    //Variáveis da interface
    private final int vidaMax = 5;
    private int vida;
    private int dano = 1;

    //Variáveis da tarefa
    private int atkTotal = 0;
    private int atkSucesso = 0;

    
    public RoboVeiculo(String nome,int posicaoX, int posicaoY,Bussola direcao, int velocidadeMaxima, int passageiros_maximo){
        super (nome,posicaoX, posicaoY,direcao, velocidadeMaxima);
        this.passageiros_maximo = passageiros_maximo;
        passageiros = 0;
        velocidade = 0;
        vida = vidaMax;

    }

    /**
     * Muda a velocidade do veiculo
     * @param velocidade
     */
    public void mudarVelocidade(int velocidade) throws RoboDesligadoException{
        if(!isLigado()){
            throw new RoboDesligadoException();
        }

        if(velocidade < 0){
            this.velocidade = 0;
        } else if(velocidade > getVelocidadeMaxima()){
            this.velocidade = getVelocidadeMaxima();
        } else {
            this.velocidade = velocidade;    
        }
    }

    /**
     * Vira a direção do robo para direita ou esquerda em relação à direção atual
     * @param direita direita(true) ou esquerda(false)
     */
    public void virar(boolean direita) throws ZeroLifePointsException, RoboDesligadoException{
        if(!isLigado()){
            throw new RoboDesligadoException();
        }

        if(vida <= 0){
            throw new ZeroLifePointsException();
        }

        int index = getDirecao().getIndice();

        //direita(1) e esquerda(-1)
        int lado = (direita)? 1: -1;

        //movimento ciclico do index
        setDirecao(Bussola.values()[((index+lado)%4 + 4) % 4]);
    }

    /**
     * Overload <p>
     * Adiciona a velocidade no valor da coordenada atual, apenas no sentido da direção.
     */
    public void mover(boolean frente) throws ZeroLifePointsException, NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException{
        if(vida <= 0){
            throw new ZeroLifePointsException();
        }


        int marcha = (frente)? 1:-1;
        int indecDirecao = getDirecao().getIndice();

        switch (indecDirecao) {
            case 0: //Norte 
                mover(0, velocidade * marcha);
                break;

            case 2: //Sul 
                mover(0, -(velocidade * marcha));
                break;

            case 1: //Leste 
                mover(velocidade * marcha,0);
                break;

            case 3: //Oeste 
                mover(-(velocidade * marcha), 0);
                break;
                
            default: 
                throw new AssertionError("Direção inválida");
        }
    }

    /**
     * Diminui o numero de passageiros
     * @param num_passageiros quantidade de passageiros saindo
     */
    public void passageirosSair(int num_passageiros){
        passageiros = Math.max(0, passageiros-num_passageiros);//não sai mais passageiros que estão no veículo
    }

    /**
     * Aumenta o numero de passageiros
     * @param num_passageiros quantidade de passageiros entrando
     */
    public void passageirosEntrar(int num_passageiros){
        passageiros = Math.min(passageiros_maximo, passageiros+num_passageiros); //não entra mais passageiros que a capacidade máxima
    }

    /**
     * Ataca um robo Destructble na direção atual <p/>
     * true = ataque com sucesso <p/>
     * false = atalho falhou
     */
    public boolean atacarFrente() throws NullPointerException, ZeroLifePointsException, RoboDesligadoException{
        if(get_ambiente() == null){
            throw new NullPointerException();
        }
        if(!isLigado()){
            throw new RoboDesligadoException();
        }
        if(vida <= 0){
            throw new ZeroLifePointsException();
        }

        int atkX = getX();
        int atkY = getY();

        switch (getDirecao()) {
            case Bussola.NORTE:
                atkY++;
                break;
            case Bussola.SUL:
                atkY--;
                break;
            case Bussola.LESTE:
                atkX++;
                break;
            case Bussola.OESTE:
                atkX--;
                break;
            default:
                throw new AssertionError("Direção inválida");
        }

        if(!get_ambiente().dentroDosLimites(atkX, atkY, 0)){
            return false;
        }

        if(get_ambiente().getMapa()[atkX][atkY][0] == TipoEntidade.ROBO){
            //Verifica se há um robo na área de ataque
            for (Robo robo : get_ambiente().getListaRobos()) {
                if(robo.getX() == atkX && robo.getY() == atkY && robo.getZ() == 0){
                    if(robo instanceof Destructible){
                        //Robo na área de ataque é destruível
                        atacar((Destructible) robo, dano);


                        //Verificar Tarefa
                        if(isTarefaAtiva()){
                            atkSucesso++;

                            if(atkSucesso == atkTotal){
                                finalizarTarefa();
                            }
                        }

                        return true;
                    } else {
                        //Robo na área de ataque não é destruível
                        break;
                    }
                }       
            }
        }

        return false;
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





    //Interfaces

    @Override
    public void atacar(Destructible ent, int damage) {
        ent.takeDamage(damage);
    }

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

    //Tarefa
    @Override
    public void executarTarefa(){
        setTarefaAtiva(true);
        Random random = new Random();

        atkTotal = random.nextInt(4) + 1;
        atkSucesso = 0;

        System.out.println("Tarefa Iniciada!");
        System.out.println(String.format("Realize %d Ataques com sucesso", atkTotal));
    }

    public void finalizarTarefa(){
        System.out.println("Tarefa Finalizada!");
        System.out.println("ATK damage +1");
        setTarefaAtiva(false);
        dano++;
    }





    //GETs e SETs

    /**
     * Retorna o valor da variável valocidade
     */
    public int getVelocidade() {
        return velocidade;
    }

    /**
     * Retorna o valor da variável passageiros
     */
    public int getPassageiros() {
        return passageiros;
    }

    /**
     * define o valor da variável passageiros
     */
    public void setPassageiros(int passageiros) {
        if(passageiros < 0){
            this.passageiros = 0;
        } else if(passageiros > passageiros_maximo){
            this.passageiros = passageiros_maximo;
        } else {
            this.passageiros = passageiros;    
        }
        
    }

    /**
     * Retorna a quantidade de ataques necessários para a tarefa
     */
    public int getAtkTotal() {
        return atkTotal;
    }

    /**
     * Retorna a quantidade de ataques com sucesso dados na tarefa
     */
    public int getAtkSucesso() {
        return atkSucesso;
    }

}
