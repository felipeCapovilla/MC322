package Console;

import ambiente.*;
import constantes.*;
import exceptions.*;
import interfaces.Comunicavel;
import iofiles.ScannerGlobal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import missao.*;
import robo.aereo.explorador.RoboVoadorExplorador;
import robo.aereo.standart.RoboAereo;
import robo.aereo.turista.RoboVoadorTurista;
import robo.agenteInteligente.AgenteInteligente;
import robo.standart.Robo;
import robo.terrestre.pedestre.RoboPedestre;
import robo.terrestre.standart.RoboTerrestre;
import robo.terrestre.veiculo.RoboVeiculo;

public class Console {
    final Ambiente ambiente;

    public Console(Ambiente ambiente){
        this.ambiente = ambiente;
    }

    //Métodos auxiliares
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

    ///////MENU PRINCIPAL
    /**
     * Menu principal do terminal
     */
    public void mainMenu(){
        int resposta;
        boolean running = true;

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Ver Ambiente", this::ambienteMenu));
        opcoesMenu.add(new MenuItem(2, "Ver Robos", ()->
            {
                System.out.println("Robos LIGADOS:");
                for(Robo robo : ambiente.getListaRobos()){
                    if(robo.getEstado() == EstadoRobo.LIGADO){
                        System.out.printf("\t -%s\n", robo);
                    }
                }

                System.out.println("Robos DESLIGADOS:");
                for(Robo robo : ambiente.getListaRobos()){
                    if(robo.getEstado() == EstadoRobo.DESLIGADO){
                        System.out.printf("\t -%s\n", robo);
                    }
                }

            })
        );
        opcoesMenu.add(new MenuItem(3, "Utilizar Robos", this::roboMenu));
        opcoesMenu.add(new MenuItem(4, "Visualizar Comunicacoes", ()->
            {
                ambiente.getComunicacao().exibirMensagens();
            })
        );

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        

        //Rodar Menu
        while(running){
            System.out.println("+----------------------------------+");
            System.out.println("|        SIMULADOR DE ROBOS        |");

            //Imprimir opções do menu
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            for(int i = 0; i<opcoesMenu.size(); i++){
                if(i < 9){
                    System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

                } else {
                    System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

                }
            }  
                     

            System.out.println("| [0] Sair                         |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = ScannerGlobal.scannerNumber();

            if(resposta == 0){
                //Voltar
                running = false;
            } else if (resposta <= opcoesMenu.size() && resposta > 0){
                //Opções
                Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                
                if (acao != null) {
                    acao.run();
                } else {
                    System.err.println("Ação não disponível");
                }

            } else {
                //Não existe
                System.out.println("Opcao nao disponivel");
            }

        }

        ScannerGlobal.close();
    }

    ///////MENU AMBIENTE
    /**
     * Menu relacionado ao ambiente
     */
    private void ambienteMenu(){
        int resposta;
        boolean running = true;

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Visualizar Ambiente", ()->
            {
                //Visualizar ambiente
                System.out.print("altura desejada: ");
                int resp = ScannerGlobal.scannerNumber();

                try {
                    ambiente.visualizarAmbiente(resp);
                } catch (PointOutOfMapException e) {
                    System.err.println("Altura inválida: " + e.getMessage());
                }
            }));
        opcoesMenu.add(new MenuItem(4, "Info", ()->
            {
                //informações do ambiente
                System.out.printf("- Dimensões (C x L x A): %d x %d x %d\n", ambiente.get_comprimento(), ambiente.get_largura(), ambiente.get_altura());
                System.out.println("- Lista de obstáculos:");

                for(Obstaculo obst : ambiente.getObstaculos()){
                    System.out.printf("\t ->%S\n", obst);
                }

                System.out.println("- Lista de Robos:");

                ArrayList<Robo> listaRobo = ambiente.getListaRobos();
                listaRobo.sort(Comparator.comparing(rob -> rob.getClass().getSimpleName())); //Ordena por tipo de robo
                for(Robo robo : listaRobo){
                    System.out.printf("\t ->%S\n", robo);
                }

            }));
        opcoesMenu.add(new MenuItem(2, "Verificar Sensores", ()->
            {
                //Verifica todos os sensores dos robos LIGADOS do ambiente
                ambiente.executarSensores();
            }));
        opcoesMenu.add(new MenuItem(3, "Verificar Colisões", ()->
            {
                //Verifica se há colisões no ambiente
                try {
                    ambiente.verificarColisoes();

                    System.out.println("Nao ha colisoes no ambiente");
                } catch (Exception e) {
                    System.out.println("Colisão em: " + e.getMessage());
                }
            }));

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        //Rodar Menu
        while(running){

            //Imprimir opções do menu
            System.out.println("+----------------------------------+");
            System.out.println("|         escolha um opcao         |");

            for(int i = 0; i<opcoesMenu.size(); i++){
                if(i < 9){
                    System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

                } else {
                    System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

                }
            }            

            System.out.println("| [0] Voltar                       |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = ScannerGlobal.scannerNumber();

            if(resposta == 0){
                //Voltar
                running = false;
            } else if (resposta <= opcoesMenu.size() && resposta > 0){
                //Opções
                Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                
                if (acao != null) {
                    acao.run();
                } else {
                    System.err.println("Ação não disponível");
                }

            } else {
                //Não existe
                System.out.println("Opcao nao disponivel");
            }

        }
        System.out.println("");
    }


