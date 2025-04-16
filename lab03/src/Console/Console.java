package Console;

import ambiente.Ambiente;
import java.util.Scanner;

public class Console {
    final Ambiente ambiente;
    final Scanner scanner = new Scanner(System.in);

    public Console(Ambiente ambiente){
        this.ambiente = ambiente;
    }

    public void mainMenu(){
        int resposta = -1;

        System.out.println("|----------------------------------|");
        System.out.println("|        SIMULADOR DE ROBOS        |");


        do{
            System.out.println("|----------------------------------|");
            System.out.println("|         escolha um opcao         |");

            //Opções
            System.out.println("| [1] Ver Ambiente                 |");
            System.out.println("| [2] Ver Robos                    |");
            System.out.println("| [99] Sair                        |");

            //Resposta
            System.out.print("opcao: ");
            resposta = scanner.nextInt();

            switch (resposta) {
                case 1:
                    //ambienteMenu
                    break;
                case 2:
                    //roboMenu
                    break;
                default:
                    System.out.println("Opcao nao disponivel");;
            }

        } while (resposta != 99);
    }
}
