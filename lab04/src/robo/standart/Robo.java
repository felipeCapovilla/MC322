package robo.standart;

import ambiente.Ambiente;
import ambiente.Obstaculo;
import constantes.*;
import exceptions.*;
import interfaces.Entidade;
import java.util.ArrayList;
import sensor.altitude.SensorAltitude;
import sensor.espacial.SensorEspacial;
import sensor.standart.Sensor;
import sensor.temperatura.SensorTemperatura;


public abstract class Robo implements Entidade{
    
    private Ambiente ambiente_atual;
    private final String id;
    private int posX;
    private int posY;
    private int posZ;
    private EstadoRobo estado;

    private boolean tarefaAtiva = false;

    private final TipoEntidade tipoEntidade = TipoEntidade.ROBO;

    private SensorTemperatura sensor_temperatura;
    private SensorAltitude sensor_altitude;
    private SensorEspacial sensor_espacial;
    protected final ArrayList<Sensor> sensores;

    /**
     * NORTE, SUL, LESTE, OESTE
     */
    private Bussola direcao;

    public Robo(String id, int posX, int posY, int posZ, Bussola direcao){
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.ambiente_atual = null;
        this.direcao = direcao;
        this.sensores = new ArrayList<>();
        this.sensor_altitude = null;
        this.sensor_temperatura = null;
        this.sensor_espacial = null;
        this.estado = EstadoRobo.DESLIGADO;
    }


    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual.
     * @param deltaX
     * @param deltaY
     */
    public void mover(int deltaX, int deltaY, int deltaZ) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException{ 
        if(this.ambiente_atual == null){
            throw new NullPointerException();
        }
        if(!isLigado()){
            throw new RoboDesligadoException();
        }


        if(ambiente_atual.dentroDosLimites(this.posX + deltaX, this.posY + deltaY, posZ+deltaZ)){
            if(detectarColisoes(posX + deltaX, posY + deltaY, posZ + deltaZ)){
                //Obs.: a função moverEntidade precisa ser chamada antes de mudar as variáveis do robo
                ambiente_atual.moverEntidade(this, this.posX+deltaX, this.posY+deltaY, this.posZ + deltaZ);

                this.posX += deltaX;
                this.posY += deltaY;
                this.posZ += deltaZ;
            } else {
                throw new ColisaoException("Posicao ja ocupada");
            }
                
        } else if((int) getZ() == -1){
            throw  new SensorMissingException("Sensor de altitude nao instalado, nao e seguro se movimentar"); 
        } else {
            throw new PointOutOfMapException("Tentativa de mover fora dos limites. Continua na posição (" + posX + "," + posY + ","+ posZ + ")");
        }
        
    }    

    /**
     * Altera o estado do Robo para LIGADO.
     */
    public void ligar(){
        this.estado = EstadoRobo.LIGADO;
    }

    /**
     * Altera o estado do Robo para DESLIGADO.
     */
    public void desligar(){
        this.estado = EstadoRobo.DESLIGADO;
    }

    /**
     * Retorna se o robo está ligado
     */
    public boolean isLigado(){
        return estado == EstadoRobo.LIGADO;
    }

    /**
     * Verifica se o espaco verificado esta livre
     * @param X
     * @param Y
     * @return
     */
    public boolean detectarColisoes(int X, int Y, int Z){
        return !identificar_obstaculos(X, Y, Z) && !identificar_robos(X, Y, Z);
    }