    ///////MENU ROBO

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

            System.out.println("| [0] Voltar                       |");

            System.out.println("+----------------------------------+");

            //Resposta
            System.out.print("opcao escolhida: ");
            resposta = ScannerGlobal.scannerNumber();

            if(resposta > 0 && resposta <= ambiente.get_robos_ativos()){
                rotearRobo(ambiente.getListaRobos().get(resposta-1));

            } else if(resposta == 0){
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
     * Menu de Controle Robo quando desligado
     */
    private boolean controleRoboDesligado(Robo robo){
        int resposta;
        boolean running = true;

        System.out.println("+----------------------------------+");
        System.out.println("|         escolha um opcao         |");

        //Opções
        System.out.println("| [1] Ligar Robo                   |");
        System.out.println("| [2] Info                         |");
        System.out.println("| [0] Voltar                       |");

        System.out.println("+----------------------------------+");

        //Resposta
        System.out.print("opcao escolhida: ");
        resposta = ScannerGlobal.scannerNumber();

        switch (resposta) {
            case 1:
                //Ligar Robo
                robo.ligar();
                System.out.println("Robo Ligado");
                break;
            case 2:
                //INFO
                System.out.println("Status: " + robo.getEstado());
                break;
            case 0:
                running = false;
                break;
            default:
                System.out.println("Opcao nao disponivel");
                break;
        }

        return running;
    }

    /**
     * Menu para comunicar com outro robo
     * @param robo
     */
    private void acaoComunicavel(Comunicavel robo){
        int resposta;

        System.out.println("+----------------------------------+");
            System.out.println("| escolha um robo para comunicar  |");

            //Opções
            ArrayList<Robo> robosComunicavel = ambiente.getListaRobos().stream()
                .filter(roboAtual -> ((roboAtual instanceof Comunicavel) && !roboAtual.equals(robo)))
                .collect(Collectors.toCollection(ArrayList::new));
            for(int i = 0; i < robosComunicavel.size(); i++){
                System.out.printf("| [%d] %s|\n", i+1, formatString(robosComunicavel.get(i).toString(), 29));

            }

            System.out.println("+----------------------------------+");

        //Resposta
        System.out.print("opcao escolhida: ");
        resposta = ScannerGlobal.scannerNumber();

        if(resposta >0 && resposta <= robosComunicavel.size()){
            System.out.println("Digite a mensagem:");
            String mensagem = ScannerGlobal.nextLine();
            
            try {
                robo.enviarMensagem((Comunicavel) robosComunicavel.get(resposta-1), mensagem);

                System.out.println("Mensagem enviada com sucesso!");
    
            } catch (ErroComunicacaoException e) {
                //Não é para ocorrer
                System.err.println(e.getMessage());
            }


        } else {
            System.out.println("Opcao nao disponivel");
        }

    }

    /**
     * Menu para selecionar missao
     */
    private void acaoMissao(AgenteInteligente robo){
        //Instanciar as opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Buscar Ponto", ()->
            {
                //Executar Missao Buscar Ponto
                robo.definirMissao(new MissaoBuscarPonto());
                robo.executarMissao(ambiente);

        }));
        opcoesMenu.add(new MenuItem(2, "Patrulhar", ()->
            {
                //Executar Missao Buscar Ponto
                robo.definirMissao(new MissaoPatrulhar());
                robo.executarMissao(ambiente);

        }));
        opcoesMenu.add(new MenuItem(3, "Monitorar", ()->
            {
                //Executar Missao Buscar Ponto
                robo.definirMissao(new MissaoMonitorar());
                robo.executarMissao(ambiente);

        }));

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice


        int resposta;

        System.out.println("+----------------------------------+");
        System.out.println("|        escolha uma missao        |");

            //Opções
        for(int i = 0; i<opcoesMenu.size(); i++){
            if(i < 9){
                System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

            } else {
                System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

            }
        }            

        System.out.println("+----------------------------------+");

        //Resposta
        System.out.print("opcao escolhida: ");
        resposta = ScannerGlobal.scannerNumber();

