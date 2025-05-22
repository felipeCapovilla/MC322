package Console;

import ambiente.*;
import constantes.TipoObstaculo;
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

    private int scannerNumber(){
        int resposta=0;
        boolean passou = false;

        while (!passou) { 
            try {
                resposta = scanner.nextInt();
                passou = true;
            } catch (Exception e) {
                System.out.println("Insira um numero");
                scanner.nextLine();
            }
            
        }        

        return resposta;
    }

    ///////MENU PRINCIPAL
    /**
     * Menu principal do terminal
     */
    public void mainMenu(){
        int resposta;
        boolean running = true;

        System.out.println("+----------------------------------+");
        System.out.println("|        SIMULADOR DE ROBOS        |");
        


        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Ver Ambiente                 |");
            System.out.println("| [2] Ver Robos                    |");
            System.out.println("| [99] Sair                        |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

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
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");;
            }

        }

        scanner.close();
    }

    ///////MENU AMBIENTE
    /**
     * Menu relacionado ao ambiente
     */
    private void ambienteMenu(){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Visualizar Ambiente          |");
            System.out.println("| [2] Info                         |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

            switch (resposta) {
                case 1:
                    //Visualizar ambiente
                    System.out.print("altura desejada: ");
                    resposta = scannerNumber();

                    ambiente.visualizarAmbiente(resposta);

                    break;
                case 2:
                    //informações do ambiente
                    System.out.printf("- Dimensões (C x L x A): %d x %d x %d\n", ambiente.get_comprimento(), ambiente.get_largura(), ambiente.get_altura());
                    System.out.println("- Lista de obstáculos:");

                    //TODO otimizar
                    for(Obstaculo obst : ambiente.getObstaculos()){
                        System.out.printf("\t ->%S\n", obst);
                    }

                    System.out.println("- Lista de Robos:");

                    for(Robo robo : ambiente.getListaRobos()){
                        System.out.printf("\t ->%S\n", robo);
                    }

                    break;
                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }


    ///////MENU ROBO
    /**
     * Formata uma String para ter exatamente o tamanho inserido, para mais(inserindo espaços) ou para menos
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
        boolean running = true;

        while(running){
            
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
            resposta = scannerNumber();

            if(resposta > 0 && resposta <= ambiente.get_robos_ativos()){
                rotearRobo(ambiente.getListaRobos().get(resposta-1));

            } else if(resposta == 99){
                running = false;
            } else{
                System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }

    /**
     * Leva ao menu de cada robo
     * @param _robo
     */
    private void rotearRobo(Robo _robo){
        //Instanciar o robo na classe correta
        if(_robo.getClass() == RoboAereo.class){
            controleRobo((RoboAereo) _robo);

        } else if(_robo.getClass() == RoboVoadorExplorador.class){
            controleRobo((RoboVoadorExplorador) _robo);

        } else if(_robo.getClass() == RoboVoadorTurista.class){
            controleRobo((RoboVoadorTurista) _robo);

        } else if(_robo.getClass() == RoboTerrestre.class){
            controleRobo((RoboTerrestre) _robo);

        } else if(_robo.getClass() == RoboVeiculo.class){
            controleRobo((RoboVeiculo) _robo);

        } else if(_robo.getClass() == RoboPedestre.class){
            controleRobo((RoboPedestre) _robo);

        }
    }

    /**
     * Menu que controla o RoboAereoStandart
     * @param robo
     */
    private void controleRobo(RoboAereo robo){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Subir/Descer Robo            |");
            System.out.println("| [3] Visualizar arredores         |");
            System.out.println("| [4] Monitorar sensores           |");
            System.out.println("| [5] Info                         |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scannerNumber();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scannerNumber();

                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    //Subir/Descer robo
                    System.out.print("Deslocamento altitude: ");
                    resposta = scannerNumber();

                    try {
                        if(resposta > 0){
                            robo.subir(resposta);
                        } else {
                            robo.descer(-resposta);
                        }    
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                
                    break;

                case 3:
                    //Visualizar arredores
                    try {
                        System.out.println("Espaco: ");
                        robo.get_SensorEspacial().monitorarPlano(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                        System.out.println("");
                        robo.get_SensorEspacial().monitorarAltura(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());                    
                    } catch (Exception e) {
                        System.out.println("Sensor espacial nao instalado");
                    }

                    break;

                case 4:
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
                    if(robo.get_SensorEspacial() == null){
                        System.out.println("Sensor espacial nao instalado");
                    } else {
                        System.out.print("Sensor espacial:\n\t");
                        robo.get_SensorEspacial().monitorar();
                    }
                    break;

                case 5:
                    //Info
                    System.out.println("Nome: " + robo.getID());
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

                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }

    /**
     * Menu que controla o RoboAereoExplorador
     * @param robo
     */
    private void controleRobo(RoboVoadorExplorador robo){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Subir/Descer Robo            |");
            System.out.println("| [3] Visualizar arredores         |");
            System.out.println("| [4] Iniciar/Finalizar missao     |");
            System.out.println("| [5] Monitorar sensores           |");
            System.out.println("| [6] Info                         |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scannerNumber();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scannerNumber();

                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    //Subir/Descer robo
                    System.out.print("Deslocamento altitude: ");
                    resposta = scannerNumber();

                    try {
                        if(resposta > 0){
                            robo.subir(resposta);
                        } else {
                            robo.descer(-resposta);
                        }    
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                
                    break;

                case 3:
                    //Visualizar arredores
                    try {
                        System.out.println("Espaco: ");
                        robo.get_SensorEspacial().monitorarPlano(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                        System.out.println("");
                        robo.get_SensorEspacial().monitorarAltura(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                    } catch (Exception e) {
                        System.out.println("Sensor espacial nao instalado");
                    }

                    break;

                case 4:
                    //Inicia/finaliza uma missão
                    if(robo.status_missao()){
                        robo.finalizar_exploracao();
                        System.out.println("Missao finalizada");
                    } else {
                        int pressao, temperatura, velocidade;
                        String planeta;

                        System.out.print("Indicar planeta destino: ");
                        scanner.nextLine(); // Acontece automaticamente, o usuario nao consegue inserir uma entrada
                        planeta = scanner.nextLine();

                        System.out.print("Indicar velocidade:");
                        velocidade = scannerNumber();

                        System.out.print("Indicar temperatura:");
                        temperatura = scannerNumber();

                        System.out.print("Indicar pressao:");
                        pressao = scannerNumber();

                        try{
                            robo.iniciar_exploracao(pressao, temperatura, velocidade, planeta);

                            System.out.println("Missao iniciada");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        
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

                    if(robo.get_SensorEspacial() == null){
                        System.out.println("Sensor espacial nao instalado");
                    } else {
                        System.out.print("Sensor espacial:\n\t");
                        robo.get_SensorEspacial().monitorar();
                    }
                    break;

                case 6:
                    //Info
                    System.out.println("Nome: " + robo.getID());
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
                        System.out.printf("\tVelocidade atual: %dm/s\n", robo.get_velocidade());

                    } else {
                        System.out.println("Status da missao: INATIVO");
                    }


                    break;

                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }

    /**
     * Menu que controla o RoboAereoTurista
     * @param robo
     */
    private void controleRobo(RoboVoadorTurista robo){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Subir/Descer Robo            |");
            System.out.println("| [3] Visualizar arredores         |");
            System.out.println("| [4] Iniciar/Finalizar passeio    |");
            System.out.println("| [5] Monitorar sensores           |");
            System.out.println("| [6] Info                         |");

            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scannerNumber();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scannerNumber();
                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    
                    break;

                case 2:
                    //Subir/Descer robo
                    System.out.print("Deslocamento altitude: ");
                    resposta = scannerNumber();

                    try {
                        if(resposta > 0){
                            robo.subir(resposta);
                        } else {
                            robo.descer(-resposta);
                        }    
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                
                    break;

                case 3:
                    //Visualizar arredores
                    try {
                        System.out.println("Espaco: ");
                        robo.get_SensorEspacial().monitorarPlano(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                        System.out.println("");
                        robo.get_SensorEspacial().monitorarAltura(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                    } catch (Exception e) {
                        System.out.println("Sensor espacial nao instalado");
                    }

                    break;

                case 4:
                    //Iniciar/finalizar passeio
                    if(robo.get_status()){
                        robo.finalizar_passeio();
                        System.out.println("Passeio finalizado");
                    } else {
                        int passageiros;
                        String cidade;

                        System.out.print("Indicar cidade destino: ");
                        scanner.nextLine(); // Acontece automaticamente, o usuario nao consegue inserir uma entrada
                        cidade = scanner.nextLine();

                        System.out.print("Indicar numero de passageiros:");
                        passageiros = scannerNumber();

                        try{
                            robo.inciar_passeio(passageiros, cidade);
                            
                            System.out.println("Passeio iniciado");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        
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

                    if(robo.get_SensorEspacial() == null){
                        System.out.println("Sensor espacial nao instalado");
                    } else {
                        System.out.print("Sensor espacial:\n\t");
                        robo.get_SensorEspacial().monitorar();
                    }
                    break;

                case 6:
                    //Info
                    System.out.println("Nome: " + robo.getID());
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

                    if(robo.get_status()){
                        System.out.println("Status do passeio: ATIVO");
                        System.out.println("\tCidade turistica: " + robo.get_destino());
                        System.out.println("\tNumeros passageiros: "+ robo.get_numero_passageiros());

                    } else {
                        System.out.println("Status do passeio: INATIVO");
                    }

                    break;

                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }

    /**
     * Menu que controla o RoboTerrestreStandart
     * @param robo
     */
    private void controleRobo(RoboTerrestre robo){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Visualizar arredores         |");
            System.out.println("| [3] Mudar velocidade maxima      |");
            System.out.println("| [4] Monitorar sensores           |");
            System.out.println("| [5] Info                         |");
            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scannerNumber();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scannerNumber();

                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    //Visualizar arredores
                    try {
                        System.out.println("Espaco: ");
                        robo.get_SensorEspacial().monitorarPlano(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                        System.out.println("");
                        robo.get_SensorEspacial().monitorarAltura(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                    } catch (Exception e) {
                        System.out.println("Sensor espacial nao instalado");
                    }

                    break;

                case 3:
                    //Mudar velocidade máxima
                    System.out.print("Indicar nova velocidade maxima: ");
                    resposta = scannerNumber();

                    robo.setVelocidadeMaxima(resposta);

                    break;

                case 4:
                    //Monitora os sensores do robo
                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }

                    if(robo.get_SensorEspacial() == null){
                        System.out.println("Sensor espacial nao instalado");
                    } else {
                        System.out.print("Sensor espacial:\n\t");
                        robo.get_SensorEspacial().monitorar();
                    }
                    break;

                case 5:
                    //Info
                    System.out.println("Nome: " + robo.getID());
                    System.out.println("Modelo: " + robo.getClass().getSimpleName());
                    System.out.printf("Posicao atual: (%d,%d)\n", robo.get_posicao()[0], robo.get_posicao()[1]);
                    System.out.println("Direcao atual: " + robo.getDirecao());
                    System.out.printf("Velocidade maxima: %dm/s\n", robo.getVelocidadeMaxima());

                    try {
                        System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Temperatura: Sensor de temperatura nao instalado");
                    }
                    break;

                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }

    /**
     * Menu que controla o RoboTerrestreVeiculo
     * @param robo
     */
    private void controleRobo(RoboVeiculo robo){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Mudar velocidade             |");
            System.out.println("| [3] Virar direcao                |");
            System.out.println("| [4] Visualizar arredores         |");
            System.out.println("| [5] Mudar velocidade maxima      |");
            System.out.println("| [6] Mudar numero de passageiros  |");
            System.out.println("| [7] Monitorar sensores           |");
            System.out.println("| [8] Info                         |");

            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

            switch (resposta) {
                case 1:
                    //Mover robo
                    String moverFrenteResposta;

                    System.out.print("Mover para frente? (Y/N) ");
                    scanner.nextLine();
                    moverFrenteResposta = scanner.nextLine();

                    try{
                        if(moverFrenteResposta.toLowerCase().equals("y")){
                            robo.mover(true); //andar para frente

                            System.out.printf("Robo se moveu %dm para frente na direcao %s\n", robo.getVelocidade(), robo.getDirecao());

                        } else if(moverFrenteResposta.toLowerCase().equals("n")){
                            robo.mover(false); //andar de ré

                            System.out.printf("Robo se moveu %dm de re na direcao %s\n", robo.getVelocidade(), robo.getDirecao());

                        } else {
                            System.out.println("Opcao nao disponivel");
                        }
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    

                    break;

                case 2:
                    //Mudar velocidade
                    System.out.print("Indicar nova velocidade: ");
                    resposta = scannerNumber();

                    robo.mudarVelocidade(resposta);

                    break;

                case 3:
                    //Mudar direção
                    String virar;
                    System.out.println("Direcao atual: " + robo.getDirecao());
                    System.out.print("Virar para esquerda (E) ou direita (D): ");
                    scanner.nextLine();
                    virar = scanner.nextLine();


                    if(virar.toLowerCase().equals("e")){
                        robo.virar(false);

                    } else if(virar.toLowerCase().equals("d")){
                        robo.virar(true);

                    } else {
                        System.out.println("Opcao nao disponivel");
                    }

                    break;

                case 4:
                    //Visualizar arredores
                    try {
                        System.out.println("Espaco: ");
                        robo.get_SensorEspacial().monitorarPlano(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                        System.out.println("");
                        robo.get_SensorEspacial().monitorarAltura(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                    } catch (Exception e) {
                        System.out.println("Sensor espacial nao instalado");
                    }

                    break;

                case 5:
                    //Mudar velocidade máxima
                    System.out.print("Indicar nova velocidade maxima: ");
                    resposta = scannerNumber();

                    robo.setVelocidadeMaxima(resposta);

                    break;

                case 6:
                    //Mudar quantidade de passageiros
                    System.out.print("Quantidade de passageiros saindo (negativo) ou entrando (positivo): ");
                    resposta = scannerNumber();

                    if(resposta < 0){
                        robo.passageirosSair(-resposta);
                    } else {
                        robo.passageirosEntrar(resposta);
                    }

                    break;

                case 7:
                    //Monitora os sensores do robo
                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }

                    if(robo.get_SensorEspacial() == null){
                        System.out.println("Sensor espacial nao instalado");
                    } else {
                        System.out.print("Sensor espacial:\n\t");
                        robo.get_SensorEspacial().monitorar();
                    }
                    break;

                case 8:
                    //Info
                    System.out.println("Nome: " + robo.getID());
                    System.out.println("Modelo: " + robo.getClass().getSimpleName());
                    System.out.printf("Posicao atual: (%d,%d)\n", robo.get_posicao()[0], robo.get_posicao()[1]);
                    System.out.println("Direcao atual: " + robo.getDirecao());
                    System.out.printf("Velocidade atual: %dm/s\n", robo.getVelocidade());
                    System.out.printf("Velocidade maxima: %dm/s\n", robo.getVelocidadeMaxima());
                    System.out.println("Numeros passageiros: "+ robo.getPassageiros());

                    try {
                        System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Temperatura: Sensor de temperatura nao instalado");
                    }
                    break;

                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }


    /**
     * Menu que controla o RoboTerrestrePedestre
     * @param robo
     */
    private void controleRobo(RoboPedestre robo){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Visualizar arredores         |");
            System.out.println("| [3] Mudar velocidade maxima      |");
            System.out.println("| [4] Monitorar sensores           |");
            System.out.println("| [5] Iniciar Tarefa               |");
            System.out.println("| [6] Info                         |");

            System.out.println("| [99] Voltar                      |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = scannerNumber();

            switch (resposta) {
                case 1:
                    //Mover robo
                    int deltaX;
                    int deltaY;
                    String correr;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scannerNumber();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scannerNumber();
                    System.out.print("Correr? (Y/N)");
                    scanner.nextLine();
                    correr = scanner.nextLine();

                    try{
                        if(correr.toLowerCase().equals("y")){
                            robo.mover(true, deltaX, deltaY);

                        } else if(correr.toLowerCase().equals("n")){
                            robo.mover(false, deltaX, deltaY);

                        } else {
                            System.out.println("Opcao nao disponivel");
                        }
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    //Visualizar arredores
                    try {
                        System.out.println("Espaco: ");
                        robo.get_SensorEspacial().monitorarPlano(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                        System.out.println("");
                        robo.get_SensorEspacial().monitorarAltura(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                    } catch (Exception e) {
                        System.out.println("Sensor espacial nao instalado");
                    }

                    break;

                case 3:
                    //Mudar velocidade máxima
                    System.out.print("Indicar nova velocidade maxima: ");
                    resposta = scannerNumber();

                    robo.setVelocidadeMaxima(resposta);

                    break;

                case 4:
                    //Monitora os sensores do robo
                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }

                    if(robo.get_SensorEspacial() == null){
                        System.out.println("Sensor espacial nao instalado");
                    } else {
                        System.out.print("Sensor espacial:\n\t");
                        robo.get_SensorEspacial().monitorar();
                    }
                    break;

                case 5:
                    //Iniciar tarefa
                    if(!robo.isTarefaAtiva()){
                        robo.executarTarefa();
                    } else {
                        System.out.println("Tarefa já iniciada");
                    }                    

                    break;

                case 6:
                    //Info
                    System.out.println("Nome: " + robo.getID());
                    System.out.println("Modelo: " + robo.getClass().getSimpleName());
                    System.out.printf("Posicao atual: (%d,%d)\n", robo.get_posicao()[0], robo.get_posicao()[1]);
                    System.out.println("Direcao atual: " + robo.getDirecao());
                    System.out.printf("Velocidade maxima: %dm/s\n", robo.getVelocidadeMaxima());
                    System.out.printf("Peso atual: %dkg\n", robo.getPeso());

                    try {
                        System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                    } catch (Exception e) {
                        System.out.println("Temperatura: Sensor de temperatura nao instalado");
                    }

                    if(robo.isTarefaAtiva()){
                        System.out.println("Status da tarefa: ATIVO");
                        System.out.println("\tCaixas coletadas: " + robo.getCaixasPegas());
                        System.out.println("\tCaixas faltando: " + (robo.getCaixasTotal() - robo.getCaixasPegas()));

                        for(Obstaculo obst : ambiente.getObstaculos()){
                            if(obst.getTipoObstaculo() == TipoObstaculo.CAIXA){
                                System.out.println(String.format("\t- CAIXA em: (%d,%d)", obst.getX(), obst.getY()));
                            }
                        }

                        

                    } else {
                        System.out.println("Status da tarefa: INATIVO");
                    }


                    break;

                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        } 
        System.out.println("");
    }

}
