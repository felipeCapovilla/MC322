package Console;

import ambiente.*;
import java.util.ArrayList;
import java.util.Scanner;
import robo.aereo.explorador.RoboVoadorExplorador;
import robo.aereo.standart.RoboAereo;
import robo.aereo.turista.RoboVoadorTurista;
import robo.standart.Robo;
import robo.terrestre.pedestre.RoboPedestre;
import robo.terrestre.standart.RoboTerrestre;
import robo.terrestre.veiculo.RoboVeiculo;

public class Console {
    final Ambiente ambiente;
    final Scanner scanner = new Scanner(System.in);

    public Console(Ambiente ambiente){
        this.ambiente = ambiente;
    }

    ///////MENU PRINCIPAL
    /**
     * Menu principal do terminal
     */
    public void mainMenu(){
        int resposta;

        System.out.println("+----------------------------------+");
        System.out.println("|        SIMULADOR DE ROBOS        |");
        


        do{
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Ver Ambiente                 |");
            System.out.println("| [2] Ver Robos                    |");
            System.out.println("| [99] Sair                        |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scanner.nextInt();

            switch (resposta) {
                case 1:
                    //ambienteMenu
                    ambienteMenu();
                    break;
                case 2:
                    //roboMenu
                    roboMenu();
                    break;
                case 99:
                    break;
                default:
                    System.out.println("Opcao nao disponivel");;
            }

        } while (resposta != 99);

        scanner.close();
    }

    ///////MENU AMBIENTE
    /**
     * Menu relacionado ao ambiente
     */
    private void ambienteMenu(){
        int resposta;

        do{
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Visualizar Ambiente          |");
            System.out.println("| [2] Info                         |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scanner.nextInt();

            switch (resposta) {
                case 1:
                    //Visualizar ambiente
                    System.out.print("altura desejada: ");
                    resposta = scanner.nextInt();

                    imprimirMapa(resposta);

                    break;
                case 2:
                    //informações do ambiente
                    System.out.printf("- Dimensões (C x L x A): %d x %d x %d\n", ambiente.get_comprimento(), ambiente.getLargura(), ambiente.getAltura());
                    System.out.println("- Lista de obstáculos:");

                    for(Obstaculo obst : ambiente.getObstaculos()){
                        System.out.printf("\t ->%S\n", obst);
                    }

                    System.out.println("- Lista de Robos:");

                    for(Robo robo : ambiente.getListaRobos()){
                        System.out.printf("\t ->%S\n", robo);
                    }


                    break;
                case 99:
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        } while (resposta != 99);
        System.out.println("");
    }

    /**
     * Imprime o mapa do ambiente na altura desejada
     * @param altura
     */
    private void imprimirMapa(int altura){
        int largura = ambiente.getLargura(); //X
        int comprimento = ambiente.get_comprimento(); //Y
        char sprite;

        if(altura < 0 || altura >= ambiente.getAltura()){
            System.out.println("Altura inválida");
        } else {
            for(int y = comprimento-1; y>=0; y--){
                for(int x = 0; x < largura; x++){
                    //Vazio
                    sprite = '.';
    
                    //Verificar robos
                    ArrayList<Robo> listaRobos = ambiente.getListaRobos();
                    for(int i = 0; sprite != '@' && i < listaRobos.size(); i++){
                        int[] pos = listaRobos.get(i).get_posicao();
    
                        if(pos[0] == x && pos[1] == y /*&& listaRobos.get(i).get_altitude() == altura*/){
                            sprite = '@';
                        }
                    }
    
                    //Verificar obstáculo
                    ArrayList<Obstaculo> obstaculos = ambiente.getObstaculos();
                    for(int i = 0; sprite != 'X' && sprite != '@' && i < obstaculos.size(); i++){
                        if(obstaculos.get(i).estaDentro(x, y) && obstaculos.get(i).getAltura() >= altura){
                            sprite = 'X';
                        }
                    }
    
                    System.out.print(sprite);
                }
                System.out.println(""); //Quebra de linha
            }
            System.out.println("");
        }

    }


    ///////MENU ROBO
    /**
     * Formata uma String para ter exatamente o tamanho inserido, para mais(inserindo espaços) ou para menos
     * @param texto
     * @param tamanho
     * @return
     */
    private static String formatString(String texto, int tamanho) {
        if (texto.length() > tamanho) {
            return texto.substring(0, tamanho); //corta a String até ter length = tamanho
        } else {
            return String.format("%-" + tamanho + "s", texto); //aumenta a string com espaços até ter length = tamanho
        }
    }

    /**
     * Menu relacionado aos robos
     */
    private void roboMenu(){
        int resposta;

        do{
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um robo          |");

            //Opções
            for(int i = 0; i < ambiente.get_robos_ativos(); i++){
                System.out.printf("| [%d] %s|\n", i+1, formatString(ambiente.getListaRobos().get(i).toString(), 29));

            }

            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scanner.nextInt();

            if(resposta > 0 && resposta <= ambiente.get_robos_ativos()){
                rotearRobo(ambiente.getListaRobos().get(resposta-1));

            } else if(resposta != 99){
                System.out.println("Opcao nao disponivel");
            }

        } while (resposta != 99);
        System.out.println("");
    }

    /**
     * Leva ao menu de cada robo
     * @param _robo
     */
    private void rotearRobo(Robo _robo){
        //Intanciar o robo na classe correta
        if(_robo.getClass() == RoboAereo.class){
            controleRoboAereoStandart((RoboAereo) _robo);

        } else if(_robo.getClass() == RoboVoadorExplorador.class){
            controleRoboAereoExplorador((RoboVoadorExplorador) _robo);

        } else if(_robo.getClass() == RoboVoadorTurista.class){
            System.out.println("RoboVoadorTurista");

        } else if(_robo.getClass() == RoboTerrestre.class){
            System.out.println("RoboTerrestre");

        } else if(_robo.getClass() == RoboVeiculo.class){
            System.out.println("RoboVeiculo");

        } else if(_robo.getClass() == RoboPedestre.class){
            System.out.println("RoboPedestre");

        } else {
            controleRoboStandart(_robo);
        }
    }

    /**
     * Menu que controla o RoboStandart
     * @param robo
     */
    private void controleRoboStandart(Robo robo){
        int resposta;

        do{
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Visualizar arredores         |");
            System.out.println("| [3] Info                         |");
            System.out.println("| [4] Monitorar sensores           |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scanner.nextInt();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scanner.nextInt();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scanner.nextInt();

                    robo.mover(deltaX, deltaY);
                    break;
                case 2:
                    //Visualizar arredores
                    //TODO visualizar arredores

                    break;

                case 3:
                    //Info
                    System.out.println("Nome: " + robo.getNome());
                    System.out.println("Modelo: " + robo.getClass().getSimpleName());
                    System.out.printf("Posicao atual: (%d,%d)\n", robo.get_posicao()[0], robo.get_posicao()[1]);
                    System.out.println("Direcao atual: " + robo.getDirecao());

                    try {
                        System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Temperatura: Sensor de temperatura nao instalado");
                    }
                    break;

                case 4:
                    //Monitora os sensores do robo
                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }
                    break;

                case 99:
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        } while (resposta != 99);
        System.out.println("");
    }

    /**
     * Menu que controla o RoboAereoStandart
     * @param robo
     */
    private void controleRoboAereoStandart(RoboAereo robo){
        int resposta;

        do{
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Subir/Descer Robo            |");
            System.out.println("| [3] Visualizar arredores         |");
            System.out.println("| [4] Info                         |");
            System.out.println("| [5] Monitorar sensores           |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scanner.nextInt();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scanner.nextInt();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scanner.nextInt();

                    robo.mover(deltaX, deltaY);
                    break;

                case 2:
                    //Subir/Descer robo
                    System.out.print("Deslocamento altitude: ");
                    resposta = scanner.nextInt();

                    try {
                        if(resposta > 0){
                            robo.subir(resposta);
                        } else {
                            robo.descer(-resposta);
                        }    
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                
                    break;

                case 3:
                    //Visualizar arredores
                    //TODO visualizar arredores

                    break;

                case 4:
                    //Info
                    System.out.println("Nome: " + robo.getNome());
                    System.out.println("Modelo: " + robo.getClass().getSimpleName());
                    System.out.printf("Posicao atual: (%d,%d)\n", robo.get_posicao()[0], robo.get_posicao()[1]);
                    System.out.println("Direcao atual: " + robo.getDirecao());
                    
                    try {
                        System.out.printf("Altitude: (%.2f\u00b1%.2f)m\n", robo.get_SensorAltitude().get_altitude(), robo.get_SensorAltitude().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Altitude: Sensor de altitude nao instalado");
                    }
                    try {
                        System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Temperatura: Sensor de temperatura nao instalado");
                    }
                    break;

                case 5:
                    //Monitora os sensores do robo
                    if(robo.get_SensorAltitude() == null){
                        System.out.println("Sensor de altitude nao instalado");
                    } else {
                        System.out.print("Sensor de altitude:\n\t");
                        robo.get_SensorAltitude().monitorar();
                    }

                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }
                    break;

                case 99:
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        } while (resposta != 99);
        System.out.println("");
    }

    /**
     * Menu que controla o RoboAereoExplorador
     * @param robo
     */
    private void controleRoboAereoExplorador(RoboVoadorExplorador robo){
        int resposta;

        do{
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Subir/Descer Robo            |");
            System.out.println("| [3] Visualizar arredores         |");
            System.out.println("| [4] Info                         |");
            System.out.println("| [5] Monitorar sensores           |");
            System.out.println("| [6] Iniciar/Finalizar missao     |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scanner.nextInt();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scanner.nextInt();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scanner.nextInt();

                    robo.mover(deltaX, deltaY);
                    break;

                case 2:
                    //Subir/Descer robo
                    System.out.print("Deslocamento altitude: ");
                    resposta = scanner.nextInt();

                    try {
                        if(resposta > 0){
                            robo.subir(resposta);
                        } else {
                            robo.descer(-resposta);
                        }    
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                
                    break;

                case 3:
                    //Visualizar arredores
                    //TODO visualizar arredores

                    break;

                case 4:
                    //Info
                    System.out.println("Nome: " + robo.getNome());
                    System.out.println("Modelo: " + robo.getClass().getSimpleName());
                    System.out.printf("Posicao atual: (%d,%d)\n", robo.get_posicao()[0], robo.get_posicao()[1]);
                    System.out.println("Direcao atual: " + robo.getDirecao());
                    
                    try {
                        System.out.printf("Altitude: (%.2f\u00b1%.2f)m\n", robo.get_SensorAltitude().get_altitude(), robo.get_SensorAltitude().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Altitude: Sensor de altitude nao instalado");
                    }
                    try {
                        System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Temperatura: Sensor de temperatura nao instalado");
                    }

                    if(robo.status_missao()){
                        System.out.println("Status da missao: ATIVO");
                        System.out.println("\tPlaneta destino: " + robo.get_planeta());
                        System.out.printf("\tPressao atual: %dkPa\n", robo.get_pressao());
                        System.out.println("\tVelocidade atual:" + robo.get_velocidade());

                    } else {
                        System.out.println("Status da missao: INATIVO");
                    }


                    break;

                case 5:
                    //Monitora os sensores do robo
                    if(robo.get_SensorAltitude() == null){
                        System.out.println("Sensor de altitude nao instalado");
                    } else {
                        System.out.print("Sensor de altitude:\n\t");
                        robo.get_SensorAltitude().monitorar();
                    }

                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }
                    break;

                case 6:
                    if(robo.status_missao()){
                        robo.finalizar_exploracao();
                        System.out.println("Missao finalizada");
                    } else {
                        int pressao, temperatura, velocidade;
                        String planeta;

                        System.out.print("Indicar planeta destino: ");
                        planeta = scanner.next();

                        System.out.print("Indicar velocidade:");
                        velocidade = scanner.nextInt();

                        System.out.print("Indicar temperatura:");
                        temperatura = scanner.nextInt();

                        System.out.print("Indicar pressao:");
                        pressao = scanner.nextInt();

                        try{
                            robo.iniciar_exploracao(pressao, temperatura, velocidade, planeta);

                            System.out.println("Missao iniciada");

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        
                    }

                    break;

                case 99:
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        } while (resposta != 99);
        System.out.println("");
    }














































































}
