package robo.terrestre.standart;

import central_comunicacao.CentralComunicacao;
import constantes.Bussola;
import exceptions.*;
import interfaces.*;
import java.util.ArrayList;
import robo.standart.Robo;

public class RoboTerrestre extends Robo implements Comunicavel{
    private int velocidadeMaxima;
    private CentralComunicacao central_comunicacao;
    private ArrayList<String> mensagens_recebidas;

    /**
     * Construtor da classe RoboTerrestre.
     */
    public RoboTerrestre(String nome,int posicaoX, int posicaoY ,Bussola direcao, int velocidadeMaxima) throws ValueOutOfBoundsException{
        super(nome,posicaoX, posicaoY, 0,direcao);

        if(velocidadeMaxima < 0){
            throw new ValueOutOfBoundsException(String.format("velocidade máxima: %d", velocidadeMaxima));
        }

        this.velocidadeMaxima = velocidadeMaxima;
        this.central_comunicacao = null;
        this.mensagens_recebidas = new ArrayList<>();
    }
        
    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual, sendo a variação máxima igual à velocidadeMaxima.
     * @param deltaX
     * @param deltaY
     */
    public void mover(int deltaX, int deltaY) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException{
        double velAtual = Math.sqrt((deltaX*deltaX + deltaY*deltaY));

        if(velAtual >= velocidadeMaxima){ //altera o deslocamento para o máximo possível que é menor do que a velocidade máxima
            deltaX = (int)((deltaX/velAtual) * velocidadeMaxima);
            deltaY = (int)((deltaY/velAtual) * velocidadeMaxima);
        } 

        super.mover(deltaX, deltaY, 0);
        
    }

    //GETs e SETs

    /**
     * Retorna o valor da variável velocidadeMaxima
     */
    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    /**
     * define o valor da variável velocidadeMaxima
     */
    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = Math.max(0, velocidadeMaxima);
    }

    @Override
    public void executarTarefa() throws UnsupportedOperationException{
        // Robo Terrestre não implementa tarefa
        throw new UnsupportedOperationException("Unimplemented method 'executarTarefa'");
    }


    //Funcoes da interface COMUNICAVEL.

    @Override
    public void set_CentralComunicao(CentralComunicacao nova_central){
        this.central_comunicacao = nova_central;
    }

    @Override
    public CentralComunicacao get_centralComunicacao(){
        return this.central_comunicacao;
    }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem){
        if(this.central_comunicacao == null){
            throw new IllegalArgumentException("Nao e possivel fazer uma comunicacao sem uma central intermediaria. Favor adicione uma central.");
        }
        this.central_comunicacao.registrarMensagem(getID(),mensagem);
        destinatario.receberMensagem(mensagem);
    }

    @Override
    public void receberMensagem(String mensagem){
        mensagens_recebidas.add(mensagem);
    }


}
