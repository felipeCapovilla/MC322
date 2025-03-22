
public class Main {
    public static void main(String[] args) {

        //Criacao do ambiente e robos.
        Ambiente ambiente = new Ambiente(10,10);
        Robo robo01 = new Robo("robo01",4,10,"Leste");
        Robo robo02 = new Robo("robo02",5,5,"Leste");
        RoboAereo robo_teste = new RoboAereo("Felipe",5,5,"Norte",100,400);


        //Testa o metodo mover. aaa
        robo01.mover(5,2);
        robo02.mover(5,4);
        robo_teste.mover(2,43);

        robo_teste.descer(900);

        //Recebe a nova posicao dos robos pos movimento.
        int[] pos_robo01 = robo01.get_posicao();
        int[] pos_robo02 = robo02.get_posicao();
        int [] pos_teste = robo_teste.get_posicao();

        //Verifica se a nova posicao esta dentro do limite do ambiente.
        boolean status_robo01 = ambiente.dentroDosLimites(pos_teste[0],pos_teste[1]);
        boolean status_robo02 = ambiente.dentroDosLimites(pos_robo02[0],pos_robo02[1]);
        System.out.println("Robo_teste: "+status_robo01);
        System.out.println("Robo02: "+status_robo02);

        //Imprime a posicao atual de cada robo apos movimento.
        System.out.println("\nPosicao teste: ("+pos_teste[0]+","+pos_teste[1]+")");
        System.out.println("Posicao Robo02: ("+pos_robo02[0]+","+pos_robo02[1]+")");




    }
}