        if (resposta <= opcoesMenu.size() && resposta > 0){
            //Opções
            Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
            
            if (acao != null) {
                try {
                    acao.run();
                } catch (Exception e) {
                     System.err.println("Erro ao executar a missão: " + e.getMessage());
                }
                
            } else {
                System.err.println("Ação não disponível");
            }

        } else {
            //Não existe
            System.out.println("Opcao nao disponivel");
        }

    }


    /**
     * Menu que controla o RoboAereoStandart
     * @param robo
     */
    private void controleRobo(RoboAereo robo){
        int resposta;
        boolean running = true;

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Mover Robo", ()->
            {
                //Mover robo
                int deltaX;
                int deltaY;

                System.out.print("Deslocamento horizontal: ");
                deltaX = ScannerGlobal.scannerNumber();
                System.out.print("Deslocamento vertical: ");
                deltaY = ScannerGlobal.scannerNumber();

                try {
                    robo.mover(deltaX, deltaY);
                    
                } catch(LowBatteryException e){ 
                    System.err.println("Robo com baixa bateria: " + e.getMessage());
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(2, "Subir/Descer Robo", ()->
            {
                //Subir/Descer robo
                System.out.print("Deslocamento altitude: ");
                int resp = ScannerGlobal.scannerNumber();

                try {
                    if(resp > 0){
                        robo.subir(resp);
                    } else {
                        robo.descer(-resp);
                    }
                     
                } catch(LowBatteryException e){ 
                    System.err.println("Robo com baixa bateria: " + e.getMessage());
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch(SensorMissingException e){ 
                    System.err.println(e.getMessage());
                } catch(ValueOutOfBoundsException e){ 
                    System.err.println("Sensor nao instalado: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(3, "Visualizar arredores", ()->
            {
                //Visualizar arredores
                try {
                    System.out.println("Espaco: ");
                    robo.get_SensorEspacial().monitorarPlano(ambiente, robo.getX(), robo.getY(), (int) robo.get_altitude());
                    System.out.println("");
                    robo.get_SensorEspacial().monitorarAltura(ambiente, robo.getX(), robo.getY(), (int) robo.get_altitude());                    
                }  catch(NullPointerException e){ 
                    System.out.println("Sensor espacial nao instalado");
                } catch (PointOutOfMapException | RoboDesligadoException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(4, "Monitorar sensores", ()->
            {
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

            }));
        opcoesMenu.add(new MenuItem(5, "Carregar", ()->
            {
                //Carregar
                robo.carregar();
            }));
        opcoesMenu.add(new MenuItem(6, "Executar Missao", ()->
            {
                //Executar missão autônoma
                acaoMissao(robo);
        }));
        opcoesMenu.add(new MenuItem(7, "Info", ()->
            {
                //Info
                System.out.println("Status: " + robo.getEstado());
                System.out.println("Nome: " + robo.getID());
                System.out.println("Modelo: " + robo.getClass().getSimpleName());
                System.out.printf("Posicao atual: (%d,%d)\n", robo.getX(), robo.getY());
                System.out.println("Direcao atual: " + robo.getDirecao());
                System.out.println("Bateria: " + robo.getBateria() + "%");
                
                try {
                    System.out.printf("Altitude: (%.2f\u00b1%.2f)m\n", robo.get_SensorAltitude().get_altitude(), robo.get_SensorAltitude().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Altitude: Sensor de altitude nao instalado");
                }
                try {
                    System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Temperatura: Sensor de temperatura nao instalado");
                }

            }));
        opcoesMenu.add(new MenuItem(8, "Desligar Robo", ()->
            {
                //Desligar Robo
                robo.desligar();
                System.out.println("Robo Desligado");

            }));

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        //Rodar Menu
        while(running){
            //Imprimir opções do menu

            if(!robo.isLigado()){
                //Menu robo desligado
                running = controleRoboDesligado(robo);
            } else {
                //Menu Robo ligado
                System.out.println("+----------------------------------+");
                System.out.println("|         escolha um opcao         |");

                for(int i = 0; i<opcoesMenu.size(); i++){
                    if(i < 9){
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

                    } else {
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

                    }
                }            

                System.out.println("| [0] Sair                         |");

                System.out.println("+----------------------------------+");

                //Resposta
                System.out.print("opcao escolhida: ");
                resposta = ScannerGlobal.scannerNumber();

                if(resposta == 0){
                    //Voltar
                    running = false;
                } else if (resposta <= opcoesMenu.size() && resposta > 0){
                    //Opções
                    Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                    
                    if (acao != null) {
                        acao.run();
                    } else {
                        System.err.println("Ação não disponível");
                    }

                } else {
                    //Não existe
                    System.out.println("Opcao nao disponivel");
                }
            
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

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Mover Robo", ()->
            {
                //Mover robo
                int deltaX;
                int deltaY;

                System.out.print("Deslocamento horizontal: ");
                deltaX = ScannerGlobal.scannerNumber();
                System.out.print("Deslocamento vertical: ");
                deltaY = ScannerGlobal.scannerNumber();

                try {
                    robo.mover(deltaX, deltaY);
                    
                } catch(LowBatteryException e){ 
                    System.err.println("Robo com baixa bateria: " + e.getMessage());
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(2, "Subir/Descer Robo", ()->
            {
                //Subir/Descer robo
                System.out.print("Deslocamento altitude: ");
                int resp = ScannerGlobal.scannerNumber();

                try {
                    if(resp > 0){
                        robo.subir(resp);
                    } else {
                        robo.descer(-resp);
                    }
                     
                } catch(LowBatteryException e){ 
                    System.err.println("Robo com baixa bateria: " + e.getMessage());
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch(SensorMissingException e){ 
                    System.err.println("Sensor nao instalado: " + e.getMessage());
                } catch (Exception e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(3, "Visualizar arredores", ()->
            {
                //Visualizar arredores
                try {
                    System.out.println("Espaco: ");
                    robo.get_SensorEspacial().monitorarPlano(ambiente, robo.getX(), robo.getY(), (int) robo.get_altitude());
                    System.out.println("");
                    robo.get_SensorEspacial().monitorarAltura(ambiente, robo.getX(), robo.getY(), (int) robo.get_altitude());                    
                }  catch(NullPointerException e){ 
                    System.out.println("Sensor espacial nao instalado");
                } catch (PointOutOfMapException | RoboDesligadoException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));

        opcoesMenu.add(new MenuItem(4, "Iniciar Tarefa", ()->
            {
                //Iniciar tarefa
                if(!robo.isTarefaAtiva()){
                    int pressao, temperatura, velocidade;
                    String planeta;

                    System.out.print("Indicar planeta destino: ");
                    planeta = ScannerGlobal.nextLine();

                    System.out.print("Indicar velocidade:");
                    velocidade = ScannerGlobal.scannerNumber();

                    System.out.print("Indicar temperatura:");
                    temperatura = ScannerGlobal.scannerNumber();

                    System.out.print("Indicar pressao:");
                    pressao = ScannerGlobal.scannerNumber();

                    try{
                        robo.iniciar_exploracao(pressao, temperatura, velocidade, planeta);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } 

                    robo.executarTarefa();

                } else {
                    System.out.println("Tarefa já iniciada");
                }  

            }));
        opcoesMenu.add(new MenuItem(5, "Monitorar sensores", ()->
            {
                if(robo.isStatusSensores()){
                    
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
                } else {
                    System.out.println("Sensores desligados");
                }


            }));
        opcoesMenu.add(new MenuItem(6, "Carregar", ()->
            {
                //Carregar
                robo.carregar();
            }));
        opcoesMenu.add(new MenuItem(7, "Executar Missao", ()->
            {
                //Executar missão autônoma
                acaoMissao(robo);
        }));
        opcoesMenu.add(new MenuItem(9, "Info", ()->
            {
                //Info
                System.out.println("Status: " + robo.getEstado());
                System.out.println("Nome: " + robo.getID());
                System.out.println("Modelo: " + robo.getClass().getSimpleName());
                System.out.printf("Posicao atual: (%d,%d)\n", robo.getX(), robo.getY());
                System.out.println("Direcao atual: " + robo.getDirecao());
                System.out.println("Bateria: " + robo.getBateria() + "%");
                
                try {
                    System.out.printf("Altitude: (%.2f\u00b1%.2f)m\n", robo.get_SensorAltitude().get_altitude(), robo.get_SensorAltitude().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Altitude: Sensor de altitude nao instalado");
                }
                try {
                    System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Temperatura: Sensor de temperatura nao instalado");
                }

                if(robo.isTarefaAtiva()){
                    System.out.println("Status da missao: ATIVO");
                    System.out.println("\tPlaneta destino: " + robo.get_planeta());
                    System.out.printf("\tPressao atual: %dkPa\n", robo.get_pressao());
                    System.out.printf("\tVelocidade atual: %dm/s\n", robo.get_velocidade());
                    System.out.printf("\tChegada em: (%d,%d,%d)\n", robo.getChegada()[0], robo.getChegada()[1], robo.getChegada()[2]);

                } else {
                    System.out.println("Status da missao: INATIVO");
                }

            }));
        opcoesMenu.add(new MenuItem(11, "Desligar Robo", ()->
            {
                //Desligar Robo
                robo.desligar();
                System.out.println("Robo Desligado");

            }));
        opcoesMenu.add(new MenuItem(10, "Desligar/Ligar Sensores", ()->
            {
                //Desligar/Ligar sensores Robo
                if(robo.isStatusSensores()){
                    robo.desligaSensores();
                    System.out.println("Sensores desligados");
                } else {
                    robo.acionarSensores();
                    System.out.println("Sensores ligados");
                }

            }));
        opcoesMenu.add(new MenuItem(8, "Enviar mensagem", ()->
            {
                acaoComunicavel(robo);
            })
        );

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        //Rodar Menu
        while(running){
            //Imprimir opções do menu

            if(!robo.isLigado()){
                //Menu robo desligado
                running = controleRoboDesligado(robo);
            } else {
                //Menu Robo ligado
                System.out.println("+----------------------------------+");
                System.out.println("|         escolha um opcao         |");

                for(int i = 0; i<opcoesMenu.size(); i++){
                    if(i < 9){
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

                    } else {
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

                    }
                }            

                System.out.println("| [0] Sair                         |");

                System.out.println("+----------------------------------+");

                //Resposta
                System.out.print("opcao escolhida: ");
                resposta = ScannerGlobal.scannerNumber();

                if(resposta == 0){
                    //Voltar
                    running = false;
                } else if (resposta <= opcoesMenu.size() && resposta > 0){
                    //Opções
                    Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                    
                    if (acao != null) {
                        acao.run();
                    } else {
                        System.err.println("Ação não disponível");
                    }

                } else {
                    //Não existe
                    System.out.println("Opcao nao disponivel");
                }
            
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

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Mover Robo", ()->
            {
                //Mover robo
                int deltaX;
                int deltaY;

                System.out.print("Deslocamento horizontal: ");
                deltaX = ScannerGlobal.scannerNumber();
                System.out.print("Deslocamento vertical: ");
                deltaY = ScannerGlobal.scannerNumber();

                try {
                    robo.mover(deltaX, deltaY);
                    
                } catch(LowBatteryException e){ 
                    System.err.println("Robo com baixa bateria: " + e.getMessage());
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(2, "Subir/Descer Robo", ()->
            {
                //Subir/Descer robo
                System.out.print("Deslocamento altitude: ");
                int resp = ScannerGlobal.scannerNumber();

                try {
                    if(resp > 0){
                        robo.subir(resp);
                    } else {
                        robo.descer(-resp);
                    }
                     
                } catch(LowBatteryException e){ 
                    System.err.println("Robo com baixa bateria: " + e.getMessage());
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch(SensorMissingException e){ 
                    System.err.println("Sensor nao instalado: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(3, "Visualizar arredores", ()->
            {
                //Visualizar arredores
                try {
                    System.out.println("Espaco: ");
                    robo.get_SensorEspacial().monitorarPlano(ambiente, robo.getX(), robo.getY(), (int) robo.get_altitude());
                    System.out.println("");
                    robo.get_SensorEspacial().monitorarAltura(ambiente, robo.getX(), robo.getY(), (int) robo.get_altitude());                    
                }  catch(NullPointerException e){ 
                    System.out.println("Sensor espacial nao instalado");
                } catch (PointOutOfMapException | RoboDesligadoException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(4, "Iniciar Tarefa", ()->
            {
                //Iniciar tarefa
                if(!robo.isTarefaAtiva()){
                    int passageiros;
                    String cidade;

                    System.out.print("Indicar cidade destino: ");
                    cidade = ScannerGlobal.nextLine();

                    System.out.print("Indicar numero de passageiros:");
                    passageiros = ScannerGlobal.scannerNumber();

                    try{
                        robo.inciar_passeio(passageiros, cidade);
                        
                        System.out.println("Passeio iniciado");

                    } catch (ValueOutOfBoundsException e) {
                        System.out.println(e.getMessage());
                    }


                    robo.executarTarefa();
                } else {
                    System.out.println("Tarefa já iniciada");
                }  

            }));
        
        opcoesMenu.add(new MenuItem(5, "Monitorar sensores", ()->
            {
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

            }));
        opcoesMenu.add(new MenuItem(6, "Carregar", ()->
            {
                //Carregar
                robo.carregar();
            }));
        opcoesMenu.add(new MenuItem(7, "Executar Missao", ()->
            {
                //Executar missão autônoma
                acaoMissao(robo);
        }));
        opcoesMenu.add(new MenuItem(8, "Info", ()->
            {
                //Info
                System.out.println("Status: " + robo.getEstado());
                System.out.println("Nome: " + robo.getID());
                System.out.println("Modelo: " + robo.getClass().getSimpleName());
                System.out.printf("Posicao atual: (%d,%d)\n", robo.getX(), robo.getY());
                System.out.println("Direcao atual: " + robo.getDirecao());
                System.out.println("Bateria: " + robo.getBateria() + "%");
                
                try {
                    System.out.printf("Altitude: (%.2f\u00b1%.2f)m\n", robo.get_SensorAltitude().get_altitude(), robo.get_SensorAltitude().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Altitude: Sensor de altitude nao instalado");
                }
                try {
                    System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Temperatura: Sensor de temperatura nao instalado");
                }

                if(robo.isTarefaAtiva()){
                    System.out.println("Status do passeio: ATIVO");
                    System.out.println("\tCidade turistica: " + robo.get_destino());
                    System.out.println("\tNumeros passageiros: "+ robo.get_numero_passageiros());
                    System.out.printf("\tChegada em: (%d,%d,%d)\n", robo.getChegada()[0], robo.getChegada()[1], robo.getChegada()[2]);

                } else {
                    System.out.println("Status do passeio: INATIVO");
                }

            }));
        opcoesMenu.add(new MenuItem(9, "Desligar Robo", ()->
            {
                //Desligar Robo
                robo.desligar();
                System.out.println("Robo Desligado");

            }));

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        //Rodar Menu
        while(running){
            //Imprimir opções do menu

            if(!robo.isLigado()){
                //Menu robo desligado
                running = controleRoboDesligado(robo);
            } else {
                //Menu Robo ligado
                System.out.println("+----------------------------------+");
                System.out.println("|         escolha um opcao         |");

                for(int i = 0; i<opcoesMenu.size(); i++){
                    if(i < 9){
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

                    } else {
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

                    }
                }             

                System.out.println("| [0] Sair                         |");

                System.out.println("+----------------------------------+");

                //Resposta
                System.out.print("opcao escolhida: ");
                resposta = ScannerGlobal.scannerNumber();

                if(resposta == 0){
                    //Voltar
                    running = false;
                } else if (resposta <= opcoesMenu.size() && resposta > 0){
                    //Opções
                    Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                    
                    if (acao != null) {
                        acao.run();
                    } else {
                        System.err.println("Ação não disponível");
                    }

                } else {
                    //Não existe
                    System.out.println("Opcao nao disponivel");
                }
            
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

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Mover Robo", ()->
            {
                //Mover robo
                int deltaX;
                int deltaY;

                System.out.print("Deslocamento horizontal: ");
                deltaX = ScannerGlobal.scannerNumber();
                System.out.print("Deslocamento vertical: ");
                deltaY = ScannerGlobal.scannerNumber();

                try {
                    robo.mover(deltaX, deltaY);
                    
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(3, "Mudar velocidade maxima", ()->
            {
                //Mudar velocidade máxima
                System.out.print("Indicar nova velocidade maxima: ");
                int resp = ScannerGlobal.scannerNumber();

                robo.setVelocidadeMaxima(resp);

            }));
        opcoesMenu.add(new MenuItem(2, "Visualizar arredores", ()->
            {
                //Visualizar arredores
                try {
                    System.out.println("Espaco: ");
                    robo.get_SensorEspacial().monitorarPlano(ambiente, robo.getX(), robo.getY(), (int) robo.getZ());
                    System.out.println("");
                    robo.get_SensorEspacial().monitorarAltura(ambiente, robo.getX(), robo.getY(), (int) robo.getZ());                    
                }  catch(NullPointerException e){ 
                    System.out.println("Sensor espacial nao instalado");
                } catch (PointOutOfMapException | RoboDesligadoException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(4, "Monitorar sensores", ()->
            {
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

            }));

        opcoesMenu.add(new MenuItem(6, "Info", ()->
            {
                //Info
                System.out.println("Status: " + robo.getEstado());
                System.out.println("Nome: " + robo.getID());
                System.out.println("Modelo: " + robo.getClass().getSimpleName());
                System.out.printf("Posicao atual: (%d,%d)\n", robo.getX(), robo.getY());
                System.out.println("Direcao atual: " + robo.getDirecao());
                System.out.printf("Velocidade maxima: %dm/s\n", robo.getVelocidadeMaxima());

                try {
                    System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Temperatura: Sensor de temperatura nao instalado");
                }

            }));
        opcoesMenu.add(new MenuItem(7, "Desligar Robo", ()->
            {
                //Desligar Robo
                robo.desligar();
                System.out.println("Robo Desligado");

            }));
        opcoesMenu.add(new MenuItem(5, "Enviar mensagem", ()->
            {
                acaoComunicavel(robo);
            })
        );

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        //Rodar Menu
        while(running){
            //Imprimir opções do menu

            if(!robo.isLigado()){
                //Menu robo desligado
                running = controleRoboDesligado(robo);
            } else {
                //Menu Robo ligado
                System.out.println("+----------------------------------+");
                System.out.println("|         escolha um opcao         |");

                for(int i = 0; i<opcoesMenu.size(); i++){
                    if(i < 9){
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

                    } else {
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

                    }
                }             

                System.out.println("| [0] Sair                         |");

                System.out.println("+----------------------------------+");

                //Resposta
                System.out.print("opcao escolhida: ");
                resposta = ScannerGlobal.scannerNumber();

                if(resposta == 0){
                    //Voltar
                    running = false;
                } else if (resposta <= opcoesMenu.size() && resposta > 0){
                    //Opções
                    Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                    
                    if (acao != null) {
                        acao.run();
                    } else {
                        System.err.println("Ação não disponível");
                    }

                } else {
                    //Não existe
                    System.out.println("Opcao nao disponivel");
                }
            
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

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Mover Robo", ()->
            {
                //Mover robo
                String moverFrenteResposta;

                System.out.print("Mover para frente? (Y/N) ");
                moverFrenteResposta = ScannerGlobal.nextLine();

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
                } catch(ZeroLifePointsException e){ 
                    System.err.println("Robo com vida baixa");
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(5, "Mudar velocidade maxima", ()->
            {
                //Mudar velocidade máxima
                System.out.print("Indicar nova velocidade maxima: ");
                int resp = ScannerGlobal.scannerNumber();

                robo.setVelocidadeMaxima(resp);

            }));
        opcoesMenu.add(new MenuItem(4, "Visualizar arredores", ()->
            {
                //Visualizar arredores
                try {
                    System.out.println("Espaco: ");
                    robo.get_SensorEspacial().monitorarPlano(ambiente, robo.getX(), robo.getY(), (int) robo.getZ());
                    System.out.println("");
                    robo.get_SensorEspacial().monitorarAltura(ambiente, robo.getX(), robo.getY(), (int) robo.getZ());                    
                }  catch(NullPointerException e){ 
                    System.out.println("Sensor espacial nao instalado");
                } catch (PointOutOfMapException | RoboDesligadoException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(10, "Monitorar sensores", ()->
            {
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

            }));

        opcoesMenu.add(new MenuItem(12, "Info", ()->
            {
                //Info
                System.out.println("Status: " + robo.getEstado());
                System.out.println("Nome: " + robo.getID());
                System.out.println("Modelo: " + robo.getClass().getSimpleName());
                System.out.printf("Posicao atual: (%d,%d)\n", robo.getX(), robo.getY());
                System.out.println("Direcao atual: " + robo.getDirecao());
                System.out.printf("Velocidade atual: %dm/s\n", robo.getVelocidade());
                System.out.printf("Velocidade maxima: %dm/s\n", robo.getVelocidadeMaxima());
                System.out.println("Numeros passageiros: "+ robo.getPassageiros());
                System.out.println("Vida: " + robo.getVida());

                try {
                    System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Temperatura: Sensor de temperatura nao instalado");
                }

                if(robo.isTarefaAtiva()){
                    System.out.println("Status da tarefa: ATIVO");
                    System.out.println(String.format("\tAtaques bem-sucedidos: %d/%d", robo.getAtkSucesso(), robo.getAtkTotal()));

                } else {
                    System.out.println("Status da tarefa: INATIVO");
                }

            }));
        opcoesMenu.add(new MenuItem(13, "Desligar Robo", ()->
            {
                //Desligar Robo
                robo.desligar();
                System.out.println("Robo Desligado");

            }));
        opcoesMenu.add(new MenuItem(2, "Mudar velocidade", ()->
            {
                //Mudar velocidade
                System.out.print("Indicar nova velocidade: ");
                int resp = ScannerGlobal.scannerNumber();

                robo.mudarVelocidade(resp);

            }));
        opcoesMenu.add(new MenuItem(3, "Virar direcao", ()->
            {
                //Mudar direção
                String virar;
                System.out.println("Direcao atual: " + robo.getDirecao());
                System.out.print("Virar para esquerda (E) ou direita (D): ");
                virar = ScannerGlobal.nextLine();


                if(virar.toLowerCase().equals("e")){
                    robo.virar(false);

                } else if(virar.toLowerCase().equals("d")){
                    robo.virar(true);

                } else {
                    System.out.println("Opcao nao disponivel");
                }

            }));
        opcoesMenu.add(new MenuItem(6, "Mudar numero de passageiros", ()->
            {
                //Mudar quantidade de passageiros
                System.out.print("Quantidade de passageiros saindo (negativo) ou entrando (positivo): ");
                int resp = ScannerGlobal.scannerNumber();

                if(resp < 0){
                    robo.passageirosSair(-resp);
                } else {
                    robo.passageirosEntrar(resp);
                }

            }));
        opcoesMenu.add(new MenuItem(7, "Iniciar Tarefa", ()->
            {
                //Iniciar tarefa
                if(!robo.isTarefaAtiva()){
                    robo.executarTarefa();
                } else {
                    System.out.println("Tarefa já iniciada");
                }  

            }));
        opcoesMenu.add(new MenuItem(8, "Atacar", ()->
            {
                //Atacar
                if(robo.atacarFrente()){
                    System.out.println("Ataque realizado com sucesso!");
                }else {
                    System.out.println("Ataque falhou");
                }

            }));
        opcoesMenu.add(new MenuItem(9, "Reparar", ()->
            {
                //Reparar
                robo.reparar();
                System.out.println("Vida reparada!");

            }));
        opcoesMenu.add(new MenuItem(11, "Enviar mensagem", ()->
            {
                acaoComunicavel(robo);
            })
        );

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        //Rodar Menu
        while(running){
            //Imprimir opções do menu

            if(!robo.isLigado()){
                //Menu robo desligado
                running = controleRoboDesligado(robo);
            } else {
                //Menu Robo ligado
                System.out.println("+----------------------------------+");
                System.out.println("|         escolha um opcao         |");

                for(int i = 0; i<opcoesMenu.size(); i++){
                    if(i < 9){
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));
                    } else {
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));
                    }
                }            

                System.out.println("| [0] Sair                         |");

                System.out.println("+----------------------------------+");

                //Resposta
                System.out.print("opcao escolhida: ");
                resposta = ScannerGlobal.scannerNumber();

                if(resposta == 0){
                    //Voltar
                    running = false;
                } else if (resposta <= opcoesMenu.size() && resposta > 0){
                    //Opções
                    Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                    
                    if (acao != null) {
                        acao.run();
                    } else {
                        System.err.println("Ação não disponível");
                    }

                } else {
                    //Não existe
                    System.out.println("Opcao nao disponivel");
                }
            
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

        //Criar as Opções do menu
        ArrayList<MenuItem> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(new MenuItem(1, "Mover Robo", ()->
            {
                int deltaX;
                int deltaY;
                String correr;

                System.out.print("Deslocamento horizontal: ");
                deltaX = ScannerGlobal.scannerNumber();
                System.out.print("Deslocamento vertical: ");
                deltaY = ScannerGlobal.scannerNumber();
                System.out.print("Correr? (Y/N)");
                correr = ScannerGlobal.nextLine();

                try{
                    if(correr.toLowerCase().equals("y")){
                        robo.mover(true, deltaX, deltaY);

                    } else if(correr.toLowerCase().equals("n")){
                        robo.mover(false, deltaX, deltaY);

                    } else {
                        System.out.println("Opcao nao disponivel");
                    }
                } catch(ZeroLifePointsException e){ 
                    System.err.println("Robo com vida baixa");
                } catch(ColisaoException e){ 
                    System.err.println("Colisão ocorreu: " + e.getMessage());
                } catch(PointOutOfMapException e){ 
                    System.err.println("Local fora dos limites do ambiente: " + e.getMessage());
                } catch (RoboDesligadoException | NullPointerException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(3, "Mudar velocidade maxima", ()->
            {
                //Mudar velocidade máxima
                System.out.print("Indicar nova velocidade maxima: ");
                int resp = ScannerGlobal.scannerNumber();

                robo.setVelocidadeMaxima(resp);

            }));
        opcoesMenu.add(new MenuItem(2, "Visualizar arredores", ()->
            {
                //Visualizar arredores
                try {
                    System.out.println("Espaco: ");
                    robo.get_SensorEspacial().monitorarPlano(ambiente, robo.getX(), robo.getY(), (int) robo.getZ());
                    System.out.println("");
                    robo.get_SensorEspacial().monitorarAltura(ambiente, robo.getX(), robo.getY(), (int) robo.getZ());                    
                }  catch(NullPointerException e){ 
                    System.out.println("Sensor espacial nao instalado");
                } catch (PointOutOfMapException | RoboDesligadoException e) {
                    //Não é para ocorrer
                    System.err.println(e);
                }

            }));
        opcoesMenu.add(new MenuItem(5, "Monitorar sensores", ()->
            {
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

            }));

        opcoesMenu.add(new MenuItem(8, "Info", ()->
            {
                //Info
                System.out.println("Status: " + robo.getEstado());
                System.out.println("Nome: " + robo.getID());
                System.out.println("Modelo: " + robo.getClass().getSimpleName());
                System.out.printf("Posicao atual: (%d,%d)\n", robo.getX(), robo.getY());
                System.out.println("Direcao atual: " + robo.getDirecao());
                System.out.printf("Velocidade maxima: %dm/s\n", robo.getVelocidadeMaxima());
                System.out.printf("Peso atual: %dkg\n", robo.getPeso());
                System.out.println("Vida: " + robo.getVida());

                try {
                    System.out.printf("Temperatura: (%.2f\u00b1%.2f)K\n", robo.get_SensorTemperatura().get_temperaturaKelvin(), robo.get_SensorTemperatura().get_incerteza());
                } catch (NullPointerException e) {
                    System.out.println("Temperatura: Sensor de temperatura nao instalado");
                }

                if(robo.isTarefaAtiva()){
                    System.out.println("Status da tarefa: ATIVO");
                    System.out.println("\tCaixas coletadas: " + robo.getCaixasPegas());
                    System.out.println("\tCaixas faltando: " + (robo.getCaixasTotal() - robo.getCaixasPegas()));

                    for(Obstaculo obst : robo.getCaixas()){
                        System.out.println(String.format("\t -CAIXA em: (%d,%d)", obst.getX(), obst.getY()));
                        
                    }
                } else {
                    System.out.println("Status da tarefa: INATIVO");
                }

            }));
        opcoesMenu.add(new MenuItem(9, "Desligar Robo", ()->
            {
                //Desligar Robo
                robo.desligar();
                System.out.println("Robo Desligado");

            }));
        opcoesMenu.add(new MenuItem(4, "Reparar", ()->
            {
                //Reparar
                robo.reparar();
                System.out.println("Vida reparada!");

            }));
        opcoesMenu.add(new MenuItem(6, "Iniciar Tarefa", ()->
            {
                //Iniciar tarefa
                if(!robo.isTarefaAtiva()){
                    robo.executarTarefa();
                } else {
                    System.out.println("Tarefa já iniciada");
                }  

            }));
        opcoesMenu.add(new MenuItem(7, "Enviar mensagem", ()->
            {
                acaoComunicavel(robo);
            })
        );

        opcoesMenu.sort(Comparator.comparing((opcao) -> opcao.getIndice())); //ordenar por indice

        //Rodar Menu
        while(running){
            //Imprimir opções do menu

            if(!robo.isLigado()){
                //Menu robo desligado
                running = controleRoboDesligado(robo);
            } else {
                //Menu Robo ligado
                System.out.println("+----------------------------------+");
                System.out.println("|         escolha um opcao         |");

                for(int i = 0; i<opcoesMenu.size(); i++){
                    if(i < 9){
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 29));

                    } else {
                        System.out.printf("| [%d] %s|\n", i+1, formatString(opcoesMenu.get(i).getDescricao(), 28));

                    }
                }            

                System.out.println("| [0] Sair                         |");

                System.out.println("+----------------------------------+");

                //Resposta
                System.out.print("opcao escolhida: ");
                resposta = ScannerGlobal.scannerNumber();

                if(resposta == 0){
                    //Voltar
                    running = false;
                } else if (resposta <= opcoesMenu.size() && resposta > 0){
                    //Opções
                    Runnable acao = opcoesMenu.get(resposta-1).getFuncao();
                    
                    if (acao != null) {
                        acao.run();
                    } else {
                        System.err.println("Ação não disponível");
                    }

                } else {
                    //Não existe
                    System.out.println("Opcao nao disponivel");
                }
            
            } 

        }
        System.out.println("");
        
    }

}
