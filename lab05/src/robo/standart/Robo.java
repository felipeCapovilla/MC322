package robo.standart;

import ambiente.Ambiente;
import constantes.*;
import exceptions.*;
import interfaces.Entidade;
import java.util.ArrayList;
import modulos.*;
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

    

    private ControleMovimento modulo_controleMovimento;
    private GerenciadorSensores modulo_gerenciadorSensores;
    private ModuloComunicacao modulo_comunicacao;

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

        this.modulo_controleMovimento = new ControleMovimento(id, this);
        this.modulo_gerenciadorSensores = new GerenciadorSensores(id, this);
    }


    /**
     * Adiciona a variacao das coordenadas no valor da coordenada atual.
     * @param deltaX
     * @param deltaY
     */
    public void mover(int deltaX, int deltaY, int deltaZ) throws NoModuleException{ 
        if(this.modulo_controleMovimento == null){
            throw new NoModuleException("Modulo para controle de movimento nao detectado.");
        }
        this.modulo_controleMovimento.mover(deltaX,deltaY,deltaZ);
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
     * Verifica se o espaco verificado esta livre, segundo o sensor espacial <p>
     * retorno: <p>
     * 1 = colinsao <p> 
     * 0 = sem solisao <p>
     * -1 = fora do alcance
     */
    public int detectarColisoes(int X, int Y, int Z) throws SensorMissingException{
        if(get_SensorEspacial() == null){
            throw new SensorMissingException("Sensor Espacial");
        }

        return get_SensorEspacial().detectarColisoes(getX(), getY(), getZ(), X, Y, Z, get_ambiente());
    }

    /**
     * Adicionar um sensor de temperatura no robo
     */
    public void adicionar_sensorTemperatura(int raio_alcance, String modelo, double precisao, double temperatura_maxima, double temperatura_minima){
        if(this.modulo_gerenciadorSensores == null){
            throw new NoModuleException(String.format("Nao foi possivel realizar essa operacao. Nao existe modulo gerenciador de sensores ativo em. %s",this.id));
        }
        SensorTemperatura novo_sensorTemperatura = new SensorTemperatura(raio_alcance,modelo,precisao,temperatura_maxima,temperatura_minima);
        this.sensor_temperatura = novo_sensorTemperatura;
        sensores.add(novo_sensorTemperatura);
    }
    /**
     * Adicionar um sensor de altitude no robo
     */
    public void adicionar_sensorAltitude(int raio_alcance, String modelo,double precisao, double altura_maxima){
        if(this.modulo_gerenciadorSensores == null){
            throw new NoModuleException(String.format("Nao foi possivel realizar essa operacao. Nao existe modulo gerenciador de sensores ativo em. %s",this.id));
        }
        SensorAltitude novo_SensorAltitude = new SensorAltitude(raio_alcance,modelo,precisao,altura_maxima);
        this.sensor_altitude = novo_SensorAltitude;
        sensores.add(novo_SensorAltitude);
    }
    /**
     * Adicionar um sensor espacial no robo
     */
    public void adicionar_sensorEspacial(int raio_alcance, String modelo){
        if(this.modulo_gerenciadorSensores == null){
            throw new NoModuleException(String.format("Nao foi possivel realizar essa operacao. Nao existe modulo gerenciador de sensores ativo em. %s",this.id));
        }
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
        if(this.modulo_gerenciadorSensores == null){
            throw new NoModuleException(String.format("Nao foi possivel realizar essa operacao. Nao existe modulo gerenciador de sensores ativo em. %s",this.id));
        }
        sensor_temperatura = novo_sensor;
        sensores.add(novo_sensor);
    }

    /**
     * Seta o novo sensor de altitude, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorAltitude(SensorAltitude novo_sensor){
        if(this.modulo_gerenciadorSensores == null){
            throw new NoModuleException(String.format("Nao foi possivel realizar essa operacao. Nao existe modulo gerenciador de sensores ativo em. %s",this.id));
        }
        sensor_altitude = novo_sensor;
        sensores.add(novo_sensor);
    }

    /**
     * Seta o novo sensor espacial, usado por herdeiros.
     * @param novo_sensor Novo sensor a ser adicionado.
     */
    public void set_sensorEspacial(SensorEspacial novo_sensor){
        if(this.modulo_gerenciadorSensores == null){
            throw new NoModuleException(String.format("Nao foi possivel realizar essa operacao. Nao existe modulo gerenciador de sensores ativo em. %s",this.id));
        }
        sensor_espacial = novo_sensor;
        sensores.add(novo_sensor);
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

    public void set_gerenciadorSensores(GerenciadorSensores novo_gerenciador){
        this.modulo_gerenciadorSensores = novo_gerenciador;
    }

    public GerenciadorSensores get_gerenciadorSensores(){
        return this.modulo_gerenciadorSensores;
    }

    @Override
    public int getY() {
        return this.posY;
    }


    @Override
    public int getZ() {
        return this.posZ;
    }

    public void setX(int novo_x){
        this.posX = novo_x;
    }

    public void setY(int novo_y){
        this.posY = novo_y;
    }

    public void setZ(int novo_z){
        this.posZ = novo_z;
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

    public ControleMovimento get_controleMovimento(){
        return this.modulo_controleMovimento;
    }

    public void set_ControleMovimento(ControleMovimento novo_modulo){
        this.modulo_controleMovimento=novo_modulo;
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

    public ArrayList<Sensor> getSensores() {
        return sensores;
    }

    public ModuloComunicacao getModulo_comunicacao() {
        return modulo_comunicacao;
    }



}

