//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //Criacao do ambiente e robos.
        Ambiente ambiente = new Ambiente(10,10);
        Robo robo01 = new Robo("robo01",4,10);
        Robo robo02 = new Robo("robo02",5,5);

        //Testa o metodo mover.
        robo01.mover(5,2);
        robo02.mover(5,4);

        //Recebe a nova posicao dos robos pos movimento.
        int[] pos_robo01 = robo01.exibirPosicao();
        int[] pos_robo02 = robo02.exibirPosicao();

        //Verifica se a nova posicao esta dentro do limite do ambiente.
        boolean status_robo01 = ambiente.dentroDosLimites(pos_robo01[0],pos_robo01[1]);
        boolean status_robo02 = ambiente.dentroDosLimites(pos_robo02[0],pos_robo02[1]);
        System.out.println("Robo01: "+status_robo01);
        System.out.println("Robo02: "+status_robo02);

        //Imprime a posicao atual de cada robo apos movimento.
        System.out.println("\nPosicao Robo01: ("+pos_robo01[0]+","+pos_robo01[1]+")");
        System.out.println("Posicao Robo02: ("+pos_robo02[0]+","+pos_robo02[1]+")");




    }
}