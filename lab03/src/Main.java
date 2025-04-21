/*
 * Main.java
 * 
 * Última modificação: 10/04/2025
 * 
 * Simulação de um ambiente virtual para a testar robos.
 */


import Console.Console;
import ambiente.*;
import constantes.*;
import robo.aereo.explorador.*;
import robo.aereo.standart.*;
import robo.aereo.turista.*;
import robo.standart.*;
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

        Robo roboStandart = new Robo("padraozinho",20,20,Bussola.LESTE);
        RoboAereo roboAereo = new RoboAereo("padrao aereo",15,15,Bussola.OESTE,20,100);
        RoboVoadorTurista roboAereoTurista = new RoboVoadorTurista("turistando",10,20,Bussola.OESTE,10,40,30);
        RoboVoadorExplorador roboAereoExplorador = new RoboVoadorExplorador("explorador",97,1,Bussola.LESTE,2,50,100);
        RoboTerrestre roboTerrestre = new RoboTerrestre("terra",15,16,Bussola.SUL,20);
        RoboVeiculo roboTerrestreVeiculo = new RoboVeiculo("carro",76,56,Bussola.OESTE,120,120);
        RoboPedestre roboTerrestrePedestre = new RoboPedestre("andarilho", 1, 1, Bussola.SUL, 20);

        
        //Ambiente
            //Adicionar os robos no ambiente
        ambiente.adicionarRobo(roboStandart);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboAereoExplorador);
        ambiente.adicionarRobo(roboAereoTurista);
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboTerrestreVeiculo);
        ambiente.adicionarRobo(roboTerrestrePedestre);

            //Adicionar Obstáculos no ambiente
        ambiente.adicionarObstaculo(98, 1, TipoObstaculo.PESSOA);
        ambiente.adicionarObstaculo(10, 7, 20, 13, TipoObstaculo.BURACO);
        ambiente.adicionarObstaculo(1, 40, 5, 60, TipoObstaculo.PREDIO);
        ambiente.adicionarObstaculo(30, 25, 15, 25, TipoObstaculo.PAREDE);
        ambiente.adicionarObstaculo(50, 50, TipoObstaculo.ARBUSTO);
        ambiente.adicionarObstaculo(70, 61, 90, 70, 40, TipoObstaculo.AVIAO);

        //Robos
            //adicionar sensores
        roboStandart.adicionar_sensorTemperatura(0, "GGG-01", 0.5, 200, 25);
        roboStandart.get_SensorTemperatura().set_temperatura(10);
        roboStandart.adicionar_sensorEspacial(5, "EPC-01");

        roboAereo.adicionar_sensorAltitude(20, "ALT-01", 1, 100);
        roboAereo.adicionar_sensorTemperatura(0, "TMP-76", 0.8, 900, 0);
        roboAereo.adicionar_sensorEspacial(5, "EPC-01");

        roboAereoExplorador.adicionar_sensorAltitude(10, "ALT-23", 0.1, 100);
        roboAereoExplorador.adicionar_sensorTemperatura(0, "TMP-09", 0.3, 400, 10);
        roboAereoExplorador.adicionar_sensorEspacial(5, "EPC-01");

        roboAereoTurista.adicionar_sensorAltitude(0, "ALT-30", 0.1, 100);
        roboAereoTurista.adicionar_sensorTemperatura(0, "TMP-92", 0.1, 100, 0);
        roboAereoTurista.adicionar_sensorEspacial(5, "EPC-01");

        roboTerrestre.adicionar_sensorTemperatura(0, "TMP-99", 5, 70, 50);
        roboTerrestre.adicionar_sensorEspacial(10, "EPC-01");

        roboTerrestrePedestre.adicionar_sensorTemperatura(0, "TMP-54", 2, 88, 55);
        roboTerrestrePedestre.adicionar_sensorEspacial(5, "EPC-01");

        roboTerrestreVeiculo.adicionar_sensorTemperatura(0, "TMP-23", 1, 400, 100);
        roboTerrestreVeiculo.adicionar_sensorEspacial(15, "EPC-01");
        

        //Menu
        menu.mainMenu();


    }}