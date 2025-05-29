package robo.aereo.turista;


import constantes.Bussola;
import exceptions.ColisaoException;
import exceptions.LowBatteryException;
import exceptions.PointOutOfMapException;
import exceptions.RoboDesligadoException;
import exceptions.SensorMissingException;
import exceptions.ValueOutOfBoundsException;
import java.util.Random;
import robo.aereo.standart.*;
public class RoboVoadorTurista extends RoboAereo{

    private int numero_passageiros;
    private final int capacidade_maxima;
    private String cidade_turistica;

    //Variávei tarefa
    private final int[] chegada = {-1,-1,-1};

    public RoboVoadorTurista(String nome,int posicaoX, int posicaoY, Bussola direcao, int altitude,int altitude_max,int capacidade_maxima) throws ValueOutOfBoundsException{
        super(nome,posicaoX,posicaoY,direcao,altitude,altitude_max);

        if(capacidade_maxima < 0){
            throw new ValueOutOfBoundsException("Capacidade máxima: "+ capacidade_maxima);
        }

        this.capacidade_maxima = capacidade_maxima;
        this.numero_passageiros = 0;
        this.cidade_turistica = "";

    }

    //Movimento detecta tarefa
    @Override
    public void subir(int metros) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException, SensorMissingException, ValueOutOfBoundsException{
        super.subir(metros);
        if(isTarefaAtiva()){
            if (getX() == chegada[0] && getY() == chegada[1] && getZ() == chegada[2]) {
                finalizarTarefa();
            }
        }
    }

    @Override
    public void descer(int metros) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException, SensorMissingException{
        super.descer(metros);
        if(isTarefaAtiva()){
            if (getX() == chegada[0] && getY() == chegada[1] && getZ() == chegada[2]) {
                finalizarTarefa();
            }
        }
    }

    @Override
    public void mover(int deltaX, int deltaY) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException {
        super.mover(deltaX, deltaY);
        if(isTarefaAtiva()){
            if (getX() == chegada[0] && getY() == chegada[1] && getZ() == chegada[2]) {
                finalizarTarefa();
            }
        }
    }

    //Tarefa
    /**
     * Inicia o turismo do robo.
     * @param numero_passageiros Indica a quantidade de passageiros atual presentes.
     * @param cidade_turistica Indica o destino do passeio turistico do Robo.
     */
    public void inciar_passeio(int numero_passageiros, String cidade_turistica) throws ValueOutOfBoundsException{
        if(numero_passageiros > this.capacidade_maxima){ //Verifica se a quantidade de passageiros e permitida.
            throw new ValueOutOfBoundsException("Capacidade acima da máxima: "+(numero_passageiros - this.capacidade_maxima));
        } else if(numero_passageiros < 0){
            throw new ValueOutOfBoundsException("Passageiros <0"); //Se nao: lança erro.
        }

        this.numero_passageiros = numero_passageiros;
        this.cidade_turistica = cidade_turistica;
        setTarefaAtiva(true);
    }

    /**
     * Finaliza a tarefa do robo
     */
    public void finalizarTarefa(){
        System.out.println("Tarefa Finalizada!");
        setTarefaAtiva(false);
    }

    @Override
    public void executarTarefa() {
        if (isTarefaAtiva()) {
            System.out.println("Tarefa Iniciada!");
            System.out.println("Sobrevoando " + this.cidade_turistica + " com " + this.numero_passageiros + " passageiros.");
            boolean running;
            Random random = new Random();

            do { 
                chegada[0] = random.nextInt(get_ambiente().get_largura());
                chegada[1] = random.nextInt(get_ambiente().get_comprimento());
                chegada[2] = random.nextInt(get_ambiente().get_altura());

                running = (get_ambiente().estaOcupado(chegada[0], chegada[1], chegada[2]) || chegada[2] >= get_altitude_max());
     
            } while (running);

            System.out.printf("Chegada em: (%d,%d,%d)\n", chegada[0], chegada[1], chegada[2]);

        } else {
            System.out.println("Aguardando passageiros para iniciar o passeio turístico.");
        }
    }



    //GETs e SETs

    /**
     * Retorna o destino turistico do robo.
     */
    public String get_destino(){
        return this.cidade_turistica;
    }

    /**
     * Retorna o numero de passageiros no robo.
     */
    public int get_numero_passageiros() {
        return numero_passageiros;
    }

    /**
     * define o valor da variável numero_passageiros
     */
    public void set_numero_passageiros(int numero_passageiros) {
        if(numero_passageiros < 0){
            this.numero_passageiros = 0;
        } else if (numero_passageiros > capacidade_maxima){
            this.numero_passageiros = capacidade_maxima;
        } else {
            this.numero_passageiros = numero_passageiros;
        }
    }

    public int[] getChegada() {
        return chegada;
    }






}