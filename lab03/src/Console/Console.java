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
                    running = false;
                    break;
                default:
                    System.out.println("Opcao nao disponivel");
            }

        }
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
    
                        if(pos[0] == x && pos[1] == y && listaRobos.get(i).get_altitude() == altura){
                            sprite = '@';
                        }
                    }
    
                    //Verificar obstáculo
                    ArrayList<Obstaculo> obstaculos = ambiente.getObstaculos();
                    for(int i = 0; sprite != 'X' && sprite != '@' && i < obstaculos.size(); i++){
                        if(obstaculos.get(i).estaDentro(x, y) && (obstaculos.get(i).getAltura() > altura)){
                            sprite = 'X';
                        }
                    }
    
                    System.out.print(sprite);
                }
                System.out.println(""); //Quebra de linha
            }

            System.out.println("LEGENDA");
            System.out.println(". - espaco vazio");
            System.out.println("X - obstaculo");
            System.out.println("@ - robo");



            System.out.println("");
        }

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
            resposta = scanner.nextInt();

            if(resposta > 0 && resposta <= ambiente.get_robos_ativos()){
                rotearRobo(ambiente.getListaRobos().get(resposta-1));

            } else if(resposta == 99){
                running = false;
            } else{
                System.out.println("Opcao nao disponivel");
            }

        };
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

        } else {
            controleRobo(_robo);
        }
    }

    /**
     * Menu que controla o RoboStandart
     * @param robo RoboStandart
     */
    private void controleRobo(Robo robo){
        int resposta;
        boolean running = true;

        while(running){
            
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Mover Robo                   |");
            System.out.println("| [2] Visualizar arredores         |");
            System.out.println("| [3] Monitorar sensores           |");
            System.out.println("| [4] Info                         |");
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

                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e);
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
                    //Monitora os sensores do robo
                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }
                    break;

                case 4:
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

                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
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
                    try {
                        System.out.println("Espaco: ");
                        robo.get_SensorEspacial().monitorarPlano(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());
                        System.out.println("");
                        robo.get_SensorEspacial().monitorarAltura(ambiente, robo.get_posicao()[0], robo.get_posicao()[1], (int) robo.get_altitude());                    } catch (Exception e) {
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
                    break;

                case 5:
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

                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
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
                        System.out.printf("\tVelocidade atual: %dm/s", robo.get_velocidade());

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
                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
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
                        passageiros = scanner.nextInt();

                        try{
                            robo.inciar_passeio(passageiros, cidade);
                            
                            System.out.println("Passeio iniciado");

                        } catch (Exception e) {
                            System.out.println(e);
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
                    break;

                case 6:
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

                    if(robo.get_status()){
                        System.out.println("Status do passeio: ATIVO");
                        System.out.println("\tCidade turistica: " + robo.get_destino());
                        System.out.println("\tNumeros passageiros: "+ robo.get_numero_passageiros());

                    } else {
                        System.out.println("Status do passeio: INATIVO");
                    }

                    break;

                case 99:
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

                    try {
                        robo.mover(deltaX, deltaY);
                    } catch (Exception e) {
                        System.out.println(e);
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
                    resposta = scanner.nextInt();

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
                    break;

                case 5:
                    //Info
                    System.out.println("Nome: " + robo.getNome());
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
            resposta = scanner.nextInt();

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
                        System.out.println(e);
                    }
                    

                    break;

                case 2:
                    //Mudar velocidade
                    System.out.print("Indicar nova velocidade: ");
                    resposta = scanner.nextInt();

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
                    resposta = scanner.nextInt();

                    robo.setVelocidadeMaxima(resposta);

                    break;

                case 6:
                    //Mudar quantidade de passageiros
                    System.out.print("Quantidade de passageiros saindo (negativo) ou entrando (positivo): ");
                    resposta = scanner.nextInt();

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
                    break;

                case 8:
                    //Info
                    System.out.println("Nome: " + robo.getNome());
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
            System.out.println("| [3] Mudar peso                   |");
            System.out.println("| [4] Mudar velocidade maxima      |");
            System.out.println("| [5] Monitorar sensores           |");
            System.out.println("| [6] Info                         |");

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
                    String correr;

                    System.out.print("Deslocamento horizontal: ");
                    deltaX = scanner.nextInt();
                    System.out.print("Deslocamento vertical: ");
                    deltaY = scanner.nextInt();
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
                        System.out.println(e);
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
                    //Mudar peso
                    System.out.print("Indicar novo peso: ");
                    resposta = scanner.nextInt();

                    robo.setPeso(resposta);

                    break;

                case 4:
                    //Mudar velocidade máxima
                    System.out.print("Indicar nova velocidade maxima: ");
                    resposta = scanner.nextInt();

                    robo.setVelocidadeMaxima(resposta);

                    break;

                case 5:
                    //Monitora os sensores do robo
                    if(robo.get_SensorTemperatura() == null){
                        System.out.println("Sensor de temperatura nao instalado");
                    } else {
                        System.out.print("Sensor de temperatura:\n\t");
                        robo.get_SensorTemperatura().monitorar();
                    }
                    break;

                case 6:
                    //Info
                    System.out.println("Nome: " + robo.getNome());
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
