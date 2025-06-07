package modulos;

import exceptions.*;
import java.util.ArrayList;
import robo.standart.Robo;
import sensor.altitude.SensorAltitude;
import sensor.espacial.SensorEspacial;
import sensor.standart.Sensor;
import sensor.temperatura.SensorTemperatura;


public class GerenciadorSensores{

    private String modelo;   
    private Robo robo_associado;
    private ArrayList<Sensor> sensores_ativos;
    
    public GerenciadorSensores(String modelo,Robo robo_associado){
        this.modelo = modelo;
        this.robo_associado = robo_associado;
    }

    public GerenciadorSensores(String modelo){
        this.modelo = modelo;
        this.robo_associado = null;
    }

    public void set_sensorAltitude(SensorAltitude novo_sensor){
        if(this.robo_associado == null){
            throw new NoRobotException(String.format("Nao ha robo associado ao gerenciador %s",this.modelo));
        }
        robo_associado.set_sensorAltitude(novo_sensor);
    }

    public void set_sensorEspacial(SensorEspacial novo_sensor){
        if(this.robo_associado == null){
            throw new NoRobotException(String.format("Nao ha robo associado ao gerenciador %s",this.modelo));
        }
        robo_associado.set_sensorEspacial(novo_sensor);        
    }

    public void set_sensorTemperatura(SensorTemperatura novo_sensor){
        if(this.robo_associado == null){
            throw new NoRobotException(String.format("Nao ha robo associado ao gerenciador %s",this.modelo));
        }
        robo_associado.set_sensorTemperatura(novo_sensor);        
    }

    public void remover_todosSensores(){
        if(this.robo_associado == null){
            throw new NoRobotException(String.format("Nao ha robo associado ao gerenciador %s",this.modelo));
        }
        robo_associado.set_sensorAltitude(null);
        robo_associado.set_sensorEspacial(null);
        robo_associado.set_sensorTemperatura(null);
    }

    public void set_roboAssociado(Robo novo_robo){
        this.robo_associado = novo_robo;
        this.sensores_ativos = robo_associado.getSensores();
    }

    public Robo get_roboAssociado(){
        return this.robo_associado;
    }

    public int get_quantidadeSensores(){
        return this.sensores_ativos.size();
    }

    @Override
    public String toString(){
        return String.format("Modulo gerenciador de sensores do modelo: %s\nAssociado ao robo %s",this.modelo,this.robo_associado);
    }
}

