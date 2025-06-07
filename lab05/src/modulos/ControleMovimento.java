package modulos;

import exceptions.*;
import robo.standart.Robo;

public class ControleMovimento{
    
    private Robo robo_associado;
    private String modelo;


    public ControleMovimento(String nome){
        this.modelo = nome;
        this.robo_associado = null;
    }

    public ControleMovimento(String nome,Robo robo_associado){
        this.robo_associado = robo_associado;
        this.modelo = nome;
    }


    public void mover(int deltaX, int deltaY, int deltaZ) throws NullPointerException,ColisaoException,PointOutOfMapException,RoboDesligadoException,NoRobotException{
        if(this.robo_associado == null){
            throw new NoRobotException(String.format("O modulo %s nao tem nenhum robo sendo controlado.",this.modelo));
        }
        if(robo_associado.get_ambiente() == null){
            throw new NullPointerException();
        }
        if(robo_associado.isLigado()==false){
            throw new RoboDesligadoException();
        }

        if(robo_associado.get_ambiente().dentroDosLimites(robo_associado.getX() + deltaX, robo_associado.getY() + deltaY,robo_associado.getZ()+deltaZ)){
            int colisao;
            try{
                colisao = robo_associado.detectarColisoes(robo_associado.getX()+deltaX,robo_associado.getY()+deltaY,robo_associado.getZ()+deltaZ);
            }catch (SensorMissingException e){
                colisao = -1;
            }

            if(colisao == 1){
                throw new ColisaoException(String.format("Sensor espacial detectou ocupacao em: (%d,%d,%d)",robo_associado.getX()+deltaX,robo_associado.getY()+deltaY,robo_associado.getZ()+deltaZ));
            }else{
                robo_associado.get_ambiente().moverEntidade(robo_associado,robo_associado.getX()+deltaX,robo_associado.getY()+deltaY,robo_associado.getZ()+deltaZ);


                int currX = robo_associado.getX();
                int currY = robo_associado.getY();
                int currZ = robo_associado.getZ();

                robo_associado.setX(currX+deltaX);
                robo_associado.setY(currY+deltaY);
                robo_associado.setZ(currZ+deltaZ);
            }
        }else{
            throw new PointOutOfMapException("("+(robo_associado.getX()+deltaX)+","+(robo_associado.getY()+deltaY)+","+(robo_associado.getZ()+deltaZ)+")");
        }

    }





    public void set_roboAssociado(Robo novo_robo){
        this.robo_associado = novo_robo;
    }

    public Robo get_roboAssociado(){
        return this.robo_associado;
    }

}