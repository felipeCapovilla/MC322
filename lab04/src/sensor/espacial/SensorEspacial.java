package sensor.espacial;

import ambiente.Ambiente;
import exceptions.*;
import sensor.standart.Sensor;

public class SensorEspacial extends Sensor{

    public SensorEspacial(int raio_alcance, String modelo) {
        super(raio_alcance, modelo);
    }

    /**
     * Sensor verifica uma area quadrada no plano de distancia Raio_alcance do sensor 
     * @param ambiente ambiente que o sensor está inserido
     * @param X posição X do sensor
     * @param Y posição Y do sensor
     * @param Z posição Z do sensor
     */
    public void monitorarPlano(Ambiente ambiente, int X, int Y, int Z) throws NullPointerException, PointOutOfMapException{
        //Casos de Excessão
        if(ambiente == null){
            throw new NullPointerException();
        }
        if (X < 0 || Y < 0 || Z < 0 || X >= ambiente.get_largura() || Y >= ambiente.get_comprimento() || Z >= ambiente.get_altura()) {
            throw new PointOutOfMapException(String.format("(%d,%d,%d)", X,Y,Z));
        }

        //Código
        int rightSpace, leftSpace, upSpace, downSpace;

        //Esquerda do sensor
        if(get_raioAlcance() > X){
            leftSpace = X;
        } else {
            leftSpace = get_raioAlcance();
        }

        //Direita do sensor
        if(get_raioAlcance() + X > ambiente.get_largura()-1){
            rightSpace = ambiente.get_largura() - X - 1;
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
        System.out.println("Plano:");
        for(int currentY = Y + upSpace; currentY >= Y - downSpace; currentY--){
            for(int currentX = X - leftSpace; currentX <= X + rightSpace; currentX++){


                if(currentX == X & currentY == Y){
                    System.out.print('R'); //Posicao do sensor
                } else{ //arredores do sensor
    
                    System.out.print(ambiente.getMapa()[currentX][currentY][Z].getRepresentacao());
                }
            }
            System.out.println(""); // Quebrar linha
        }
    }

    /**
     * Sensor verifica a vertical (altitude) de distancia Raio_alcance do sensor 
     * @param ambiente ambiente que o sensor está inserido
     * @param X posição X do sensor
     * @param Y posição Y do sensor
     * @param Z posição Z do sensor
     */
    public void monitorarAltura(Ambiente ambiente, int X, int Y, int Z) throws NullPointerException, PointOutOfMapException{
        //Casos de Excessão
        if(ambiente == null){
            throw new NullPointerException();
        }
        if (X < 0 || Y < 0 || Z < 0 || X >= ambiente.get_largura() || Y >= ambiente.get_comprimento() || Z >= ambiente.get_altura()) {
            throw new PointOutOfMapException(String.format("(%d,%d,%d)", X,Y,Z));
        }

        //Código
        int upSpace, downSpace;

        //Acima do sensor
        if(get_raioAlcance() + Z > ambiente.get_altura()){
            upSpace = ambiente.get_comprimento() - Z - 1;
        } else {
            upSpace = get_raioAlcance();
        }

        //Abaixo do sensor
        if(get_raioAlcance() > Z){
            downSpace = Z;
        } else {
            downSpace = get_raioAlcance();
        }

        //Imprimir
        System.out.println("Altitude:");
        for(int currentZ = Z + upSpace; currentZ>= Z - downSpace; currentZ--){
            if(currentZ == Z){
                System.out.println("\tR"); //Posicao do sensor
            } else{ //arredores do sensor

                System.out.println("\t" + ambiente.getMapa()[X][Y][currentZ].getRepresentacao());
            }
        }
        System.out.println("");

    }

    @Override
    public void monitorar(){
        System.out.printf("Monitorando ambiente num raio de %d metros.\n", get_raioAlcance());
    }
}
