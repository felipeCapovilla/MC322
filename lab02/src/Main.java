/*
 * Main.java
 * 
 * Última modificação: 28/03/2025
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
 * Arquivo principal para iniciação do programa
 */
public class Main {
    public static void main(String[] args) {
        
        //Criacao do ambiente e dos robos.

        Ambiente ambiente = new Ambiente(100,100,100);
        Robo roboStandart = new Robo("roboStandart",20,20,"LESTE");
        RoboAereo roboAereo = new RoboAereo("roboAereo",15,15,"OESTE",20,100);
        RoboVoadorTurista roboAereoTurista = new RoboVoadorTurista("roboAereoTurista",10,20,"OESTE",10,40,30);
        RoboVoadorExplorador roboAereoExplorador = new RoboVoadorExplorador("roboAereoExplorador",23,42,"LESTE",2,50,100);
        RoboTerrestre roboTerrestre = new RoboTerrestre("RoboTerrestre",15,16,"SUL",120);
        RoboVeiculo roboTerrestreVeiculo = new RoboVeiculo("RoboTerrestreVeiculo",76,56,"OESTE",120,120);
        RoboPedestre roboTerrestrePedestre = new RoboPedestre("RoboTerrestePedestre", 1, 1, "SUL", 45);


        //Adicona todos robos ao ambiente criado.
        ambiente.adicionarRobo(roboStandart);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboAereoTurista);
        ambiente.adicionarRobo(roboAereoExplorador);
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboTerrestreVeiculo);
        ambiente.adicionarRobo(roboTerrestrePedestre);

        //Testa movimentações aéreas.

        roboStandart.mover(10, 10);
        
        roboAereo.mover(10,15);
        roboAereo.subir(10);
        roboAereo.descer(15);

        roboAereoTurista.mover(10, 10);
        roboAereoTurista.subir(15);
        roboAereoTurista.descer(21);

        roboAereoExplorador.mover(10,10);
        roboAereoExplorador.subir(15);
        roboAereoExplorador.descer(5);
        
        //Testa movimentações RobosTerrestres
        
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


        //Testa altura máxima atingida pelos RoboAereo's.

        roboAereo.set_altitude(4000);
        roboAereo.set_altitude(-43);
        roboAereoExplorador.set_altitude(600);
        roboAereoExplorador.set_altitude(-1);
        roboAereoTurista.set_altitude(900);
        roboAereoTurista.set_altitude(-1);

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


        //Testa métodos específicos de RoboVoadorExplorador.

        roboAereoExplorador.iniciar_exploracao(10, 300, 50, "Vênus");
        roboAereoExplorador.set_temperatura(200);
        roboAereoExplorador.set_pressao(50);
        roboAereoExplorador.set_velocidade(20);









    }}