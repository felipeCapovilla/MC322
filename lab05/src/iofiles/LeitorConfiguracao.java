package iofiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

import ambiente.Ambiente;
import constantes.*;
import exceptions.*;
import robo.aereo.explorador.RoboVoadorExplorador;
import robo.aereo.standart.RoboAereo;
import robo.aereo.turista.RoboVoadorTurista;
import robo.standart.Robo;
import robo.terrestre.pedestre.RoboPedestre;
import robo.terrestre.standart.RoboTerrestre;
import robo.terrestre.veiculo.RoboVeiculo;

public class LeitorConfiguracao {

    static public Ambiente lerConfiguracao(String path){
        Ambiente ambiente = null;

        try {
            File fileConfig = new File(path);
            BufferedReader inData = new BufferedReader(new FileReader(fileConfig));
            try (Scanner configText = new Scanner(inData)) {
                configText.useLocale(Locale.US); //usa ponto para separar decimal enquanto ler o arquivo
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

                        case "OBSTACULO":
                            leitura = configText.next();

                            switch (leitura) {
                                case "ponto":
                                    ambiente.adicionarObstaculo(configText.nextInt(), configText.nextInt(), TipoObstaculo.strToTipoObstaculo(configText.next()));
                            
                                    break;

                                case "area":
                                    ambiente.adicionarObstaculo(configText.nextInt(), configText.nextInt(), configText.nextInt(), configText.nextInt(), TipoObstaculo.strToTipoObstaculo(configText.next()));
                                    
                                    break;

                                case "volume":
                                    ambiente.adicionarObstaculo(configText.nextInt(), configText.nextInt(), configText.nextInt(), configText.nextInt(), configText.nextInt(), TipoObstaculo.strToTipoObstaculo(configText.next()));
                                    
                                    break;

                                default:
                                    System.err.println(leitura);
                            }

                            break;

                        case "SENSORTEMPERATURA":
                            leitura = configText.next();
                            robo = ambiente.getRobo(leitura);

                            if(robo != null){
                                robo.adicionar_sensorTemperatura(configText.nextInt(), configText.next(), configText.nextDouble(), configText.nextDouble(), configText.nextDouble());

                            } else {
                                System.err.printf("ID %s não encontrado\n", leitura);
                                configText.nextLine();
                            }

                            break;

                        case "SENSORESPACIAL":
                            leitura = configText.next();
                            robo = ambiente.getRobo(leitura);

                            if(robo != null){
                                robo.adicionar_sensorEspacial(configText.nextInt(), configText.next());

                            } else {
                                System.err.printf("ID %s não encontrado\n", leitura);
                                configText.nextLine();
                            }

                            break;

                        case "SENSORALTITUDE":
                            leitura = configText.next();
                            robo = ambiente.getRobo(leitura);

                            if(robo != null){
                                robo.adicionar_sensorAltitude(configText.nextInt(), configText.next(), configText.nextDouble(), configText.nextDouble());

                            } else {
                                System.err.printf("ID %s não encontrado\n", leitura);
                                configText.nextLine();
                            }

                            break;
                            
                        default:
                            System.err.println(leitura);
                    }
                    
                }
            }



        } catch (FileNotFoundException ex) {
            System.err.println("Erro ao ler o arquivo de configuração: " + ex.getMessage());
        } catch (ColisaoException | PointOutOfMapException | ValueOutOfBoundsException | NullPointerException e){
            System.err.println(e);
        }

        return ambiente;
    }


}
