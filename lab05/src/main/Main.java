/*
 * Main.java
 * 
 * Simulação de um ambiente virtual para a testar robos.
 */
package main;

import Console.Console;
import ambiente.*;
import constantes.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import robo.aereo.explorador.*;
import robo.aereo.standart.*;
import robo.aereo.turista.*;
import robo.standart.Robo;
import robo.terrestre.pedestre.*;
import robo.terrestre.standart.*;
import robo.terrestre.veiculo.*;

/**
 * Arquivo principal para iniciação do programa
 */
public class Main {
    public static void main(String[] args) {
        //Ambiente
        Ambiente ambiente =null;

        //Importar configurações do arquivo de propriedades
        String path = "src" + File.separator + "resources" + File.separator + "config.txt";
        
        try {
            File fileConfig = new File(path);
            BufferedReader inData = new BufferedReader(new FileReader(fileConfig));
            try (Scanner configText = new Scanner(inData)) {
                String leitura;
                Robo robo;
                
                while (configText.hasNext()) {
                    leitura = configText.next();
                    
                    switch (leitura) {
                        case "AMBIENTE":
                            ambiente = new Ambiente(configText.nextInt(), configText.nextInt(), configText.nextInt());
                            break;

                        case "ROBOAEREO":
                            robo = new RoboAereo(configText.next(),configText.nextInt(),configText.nextInt(),Bussola.strToBussola(configText.next()),configText.nextInt(),configText.nextInt());
                            ambiente.adicionarRobo(robo);
                            break;

                        case "ROBOAEREOTURISTA":
                            robo = new RoboVoadorTurista(configText.next(),configText.nextInt(),configText.nextInt(),Bussola.strToBussola(configText.next()),configText.nextInt(),configText.nextInt(), configText.nextInt());
                            ambiente.adicionarRobo(robo);
                            break;

                        case "ROBOAEREOEXPLORADOR":
                            robo = new RoboVoadorExplorador(configText.next(),configText.nextInt(),configText.nextInt(),Bussola.strToBussola(configText.next()),configText.nextInt(),configText.nextInt(), configText.nextInt());
                            ambiente.adicionarRobo(robo);
                            break;

                        case "ROBOTERRESTRE":
                            robo = new RoboTerrestre(configText.next(),configText.nextInt(),configText.nextInt(),Bussola.strToBussola(configText.next()),configText.nextInt());
                            ambiente.adicionarRobo(robo);
                            break;

                        case "ROBOVEICULO":
                            robo = new RoboVeiculo(configText.next(),configText.nextInt(),configText.nextInt(),Bussola.strToBussola(configText.next()),configText.nextInt(), configText.nextInt());
                            ambiente.adicionarRobo(robo);
                            break;

                        case "ROBOPEDESTRE":
                            robo = new RoboPedestre(configText.next(),configText.nextInt(),configText.nextInt(),Bussola.strToBussola(configText.next()), configText.nextInt());
                            ambiente.adicionarRobo(robo);
                            break;
                            
                        default:
                            System.err.println(leitura);;
                    }
                    
                }
            }



        } catch (FileNotFoundException ex) {
            System.err.println("Erro ao ler o arquivo de configuração: " + ex.getMessage());
            return;
        } catch (Exception e){
            System.err.println(e);
        }


        //Começar programa
        Console menu = new Console(ambiente);

        //Menu
        menu.mainMenu();

        
        //Criacao do ambiente e dos robos.

        


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
        ambiente.adicionarObstaculo(31, 31, TipoObstaculo.ARBUSTO);

        ambiente.adicionarObstaculo(1, 40, 5, 60, TipoObstaculo.PREDIO);
        ambiente.adicionarObstaculo(30, 25, 15, 25, TipoObstaculo.PAREDE);
        ambiente.adicionarObstaculo(75, 10, 90, 20, 40, TipoObstaculo.AVIAO);


        

        

    }}