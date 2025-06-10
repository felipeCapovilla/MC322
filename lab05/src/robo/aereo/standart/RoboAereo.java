package robo.aereo.standart;

import ambiente.Ambiente;
import constantes.*;
import exceptions.*;
import interfaces.*;
import robo.agenteInteligente.AgenteInteligente;
import sensor.altitude.SensorAltitude;

public class RoboAereo extends AgenteInteligente implements Battery {
    
    private final int altitude_max;
    private int bateria = 100;
    private final int carregarBateria = 20;

    public RoboAereo(String nome,int posicaoX, int posicaoY,Bussola direcao, int altitude,int altitude_max) throws ValueOutOfBoundsException{
        
        super(nome,posicaoX, posicaoY, altitude, direcao); //Chama o construtor da super-classe Robo.

        if(altitude < 0){
            throw new ValueOutOfBoundsException("altitude: " + altitude);
        }
        if(altitude_max < 0){
            throw new ValueOutOfBoundsException("altitude máxima: " + altitude_max);
        }

        this.altitude_max = altitude_max;
    }


    /**
     * Adiciona a altura atual do robo.
     * @param metros Indica quantos metros ele deve subir.
     */
    public void subir(int metros) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException, SensorMissingException, ValueOutOfBoundsException{
        int pesoBateria = 10;//quanto gasta de bateria
        
        if(getZ() + metros > this.altitude_max){ //Verifica se a altura pode ser atingida por conta das limitações do robo.
            throw new ValueOutOfBoundsException("altitude acima da máxima: " + ((getZ() + metros) - this.altitude_max)+"m."); 
        }

        if(this.get_SensorAltitude() == null){
            throw new SensorMissingException("Sensor de altitude");
        }


        mover(0, 0, metros); 
        descarregar(pesoBateria);
        this.get_SensorAltitude().set_altitude(getZ());

        
    }

    

    /**
     * Subtrai a altura atual do robo.
     * @param metros Indica quantos metros ele vai descer.
     */
    public void descer(int metros) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException, SensorMissingException{
        int pesoBateria = 10;//quanto gasta de bateria

        if(this.get_SensorAltitude() == null){
            throw new SensorMissingException("Sensor de altitude");
        }
        
        mover(0, 0, -metros);
        descarregar(pesoBateria);
        this.get_SensorAltitude().set_altitude(getZ());

    }

    /**
     * Move o Robo no plano XY
     */
    public void mover(int deltaX, int deltaY) throws NullPointerException, ColisaoException, PointOutOfMapException, RoboDesligadoException, LowBatteryException {
        int pesoBateria = 5;//quanto gasta de bateria

        if(pesoBateria <= bateria){
            //Evitar de mover mesmo com bateria baixa ou perder bateria em um movimento inválido
            super.mover(deltaX, deltaY, 0);
            descarregar(pesoBateria);
        } else {
            descarregar(pesoBateria);//joga o erro de lowBattery
        }
    }


    /**
     * Sobreescreve o metodo adicionar_sensorAltitude pois aqui o robo ja pode ter altitude, a qual e passada pelo sensor.
     */
    @Override
    public void adicionar_sensorAltitude(int raio_alcance, String modelo,double precisao, double altura_maxima){
        SensorAltitude novo_SensorAltitude = new SensorAltitude(raio_alcance,modelo,precisao,altura_maxima);
        novo_SensorAltitude.set_altitude(getZ());
        this.set_sensorAltitude(novo_SensorAltitude); 
        sensores.add(novo_SensorAltitude);
    }

    /**
     * Retorna o valor da variável altitude pelo sensor, caso esteja dentro dos limites de seu funcionamento.
     */
    public double get_altitude() {

        if(get_SensorAltitude() != null && getZ() <= this.get_SensorAltitude().get_alturaMaxima()){ //Se a altitude atual pode ser medida pelo sensor.
            return this.get_SensorAltitude().get_altitude();
        }
        else{
            return -1.0; //Caso contrario: retorna um numero incoerente (-1).
        }
    }


    /**
     * Retorna o valor da variável altitude maxima
     */
    public int get_altitude_max() {
        return altitude_max;
    }

    @Override
    public void executarTarefa() {
        // Tarefa não implementada para RoboAereo
        throw new UnsupportedOperationException("Unimplemented method 'executarTarefa'");
    }


    @Override
    public void carregar() {
        bateria = Math.min(bateria + carregarBateria, 100);
    }

    @Override
    public void descarregar(int descarga) throws LowBatteryException{
        if(bateria >= descarga){
            bateria -= descarga;
        } else {
            throw new LowBatteryException(bateria + "%");
        }
    }

    @Override
    public int getBateria() {
        return bateria;
    }


    @Override
    public void executarMissao(Ambiente a) throws NullPointerException{
        if(temMissao()){
            System . out . println (" Executando missão de busca de ponto ... ");
            System.out.printf("Ponto inicial: (%d,%d,%d)\n", getX(), getY(), getZ());
            do { 
                missao.executar(this, a); 
                //System.out.printf("(%d,%d,%d)\n", getX(), getY(), getZ());  
            } while (missao.isAtivo());
            
        } else {
            throw new NullPointerException("Missao nao selecionada");
        }

        
    }

    @Override
    public void setZ(int z) throws SensorMissingException {
        super.setZ(z);

        if(this.get_SensorAltitude() == null){
            throw new SensorMissingException("Sensor de altitude");
        } else {
            this.get_SensorAltitude().set_altitude(z);
        }        
    }


}
