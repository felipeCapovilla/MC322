/*
 * Main.java
 * 
 * Última modificação: 27/03/2025
 * 
 * Simulação de um ambiente virtual para a testar robos.
 */


import ambiente.*;
import robo.aereo.explorador.*;
import robo.aereo.standart.*;
import robo.aereo.turista.*;
import robo.standart.*;
import robo.terrestre.pedestre.*;
import robo.terrestre.standart.*;
import robo.terrestre.veiculo.*;

/**
 * Arquivo principaal para iniciação do programa
 */
public class Main {
    public static void main(String[] args) {

        //Criacao do ambiente e robos.
        Ambiente ambiente = new Ambiente(50,50);

        Robo robo01 = new Robo("robo01",4, 10,"LESTE");

        RoboAereo roboAereo = new RoboAereo("Felipe",5,5,"NORTE",  100,400);
        RoboVoadorExplorador roboExplorador = new RoboVoadorExplorador("roboExplorador",0, 0, "NORTE", 0, 1000, 10);
        RoboVoadorTurista roboTurista = new RoboVoadorTurista("roboTurista",0, 0, "LESTE",  0, 1000, 10);

        RoboTerrestre roboTerrestre = new RoboTerrestre("roboTer",5,5,"SUL",15);
        RoboVeiculo roboVeiculo = new RoboVeiculo("roboVeiculo",25,25, "NORTE", 10, 15);
        RoboPedestre roboPedestre = new RoboPedestre("roboPedestre",0, 0,"OESTE",20);

        //Teste Ambiente
        System.out.println("\n--AMBIENTE--");
            //Adicionar os robos no ambiente
        ambiente.adicionarRobo(robo01);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboExplorador);
        ambiente.adicionarRobo(roboTurista);
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboVeiculo);
        ambiente.adicionarRobo(roboPedestre);

        System.out.println("Lista de robos no ambiente:");
        ambiente.getListaRobos().forEach(robo -> {
            System.out.print(" " + robo);
        });

            //Adicionar Obstáculos no ambiente
        ambiente.adicionarObstaculo(1, 1);
        ambiente.adicionarObstaculo(10, 7);
        ambiente.adicionarObstaculo(1, 40);
        ambiente.adicionarObstaculo(30, 25);
        ambiente.adicionarObstaculo(49, 49);
        ambiente.adicionarObstaculo(0, 0);

        System.out.println("\nLista de obstáculos no ambiente:");
        ambiente.getObstaculos().forEach(obst -> {
            System.out.print(String.format(" (%d,%d)", obst[0], obst[1]));
        });

            //Obstáculo fora do limite
        System.out.println("\nAdicionando obstáculo fora dos limites do ambiente");
        try {
            ambiente.adicionarObstaculo(60, 60);
            System.out.println("Obstáculo adicionado com sucesso fora dos limites");
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }



        //Teste Robo Standart
        System.out.println("\n\n--ROBO STANDART--");
        System.out.println("testando o " + robo01.getNome());

            //Testa o metodo mover
        System.out.println("1. Método de mover");
        int[] pos_robo01 = robo01.get_posicao();
        System.out.println("Posicao inicial robo01: ("+pos_robo01[0]+","+pos_robo01[1]+")");

        robo01.mover(5, 2);
        pos_robo01 = robo01.get_posicao();
        System.out.println("Posicao final robo01: ("+pos_robo01[0]+","+pos_robo01[1]+")");

            //Verifica se a nova posicao esta dentro do limite do ambiente.
        System.out.println("2. Verificar os limites do ambiente");
        System.out.println("Robo01 está dentro dos limites?");
        System.out.println("Posicao inicial robo01: ("+pos_robo01[0]+","+pos_robo01[1]+")");
        try {
            robo01.mover(5, 0);
            pos_robo01 = robo01.get_posicao();
            System.out.println("\t movido com sucesso para ("+pos_robo01[0]+","+pos_robo01[1]+")");
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }
        System.out.println("Robo01 está dentro dos limites?");
        try {
            robo01.mover(60, 60);
            pos_robo01 = robo01.get_posicao();
            System.out.println("\t movido com sucesso para ("+pos_robo01[0]+","+pos_robo01[1]+")");
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }


        //Teste RoboAereo
        System.out.println("\n--ROBO AEREO--");
        System.out.println("testando o " + roboAereo.getNome());
            //Testa metodo de subir e descer
        System.out.println("1. Métodos subida e descida");
        System.out.println("Altitude inicial: " + roboAereo.get_altitude()); //altitude original
        
        roboAereo.subir(200);
        System.out.println("Altitude subida (tenta subir 200m): " + roboAereo.get_altitude());

        roboAereo.descer(250);
        System.out.println("Altitude descida (tenta descer 250m): " + roboAereo.get_altitude());

            //Testa altitude máxima e mínima
        System.out.println("2. Verificar altitude máxima e mínima");
        System.out.println("Altitude máxima = " + roboAereo.get_altitude_max());
        try {
            roboAereo.subir(200);
            System.out.println("Altitude subida com sucesso: " + roboAereo.get_altitude());
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }





        



        //Teste RoboExplorador
        System.out.println("\n--ROBO AEREO EXPLORADOR--");
        System.out.println("testando o " + roboExplorador.getNome());
            //Metodo de iniciar exploração
        roboExplorador.iniciar_exploracao(200, 600, 5, "Marte");
        System.out.println("Iniciar exploração");
        System.out.println("\tEm missão: " + roboExplorador.status_missao());
        System.out.println("\tPressão: " + roboExplorador.get_pressao());
        System.out.println("\tTemperatura: " + roboExplorador.get_temperatura());
        System.out.println("\tVelocidade: " + roboExplorador.get_velocidade());
        System.out.println("\tPlaneta: " + roboExplorador.get_planeta());

            //Finalizar missão
        roboExplorador.finalizar_exploracao();
        System.out.println("Finalizar missão");
        System.out.println("\tEm missão: " + roboExplorador.status_missao());
        System.out.println("\tPressão: " + roboExplorador.get_pressao());
        System.out.println("\tTemperatura: " + roboExplorador.get_temperatura());
        System.out.println("\tVelocidade: " + roboExplorador.get_velocidade());
        System.out.println("\tPlaneta: " + roboExplorador.get_planeta());



        //Teste RoboTurista
        System.out.println("\n--ROBO AEREO TURISTA");
            //Verifivar iniciar passeio
        roboTurista.inciar_passeio(7, "Rio de Janeiro");
        System.out.println("Em Passeio: " + roboTurista.get_status());
        System.out.println("Passegeiros: " + roboTurista.get_numero_passageiros());
        System.out.println("Cidade turistica: " + roboTurista.get_destino());



        //Teste RoboTerrestre
        System.out.println("\n--ROBO TERRESTRE");

            //Verificar velocidade maxima
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




        //Teste roboVeiculo
        System.out.println("\n--ROBO TERRESTRE VEICULO");

            //Virar o robo
        System.out.println("Direção inicial:" + roboVeiculo.getDirecao());

        roboVeiculo.virar(true);
        System.out.println("Direção virar direita:" + roboVeiculo.getDirecao());

        roboVeiculo.virar(false);
        roboVeiculo.virar(false);
        System.out.println("Direção virar esquerda:" + roboVeiculo.getDirecao());

            //Mudar numero de passageiros
        System.out.println("Números de passageiros (max 15):");
        System.err.println("passageiros iniciais: " + roboVeiculo.getPassageiros());

        roboVeiculo.passageirosEntrar(10);
        System.err.println("passageiros (tenta entrar 10): " + roboVeiculo.getPassageiros());

        roboVeiculo.passageirosEntrar(7);
        System.err.println("passageiros (tenta entrar 7): " +roboVeiculo.getPassageiros());

        roboVeiculo.passageirosSair(20);
        System.err.println("passageiros (tenta sair 20): " +roboVeiculo.getPassageiros());


            //Mover o robo no sentido
        int[] pos_roboVeiculo = roboVeiculo.get_posicao();
        System.out.println("\nPosicao inicial roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");

        roboVeiculo.mudarVelocidade(5);
        roboVeiculo.mover(true);
        pos_roboVeiculo = roboVeiculo.get_posicao();
        System.out.println("Posicao roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");
        System.out.println("\tDireção: " + roboVeiculo.getDirecao() + "; Velocidade: " + roboVeiculo.getVelocidade());

        roboVeiculo.mudarVelocidade(20);
        roboVeiculo.mover(false);
        pos_roboVeiculo = roboVeiculo.get_posicao();
        System.out.println("Posicao roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");
        System.out.println("\tDireção: " + roboVeiculo.getDirecao() + "; Velocidade: " + roboVeiculo.getVelocidade()); 


        
        //Teste roboPedestre
        System.out.println("\n--ROBO TERRESTRE PEDESTRE");

            //Teste de mover correndo e andando
        System.out.println("Velocidade máxima: 20");
        System.out.println("Peso=0");
        int[] pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao inicial roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.mover(false, 20, 0);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar andar 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.mover(true, 0, 20);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar correr 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

            //Teste peso
        roboPedestre.setPeso(10);
        System.out.println("Peso=10");
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao inicial roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.mover(false, 20, 0);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar andar 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.mover(true, 0, 20);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar correr 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");


    }
}