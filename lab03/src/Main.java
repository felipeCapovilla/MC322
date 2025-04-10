/*
 * Main.java
 * 
 * Última modificação: 10/04/2025
 * 
 * Simulação de um ambiente virtual para a testar robos.
 */


import ambiente.*;
import constantes.Bussula;
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

        Robo roboStandart = new Robo("roboStandart",20,20,Bussula.LESTE);
        RoboAereo roboAereo = new RoboAereo("roboAereo",15,15,Bussula.OESTE,20,100);
        RoboVoadorTurista roboAereoTurista = new RoboVoadorTurista("roboAereoTurista",10,20,Bussula.OESTE,10,40,30);
        RoboVoadorExplorador roboAereoExplorador = new RoboVoadorExplorador("roboAereoExplorador",23,42,Bussula.LESTE,2,50,100);
        RoboTerrestre roboTerrestre = new RoboTerrestre("RoboTerrestre",15,16,Bussula.SUL,120);
        RoboVeiculo roboTerrestreVeiculo = new RoboVeiculo("RoboTerrestreVeiculo",76,56,Bussula.OESTE,120,120);
        RoboPedestre roboTerrestrePedestre = new RoboPedestre("RoboTerrestePedestre", 1, 1, Bussula.SUL, 20);


        //Teste Ambiente
        System.out.println("\n--AMBIENTE--");
            //Adicionar os robos no ambiente
        ambiente.adicionarRobo(roboStandart);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboAereoExplorador);
        ambiente.adicionarRobo(roboAereoTurista);
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboTerrestreVeiculo);
        ambiente.adicionarRobo(roboTerrestrePedestre);

        System.out.printf("Lista de robos no ambiente (%d):\n", ambiente.get_robos_ativos());
        ambiente.getListaRobos().forEach(robo -> {
            System.out.print(" " + robo);
        });

            //Adicionar e remover Obstáculos no ambiente
        ambiente.adicionarObstaculo(1, 1);
        ambiente.adicionarObstaculo(10, 7);
        ambiente.adicionarObstaculo(1, 40);
        ambiente.adicionarObstaculo(30, 25);
        ambiente.adicionarObstaculo(49, 49);
        ambiente.adicionarObstaculo(0, 0);
        ambiente.removerObstaculo(1, 40); //existe
        ambiente.removerObstaculo(62, 48); //não existe

        System.out.printf("\nLista de obstáculos no ambiente (%d):\n", ambiente.get_quantidade_obstaculos());
        ambiente.getObstaculos().forEach(obst -> {
            System.out.print(String.format(" (%d,%d)", obst[0], obst[1]));
        });

            //Obstáculo fora do limite
        System.out.println("\nAdicionando obstáculo fora dos limites do ambiente:");
        try {
            ambiente.adicionarObstaculo(160, 60);
            System.out.println("Obstáculo adicionado com sucesso fora dos limites");
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }



        //Teste Robo Standart
        System.out.println("\n\n--ROBO STANDART--");
        System.out.println("testando o " + roboStandart.getNome());

            //Testa o metodo mover
        System.out.println("1. Método de mover");
        int[] pos_roboStandart = roboStandart.get_posicao();
        System.out.println("Posicao inicial roboStandart: ("+pos_roboStandart[0]+","+pos_roboStandart[1]+")");

        roboStandart.mover(5, 2);
        pos_roboStandart = roboStandart.get_posicao();
        System.out.println("Posicao final roboStandart: ("+pos_roboStandart[0]+","+pos_roboStandart[1]+")");

            //Verifica se a nova posicao esta dentro do limite do ambiente.
        System.out.println("2. Verificar os limites do ambiente");
        System.out.println("roboStandart está dentro dos limites?");
        System.out.println("Posicao inicial roboStandart: ("+pos_roboStandart[0]+","+pos_roboStandart[1]+")");
        try {
            roboStandart.mover(600, 60);
            pos_roboStandart = roboStandart.get_posicao();
            System.out.println("\t movido com sucesso para ("+pos_roboStandart[0]+","+pos_roboStandart[1]+")");
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }

            //Testa o método de identificar obstáculos
        System.out.println("3. Verificar os obstáculos do ambiente do robo");
        roboStandart.identificar_obstaculos();

        System.out.println("Removendo o robo do ambiente");
        ambiente.removerRobo(roboStandart);
        roboStandart.identificar_obstaculos();



        //Teste RoboAereo
        System.out.println("\n--ROBO AEREO--");
        System.out.println("testando o " + roboAereo.getNome());
            //Testa metodo de subir e descer
        System.out.println("1. Métodos subida e descida");
        System.out.println("Altitude inicial: " + roboAereo.get_altitude()); //altitude original
        
        roboAereo.subir(20);
        System.out.println("Altitude subida (tenta subir 20m): " + roboAereo.get_altitude());

        roboAereo.descer(25);
        System.out.println("Altitude descida (tenta descer 25m): " + roboAereo.get_altitude());

            //Testa altitude máxima e mínima
        System.out.println("2. Verificar altitude máxima e mínima");
        System.out.println("Altitude máxima = " + roboAereo.get_altitude_max());
        try {
            roboAereo.subir(200);
            System.out.println("Altitude subida com sucesso: " + roboAereo.get_altitude());
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }

        try {
            roboAereo.descer(500);
            System.out.println("Altitude descida com sucesso: " + roboAereo.get_altitude());
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }

        roboAereo.set_altitude(4000);
        roboAereo.set_altitude(-43);

            //Movimentação geral
        roboAereo.mover(10,15);
        roboAereo.subir(30);
        roboAereo.descer(15);


        //Teste roboAereoExplorador
        System.out.println("\n--ROBO AEREO EXPLORADOR--");
        System.out.println("testando o " + roboAereoExplorador.getNome());
            //Metodo de iniciar exploração
        roboAereoExplorador.iniciar_exploracao(200, 600, 5, "Marte");
        System.out.println("1. Iniciar exploração");
        System.out.println("\tEm missão: " + roboAereoExplorador.status_missao());
        System.out.println("\tPressão: " + roboAereoExplorador.get_pressao());
        System.out.println("\tTemperatura: " + roboAereoExplorador.get_temperatura());
        System.out.println("\tVelocidade: " + roboAereoExplorador.get_velocidade());
        System.out.println("\tPlaneta: " + roboAereoExplorador.get_planeta());

            //Finalizar missão
        roboAereoExplorador.finalizar_exploracao();
        System.out.println("2. Finalizar missão");
        System.out.println("\tEm missão: " + roboAereoExplorador.status_missao());
        System.out.println("\tPressão: " + roboAereoExplorador.get_pressao());
        System.out.println("\tTemperatura: " + roboAereoExplorador.get_temperatura());
        System.out.println("\tVelocidade: " + roboAereoExplorador.get_velocidade());
        System.out.println("\tPlaneta: " + roboAereoExplorador.get_planeta());

            //Testando valores extremos
        System.out.println("3. Testando valores extremos");
        try {
            roboAereoExplorador.set_temperatura(-1);
            System.out.println("Temperatura mudada com sucesso para: " + roboAereoExplorador.get_temperatura());

        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }
        try {
            roboAereoExplorador.set_velocidade(200);
            System.out.println("Velocidade mudada com sucesso para: " + roboAereoExplorador.get_temperatura());

        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }

            //Movimentação geral
        roboAereoExplorador.mover(10,10);
        roboAereoExplorador.subir(15);
        roboAereoExplorador.descer(5);

            //Altitude máima e mínima
        roboAereoExplorador.set_altitude(600);
        roboAereoExplorador.set_altitude(-1);


        //Teste roboAereoTurista
        System.out.println("\n--ROBO AEREO TURISTA--");
        System.out.println("testando o " + roboAereoTurista.getNome());

            //Verifivar iniciar passeio
        System.out.println("1. Verificar iniciação de passeio");
        roboAereoTurista.inciar_passeio(7, "Rio de Janeiro");
        System.out.println("Em Passeio: " + roboAereoTurista.get_status());
        System.out.println("Passegeiros: " + roboAereoTurista.get_numero_passageiros());
        System.out.println("Cidade turistica: " + roboAereoTurista.get_destino());

            //Movimentação geral
        roboAereoTurista.mover(10, 10);
        roboAereoTurista.subir(15);
        roboAereoTurista.descer(21);

            //Altitude máxima e mínima
        roboAereoTurista.set_altitude(900);
        roboAereoTurista.set_altitude(-1);
    


        //Teste RoboTerrestre
        System.out.println("\n--ROBO TERRESTRE--");
        System.out.println("testando o " + roboTerrestre.getNome());

            //Verificar velocidade maxima
        System.out.println("1. Verificar a velocidade máxima");
        int[] pos_roboTerrestre = roboTerrestre.get_posicao();
        int[] velocidade = {0,0};
        System.out.println("Posicao inicial RoboTerrestre: ("+pos_roboTerrestre[0]+","+pos_roboTerrestre[1]+")");
        System.out.println("\tVelocidade máxima: 15");

        velocidade[0] = 0;
        velocidade[1] = 20;
        roboTerrestre.mover(velocidade[0], velocidade[1]);
        pos_roboTerrestre = roboTerrestre.get_posicao();
        System.out.println("Posicao RoboTerrestre: ("+pos_roboTerrestre[0]+","+pos_roboTerrestre[1]+")");
        System.out.println("\tVelocidade inserida: " + (int)Math.sqrt((velocidade[0]*velocidade[0] + velocidade[1]*velocidade[1])));

        velocidade[0] = 8;
        velocidade[1] = 15;
        roboTerrestre.mover(velocidade[0], velocidade[1]);
        pos_roboTerrestre = roboTerrestre.get_posicao();
        System.out.println("Posicao RoboTerrestre: ("+pos_roboTerrestre[0]+","+pos_roboTerrestre[1]+")");
        System.out.println("\tVelocidade inserida: " + (int)Math.sqrt((velocidade[0]*velocidade[0] + velocidade[1]*velocidade[1])));


        //Teste roboTerrestreVeiculo
        System.out.println("\n--ROBO TERRESTRE VEICULO--");
        System.out.println("testando o " + roboTerrestreVeiculo.getNome());

            //Virar o robo
        System.out.println("1. Testar o método de virar");
        System.out.println("Direção inicial:" + roboTerrestreVeiculo.getDirecao());

        roboTerrestreVeiculo.virar(true);
        System.out.println("Direção virar 1 para direita:" + roboTerrestreVeiculo.getDirecao());

        roboTerrestreVeiculo.virar(false);
        roboTerrestreVeiculo.virar(false);
        System.out.println("Direção virar 2 para esquerda:" + roboTerrestreVeiculo.getDirecao());

            //Mudar numero de passageiros
        System.out.println("2. Testar a entrada e saída de passageiros");
        System.out.println("Números de passageiros (max 15):");
        System.err.println("passageiros iniciais: " + roboTerrestreVeiculo.getPassageiros());

        roboTerrestreVeiculo.passageirosEntrar(10);
        System.err.println("passageiros (tenta entrar 10): " + roboTerrestreVeiculo.getPassageiros());

        roboTerrestreVeiculo.passageirosEntrar(7);
        System.err.println("passageiros (tenta entrar 7): " +roboTerrestreVeiculo.getPassageiros());

        roboTerrestreVeiculo.passageirosSair(20);
        System.err.println("passageiros (tenta sair 20): " +roboTerrestreVeiculo.getPassageiros());


            //Mover o robo no sentido
        System.out.println("3. Verificar o método mover no sentido da direção");
        int[] pos_roboTerrestreVeiculo = roboTerrestreVeiculo.get_posicao();
        System.out.println("Posicao inicial roboTerrestreVeiculo: ("+pos_roboTerrestreVeiculo[0]+","+pos_roboTerrestreVeiculo[1]+")");

        roboTerrestreVeiculo.mudarVelocidade(5);
        roboTerrestreVeiculo.mover(true);
        pos_roboTerrestreVeiculo = roboTerrestreVeiculo.get_posicao();
        System.out.println("Posicao roboTerrestreVeiculo: ("+pos_roboTerrestreVeiculo[0]+","+pos_roboTerrestreVeiculo[1]+")");
        System.out.println("\tDireção (frente): " + roboTerrestreVeiculo.getDirecao() + "; Velocidade: " + roboTerrestreVeiculo.getVelocidade());

        roboTerrestreVeiculo.mudarVelocidade(20);
        roboTerrestreVeiculo.mover(false);
        pos_roboTerrestreVeiculo = roboTerrestreVeiculo.get_posicao();
        System.out.println("Posicao roboTerrestreVeiculo: ("+pos_roboTerrestreVeiculo[0]+","+pos_roboTerrestreVeiculo[1]+")");
        System.out.println("\tDireção (trás): " + roboTerrestreVeiculo.getDirecao() + "; Velocidade: " + roboTerrestreVeiculo.getVelocidade()); 

        
        //Teste roboTerrestrePedestre
        System.out.println("\n--ROBO TERRESTRE PEDESTRE--");
        System.out.println("testando o " + roboTerrestrePedestre.getNome());

            //Teste de mover correndo e andando
        System.out.println("1. Verificar o movimento andando e correndo");
        System.out.println("Velocidade máxima: " + roboTerrestrePedestre.getVelocidadeMaxima());
        System.out.println("Peso=" + roboTerrestrePedestre.getPeso());
        int[] pos_roboTerrestrePedestre = roboTerrestrePedestre.get_posicao();
        System.out.println("Posicao inicial roboTerrestrePedestre: ("+pos_roboTerrestrePedestre[0]+","+pos_roboTerrestrePedestre[1]+")");

        roboTerrestrePedestre.mover(false, 20, 0);
        pos_roboTerrestrePedestre = roboTerrestrePedestre.get_posicao();
        System.out.println("Posicao roboTerrestrePedestre (tentar andar 20): ("+pos_roboTerrestrePedestre[0]+","+pos_roboTerrestrePedestre[1]+")");

        roboTerrestrePedestre.mover(true, 0, 20);
        pos_roboTerrestrePedestre = roboTerrestrePedestre.get_posicao();
        System.out.println("Posicao roboTerrestrePedestre (tentar correr 20): ("+pos_roboTerrestrePedestre[0]+","+pos_roboTerrestrePedestre[1]+")");

            //Teste peso
        System.out.println("2. Verificar o movimento com maior peso");
        roboTerrestrePedestre.setPeso(10);
        System.out.println("Peso="+ roboTerrestrePedestre.getPeso());
        pos_roboTerrestrePedestre = roboTerrestrePedestre.get_posicao();
        System.out.println("Posicao inicial roboTerrestrePedestre: ("+pos_roboTerrestrePedestre[0]+","+pos_roboTerrestrePedestre[1]+")");

        roboTerrestrePedestre.mover(false, 20, 0);
        pos_roboTerrestrePedestre = roboTerrestrePedestre.get_posicao();
        System.out.println("Posicao roboTerrestrePedestre (tentar andar 20): ("+pos_roboTerrestrePedestre[0]+","+pos_roboTerrestrePedestre[1]+")");

        roboTerrestrePedestre.mover(true, 0, 20);
        pos_roboTerrestrePedestre = roboTerrestrePedestre.get_posicao();
        System.out.println("Posicao roboTerrestrePedestre (tentar correr 20): ("+pos_roboTerrestrePedestre[0]+","+pos_roboTerrestrePedestre[1]+")");


    }}