    /**
     * Identifica se a posicao esta ocupado por um obstaculo
     */
    private boolean identificar_obstaculos(int X, int Y, int Z) throws NullPointerException{
        if(ambiente_atual != null){ //apenas verifica se o robo estiver em um ambiente
            for(Obstaculo obst: ambiente_atual.getObstaculos()){
                if(!obst.Passavel() && obst.estaDentro(X, Y, Z)){
                    return true;
                }
                
            }

            return false;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Identifica se a posicao esta ocupado por um robo
     */
    private boolean identificar_robos(int X, int Y, int Z) throws NullPointerException{
        if(ambiente_atual != null){ //apenas verifica se o robo estiver em um ambiente
            for(Robo robo: ambiente_atual.getListaRobos()){
                if(robo.getX() == X && robo.getY() == Y && robo.getZ() == Z){
                    return true;
                }
            }

            return false;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Adicionar um sensor de temperatura no robo
     */
    public void adicionar_sensorTemperatura(int raio_alcance, String modelo, double precisao, double temperatura_maxima, double temperatura_minima){
        SensorTemperatura novo_sensorTemperatura = new SensorTemperatura(raio_alcance,modelo,precisao,temperatura_maxima,temperatura_minima);
        this.sensor_temperatura = novo_sensorTemperatura;
        sensores.add(novo_sensorTemperatura);
    }
    /**
     * Adicionar um sensor de altitude no robo
     */
    public void adicionar_sensorAltitude(int raio_alcance, String modelo,double precisao, double altura_maxima){
        SensorAltitude novo_SensorAltitude = new SensorAltitude(raio_alcance,modelo,precisao,altura_maxima);
        this.sensor_altitude = novo_SensorAltitude;
        sensores.add(novo_SensorAltitude);
    }
    /**
     * Adicionar um sensor espacial no robo
     */
    public void adicionar_sensorEspacial(int raio_alcance, String modelo){
        SensorEspacial novo_SensorEspacial = new SensorEspacial(raio_alcance,modelo);
        this.sensor_espacial = novo_SensorEspacial;
        sensores.add(novo_SensorEspacial);

    }

    //GETs e SETs

    /**
     * Seta o novo sensor de temperatura, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorTemperatura(SensorTemperatura novo_sensor){
        this.sensor_temperatura = novo_sensor;
    }

    /**
     * Seta o novo sensor de altitude, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorAltitude(SensorAltitude novo_sensor){
        this.sensor_altitude  = novo_sensor;
    }

    /**
     * Seta o novo sensor espacial, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorEspacial(SensorEspacial novo_sensor){
        this.sensor_espacial = novo_sensor;
    }

    /**
     * Retorna o valor da variável nome
     */
    public String getID(){
        return this.id;
    }

    /**
     * Adiciona o robo num ambiente.
     * @param novo_ambiente Ambiente a ser adicionado.
     */
    public void set_ambiente(Ambiente novo_ambiente){
        this.ambiente_atual = novo_ambiente;
    }

    /**
     * Retorna o valor da variável ambiente_atual
     */
    public Ambiente get_ambiente(){
        return this.ambiente_atual;
    }
    
    /**
     * Retorna o objeto sensorTemperatura associado ao robo.
     */
    public SensorTemperatura get_SensorTemperatura() throws RoboDesligadoException{
        if(!isLigado()){
            throw new RoboDesligadoException();
        }

        return this.sensor_temperatura;
    }

    /**
     * Retorna o objeto sensorAltitude associado ao robo.
     */
    public SensorAltitude get_SensorAltitude() throws RoboDesligadoException{
        if(!isLigado()){
            throw new RoboDesligadoException();
        }
        return this.sensor_altitude;
    }

    /**
     * Retorna o objeto sensorEspacial associado ao robo.
     */
    public SensorEspacial get_SensorEspacial() throws RoboDesligadoException{
        if(!isLigado()){
            throw new RoboDesligadoException();
        }

        return this.sensor_espacial;
    }

    /**
     * Retorna o valor da variável direção
     */
    public Bussola getDirecao(){
        return this.direcao;
    }

    /**
     * define o valor da variável direção
     */
    public void setDirecao(Bussola direcao){
        this.direcao = direcao;
        
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", getClass().getSimpleName(), id);
    }


    @Override
    public int getX() {
        return this.posX;
    }


    @Override
    public int getY() {
        return this.posY;
    }


    @Override
    public int getZ() {
        return this.posZ;
    }


    @Override
    public TipoEntidade getTipo() {
        return this.tipoEntidade;
    }

    /**
     * Retorna o Estado do Robo
     */
    public EstadoRobo getEstado(){
        return this.estado;
    }

    /**
     * define o Estado do robo
     */
    public void setEstado_robo(EstadoRobo novoEstado){
        this.estado = novoEstado;
    }

    @Override
    public char getRepresentacao() {
        return tipoEntidade.getRepresentacao();
    }

    //Metodos abstratos.

    /**
     * Começa a tarefa do robo
     */
    public abstract void executarTarefa();

    /**
     * Retorna se a Tarefa está ativa
     */
    public boolean isTarefaAtiva() {
        return tarefaAtiva;
    }

    /**
     * Define o estado da Tarefa
     */
    public void setTarefaAtiva(boolean tarefaAtiva) {
        this.tarefaAtiva = tarefaAtiva;
    }

    @Override
    public String getDescricao() {
        return "Robo é uma entidade móvel que pode realizar diversas funções";
    }

}

