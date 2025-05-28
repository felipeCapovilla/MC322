/*
 * Main.java
 * 
 * Simulação de um ambiente virtual para a testar robos.
 */


import Console.Console;
import ambiente.*;
import constantes.*;
import robo.aereo.explorador.*;
import robo.aereo.standart.*;
import robo.aereo.turista.*;
import robo.terrestre.pedestre.*;
import robo.terrestre.standart.*;
import robo.terrestre.veiculo.*;

/**
 * Arquivo principal para iniciação do programa
 */
public class Main {
    public static void main(String[] args) {
        
        //Criacao do ambiente e dos robos.

        Ambiente ambiente = new Ambiente(100,100,100);
        Console menu = new Console(ambiente);

        RoboAereo roboAereo = new RoboAereo("padrao aereo",2,97,Bussola.OESTE,5,100);
        RoboVoadorTurista roboAereoTurista = new RoboVoadorTurista("turistando",10,20,Bussola.OESTE,10,40,30);
        RoboVoadorExplorador roboAereoExplorador = new RoboVoadorExplorador("explorador",80,65,Bussola.LESTE,41,50,100);
        RoboTerrestre roboTerrestre = new RoboTerrestre("terra",30,30,Bussola.SUL,20);
        
        RoboVeiculo roboTerrestreVeiculo = new RoboVeiculo("carro",75,25,Bussola.OESTE,120,120);
        RoboVeiculo roboTerrestreVeiculo2 = new RoboVeiculo("marquinhos",74,25,Bussola.LESTE,120,120);

        RoboPedestre roboTerrestrePedestre = new RoboPedestre("andarilho", 1, 1, Bussola.SUL, 20);

        RoboAereo roboSemSensor = new RoboAereo("noSense", 0, 0, Bussola.NORTE, 90, 99);

        //Robos
            //adicionar sensores
        roboAereo.adicionar_sensorAltitude(20, "ALT-01", 1, 100);
        roboAereo.adicionar_sensorTemperatura(0, "TMP-76", 0.8, 900, 0);
        roboAereo.adicionar_sensorEspacial(15, "EPC-01");

        roboAereoExplorador.adicionar_sensorAltitude(10, "ALT-23", 0.1, 100);
        roboAereoExplorador.adicionar_sensorTemperatura(0, "TMP-09", 0.3, 400, 10);
        roboAereoExplorador.adicionar_sensorEspacial(15, "EPC-01");

        roboAereoTurista.adicionar_sensorAltitude(0, "ALT-30", 0.1, 100);
        roboAereoTurista.adicionar_sensorTemperatura(0, "TMP-92", 0.1, 100, 0);
        roboAereoTurista.adicionar_sensorEspacial(5, "EPC-01");

        roboTerrestre.adicionar_sensorTemperatura(0, "TMP-99", 5, 70, 50);
        roboTerrestre.adicionar_sensorEspacial(10, "EPC-01");

        roboTerrestrePedestre.adicionar_sensorTemperatura(0, "TMP-54", 2, 88, 55);
        roboTerrestrePedestre.adicionar_sensorEspacial(5, "EPC-01");

        roboTerrestreVeiculo.adicionar_sensorTemperatura(0, "TMP-23", 1, 400, 100);
        roboTerrestreVeiculo.adicionar_sensorEspacial(15, "EPC-01");
            
        
        //Ambiente
            //Adicionar os robos no ambiente
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboAereoExplorador);
        ambiente.adicionarRobo(roboAereoTurista);
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboTerrestreVeiculo);
        ambiente.adicionarRobo(roboTerrestrePedestre);
        ambiente.adicionarRobo(roboSemSensor);
        ambiente.adicionarRobo(roboTerrestreVeiculo2);

            //Adicionar Obstáculos no ambiente
        ambiente.adicionarObstaculo(40, 40, 45, 45, TipoObstaculo.BURACO);

        ambiente.adicionarObstaculo(1, 97, TipoObstaculo.PESSOA);
        ambiente.adicionarObstaculo(3, 97, TipoObstaculo.PESSOA);
        ambiente.adicionarObstaculo(2, 96, TipoObstaculo.PESSOA);
        ambiente.adicionarObstaculo(2, 98, TipoObstaculo.PESSOA);
        ambiente.adicionarObstaculo(70, 70, TipoObstaculo.ARBUSTO);

        ambiente.adicionarObstaculo(1, 40, 5, 60, TipoObstaculo.PREDIO);
        ambiente.adicionarObstaculo(30, 25, 15, 25, TipoObstaculo.PAREDE);
        ambiente.adicionarObstaculo(75, 10, 90, 20, 40, TipoObstaculo.AVIAO);


        //Menu
        menu.mainMenu();

        

    }}