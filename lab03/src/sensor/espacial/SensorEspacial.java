package sensor.espacial;

import ambiente.Ambiente;
import ambiente.Obstaculo;
import java.util.ArrayList;
import robo.standart.Robo;
import sensor.standart.Sensor;

public class SensorEspacial extends Sensor{

    public SensorEspacial(int raio_alcance, String modelo) {
        super(raio_alcance, modelo);
    }

    public void monitorarPlano(Ambiente ambiente, int X, int Y, int Z){
        int rightSpace, leftSpace, upSpace, downSpace;

        //Esquerda do sensor
        if(get_raioAlcance() > X){
            leftSpace = X;
        } else {
            leftSpace = get_raioAlcance();
        }

        //Direita do sensor
        if(get_raioAlcance() + X > ambiente.getLargura()-1){
            rightSpace = ambiente.getLargura() - X - 1;
        } else {
            rightSpace = get_raioAlcance();
        }

        //Acima do sensor
        if(get_raioAlcance() + Y > ambiente.get_comprimento()){
            upSpace = ambiente.get_comprimento() - Y - 1;
        } else {
            upSpace = get_raioAlcance();
        }

        //Abaixo do sensor
        if(get_raioAlcance() > Y){
            downSpace = Y;
        } else {
            downSpace = get_raioAlcance();
        }


        //Imprimir
        for(int currentY = Y + upSpace; currentY >= Y - downSpace; currentY--){
            for(int currentX = X - leftSpace; currentX <= X + rightSpace; currentX++){
                if(currentX == X & currentY == Y){
                    System.out.print('R'); //Posicao do sensor
                } else{ //arredores do sensor
                    //Vazio
                    char sprite = '.';
    
                    //Verificar robos
                    ArrayList<Robo> listaRobos = ambiente.getListaRobos();
                    for(int i = 0; sprite != '@' && i < listaRobos.size(); i++){
                        int[] pos = listaRobos.get(i).get_posicao();
    
                        if(pos[0] == currentX && pos[1] == currentY && listaRobos.get(i).get_altitude() == Z){
                            sprite = '@';
                        }
                    }
    
                    //Verificar obstáculo
                    ArrayList<Obstaculo> obstaculos = ambiente.getObstaculos();
                    for(int i = 0; sprite != 'X' && sprite != '@' && i < obstaculos.size(); i++){
                        if(obstaculos.get(i).estaDentro(currentX, currentY) && obstaculos.get(i).getAltura() > Z){
                            sprite = 'X';
                        }
                    }
    
                    System.out.print(sprite);
                }
            }
            System.out.println(""); // Quebrar linha
        }




    }
    
    
    




}
