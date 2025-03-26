import robo.aereo.explorador.RoboVoadorExplorador;
import robo.aereo.standart.*;
import robo.aereo.turista.RoboVoadorTurista;
import robo.standart.*;
import robo.terrestre.pedestre.*;
import robo.terrestre.standart.*;
import robo.terrestre.veiculo.*;

public class Main {
    public static void main(String[] args) {

        //Criacao do ambiente e robos.
        Ambiente ambiente = new Ambiente(50,50);

        Robo robo01 = new Robo("robo01",4,10,"LESTE");

        RoboAereo roboAereo = new RoboAereo("Felipe",5,5,"NORTE",100,400);
        RoboVoadorExplorador roboExplorador = new RoboVoadorExplorador("roboExplorador", 0, 0, "NORTE", 0, 1000, 10);
        RoboVoadorTurista roboTurista = new RoboVoadorTurista("roboTurista", 0, 0, "LESTE", 0, 1000, 10);

        RoboTerrestre roboTerrestre = new RoboTerrestre("roboTer",5,5, "SUL",15);
        RoboVeiculo roboVeiculo = new RoboVeiculo("roboVeiculo",25,25, "NORTE", 10, 15);
        RoboPedestre roboPedestre = new RoboPedestre("roboPedestre", 0, 0, "OESTE", 20);




        //Teste Robo Standart
        System.out.println("\n--ROBO STANDART");
            //Testa o metodo mover
        int[] pos_robo01 = robo01.get_posicao();
        System.out.println("Posicao inicial robo01: ("+pos_robo01[0]+","+pos_robo01[1]+")");

        robo01.mover(5, 2);
        pos_robo01 = robo01.get_posicao();
        System.out.println("Posicao final robo01: ("+pos_robo01[0]+","+pos_robo01[1]+")");

            //Verifica se a nova posicao esta dentro do limite do ambiente.
        boolean status_robo01 = ambiente.dentroDosLimites(pos_robo01[0],pos_robo01[1]);
        System.out.println("Robo01 está dentro dos limites? \n\t posição("+pos_robo01[0]+","+pos_robo01[1]+") = "+status_robo01);

        robo01.mover(60, 60);
        pos_robo01 = robo01.get_posicao();
        status_robo01 = ambiente.dentroDosLimites(pos_robo01[0],pos_robo01[1]);
        System.out.println("Robo01 está dentro dos limites? \n\t posição("+pos_robo01[0]+","+pos_robo01[1]+") = "+status_robo01);





        //Teste RoboAereo
        System.out.println("\n--ROBO AEREO");
            //Testa metodo de subir e descer
        System.out.println("Altitude inicial: " + roboAereo.subir(0)); //altitude original
        
        System.out.println("Altitude subida: " + roboAereo.subir(200));

        System.out.println("Altitude descida: " + roboAereo.descer(250));



        //Teste RoboExplorador
        System.out.println("\n--ROBO AEREO EXPLORADOR");
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
        System.out.println("Direção inicial:" + roboVeiculo.get_direcao());

        roboVeiculo.virar(true);
        System.out.println("Direção virar direita:" + roboVeiculo.get_direcao());

        roboVeiculo.virar(false);
        roboVeiculo.virar(false);
        System.out.println("Direção virar esquerda:" + roboVeiculo.get_direcao());

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
        roboVeiculo.moverSentido(true);
        pos_roboVeiculo = roboVeiculo.get_posicao();
        System.out.println("Posicao roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");
        System.out.println("\tDireção: " + roboVeiculo.get_direcao() + "; Velocidade: " + roboVeiculo.getVelocidade_atual());

        roboVeiculo.mudarVelocidade(20);
        roboVeiculo.moverSentido(false);
        pos_roboVeiculo = roboVeiculo.get_posicao();
        System.out.println("Posicao roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");
        System.out.println("\tDireção: " + roboVeiculo.get_direcao() + "; Velocidade: " + roboVeiculo.getVelocidade_atual()); 


        
        //Teste roboPedestre
        System.out.println("\n--ROBO TERRESTRE PEDESTRE");

            //Teste de mover correndo e andando
        System.out.println("Velocidade máxima: 20");
        System.out.println("Peso=0");
        int[] pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao inicial roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(false, 20, 0);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar andar 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(true, 0, 20);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar correr 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

            //Teste peso
        roboPedestre.setPeso(10);
        System.out.println("Peso=10");
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao inicial roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(false, 20, 0);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar andar 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(true, 0, 20);
        pos_roboPedestre = roboPedestre.get_posicao();
        System.out.println("Posicao roboPedestre (tentar correr 20): ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");


    }
}