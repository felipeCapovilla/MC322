import robo.terrestre.pedestre.RoboPedestre;
import robo.terrestre.standart.*;
import robo.terrestre.veiculo.*;

public class Main {
    public static void main(String[] args) {

        //Criacao do ambiente e robos.
        Ambiente ambiente = new Ambiente(50,50);
        RoboTerrestre roboTerrestre = new RoboTerrestre("roboTer",5,5, "SUL",15);
        RoboVeiculo roboVeiculo = new RoboVeiculo("roboVeiculo",25,25, "NORTE", 10, 15);
        RoboPedestre roboPedestre = new RoboPedestre("roboPedestre", 0, 0, "OESTE", 20);

        
        //Teste RoboTerrestre
        System.out.println("\nROBO TERRESTRE");

            //Verificar velocidade maxima
        int[] pos_roboTerrestre = roboTerrestre.exibirPosicao();
        int[] velocidade = {0,0};
        System.out.println("\nPosicao RoboTerrestre: ("+pos_roboTerrestre[0]+","+pos_roboTerrestre[1]+")");

        velocidade[0] = 0;
        velocidade[1] = 20;
        roboTerrestre.mover(velocidade[0], velocidade[1]);
        pos_roboTerrestre = roboTerrestre.exibirPosicao();
        System.out.println("Posicao RoboTerrestre: ("+pos_roboTerrestre[0]+","+pos_roboTerrestre[1]+")");
        System.out.println("\tVelocidade: " + (int)Math.sqrt((velocidade[0]*velocidade[0] + velocidade[1]*velocidade[1])));

        velocidade[0] = 8;
        velocidade[1] = 15;
        roboTerrestre.mover(velocidade[0], velocidade[1]);
        pos_roboTerrestre = roboTerrestre.exibirPosicao();
        System.out.println("Posicao RoboTerrestre: ("+pos_roboTerrestre[0]+","+pos_roboTerrestre[1]+")");
        System.out.println("\tVelocidade: " + (int)Math.sqrt((velocidade[0]*velocidade[0] + velocidade[1]*velocidade[1])));





        //Teste roboVeiculo
        System.out.println("\nROBO TERRESTRE VEICULO");

            //Virar o robo
        System.out.println("Direção:" + roboVeiculo.getDirecao());

        roboVeiculo.virar(true);
        System.out.println("Direção:" + roboVeiculo.getDirecao());

        roboVeiculo.virar(false);
        roboVeiculo.virar(false);
        System.out.println("Direção:" + roboVeiculo.getDirecao());

            //Mudar numero de passageiros
        System.out.println("Números de passageiros (max 15):");
        System.err.println(roboVeiculo.getPassageiros());

        roboVeiculo.passageirosEntrar(10);
        System.err.println(roboVeiculo.getPassageiros());

        roboVeiculo.passageirosEntrar(7);
        System.err.println(roboVeiculo.getPassageiros());

        roboVeiculo.passageirosSair(20);
        System.err.println(roboVeiculo.getPassageiros());


            //Mover o robo no sentido
        int[] pos_roboVeiculo = roboVeiculo.exibirPosicao();
        System.out.println("\nPosicao roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");

        roboVeiculo.mudarVelocidade(5);
        roboVeiculo.moverSentido(true);
        pos_roboVeiculo = roboVeiculo.exibirPosicao();
        System.out.println("Posicao roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");
        System.out.println("\tDireção: " + roboVeiculo.getDirecao() + "; Velocidade: " + roboVeiculo.getVelocidade_atual());

        roboVeiculo.mudarVelocidade(20);
        roboVeiculo.moverSentido(false);
        pos_roboVeiculo = roboVeiculo.exibirPosicao();
        System.out.println("Posicao roboVeiculo: ("+pos_roboVeiculo[0]+","+pos_roboVeiculo[1]+")");
        System.out.println("\tDireção: " + roboVeiculo.getDirecao() + "; Velocidade: " + roboVeiculo.getVelocidade_atual()); 


        
        //Teste roboPedestre
        System.out.println("\nROBO TERRESTRE PEDESTRE");

            //Teste de mover correndo e andando
        System.out.println("Peso=0");
        int[] pos_roboPedestre = roboPedestre.exibirPosicao();
        System.out.println("Posicao roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(false, 20, 0);
        pos_roboPedestre = roboPedestre.exibirPosicao();
        System.out.println("Posicao roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(true, 0, 20);
        pos_roboPedestre = roboPedestre.exibirPosicao();
        System.out.println("Posicao roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

            //Teste peso
        roboPedestre.setPeso(10);
        System.out.println("Peso=10");
        pos_roboPedestre = roboPedestre.exibirPosicao();
        System.out.println("Posicao roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(false, 20, 0);
        pos_roboPedestre = roboPedestre.exibirPosicao();
        System.out.println("Posicao roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");

        roboPedestre.moverAndar(true, 0, 20);
        pos_roboPedestre = roboPedestre.exibirPosicao();
        System.out.println("Posicao roboPedestre: ("+pos_roboPedestre[0]+","+pos_roboPedestre[1]+")");


    }
}