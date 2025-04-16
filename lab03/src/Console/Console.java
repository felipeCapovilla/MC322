package Console;

import ambiente.*;
import java.util.ArrayList;
import java.util.Scanner;
import robo.standart.Robo;

public class Console {
    final Ambiente ambiente;
    final Scanner scanner = new Scanner(System.in);

    public Console(Ambiente ambiente){
        this.ambiente = ambiente;
    }

    public void mainMenu(){
        int resposta;

        System.out.println("+----------------------------------+");
        System.out.println("|        SIMULADOR DE ROBOS        |");
        


        do{
            
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
                    break;
                case 99:
                    break;
                default:
                    System.out.println("Opcao nao disponivel");;
            }

        } while (resposta != 99);

        
    }

    private void ambienteMenu(){
        int resposta;

        do{
            
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
                    break;
                default:
                    System.out.println("Opcao nao disponivel");;
            }

        } while (resposta != 99);
        System.out.println("");
    }








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
    
                        if(pos[0] == x && pos[1] == y /*&& listaRobos.get(i).get_altitude() == altura*/){
                            sprite = '@';
                        }
                    }
    
                    //Verificar obstáculo
                    ArrayList<Obstaculo> obstaculos = ambiente.getObstaculos();
                    for(int i = 0; sprite != 'X' && sprite != '@' && i < obstaculos.size(); i++){
                        if(obstaculos.get(i).estaDentro(x, y) && obstaculos.get(i).getAltura() >= altura){
                            sprite = 'X';
                        }
                    }
    
                    System.out.print(sprite);
                }
                System.out.println(""); //Quebra de linha
            }
            System.out.println("");
        }

    }
}
