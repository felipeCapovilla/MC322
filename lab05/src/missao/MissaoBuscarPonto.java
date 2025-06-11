package missao;

import ambiente.Ambiente;
import constantes.TipoEntidade;
import exceptions.*;
import interfaces.Missao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import robo.aereo.standart.RoboAereo;
import robo.standart.Robo;
import sensor.espacial.SensorEspacial;

public class MissaoBuscarPonto implements Missao{
    private int finalX;
    private int finalY;
    private int finalZ;

    private boolean isAtivo = false;

    public MissaoBuscarPonto(){
        finalX=0;
        finalY=0;
        finalZ=0;
    }

    @Override
    public void executar(Robo robo, Ambiente ambiente) throws NoRobotException, NullPointerException, SensorMissingException, NoModuleException{
        //Verificar Exceptions
        if(robo == null){
            throw new NoRobotException();
        }
        if(ambiente == null){
            throw new NullPointerException();
        }

        //Verifica sensores
        if(robo.get_gerenciadorSensores() == null){
            throw new NoModuleException("Gerenciador de Sensores");
        }
        int raioDeslocamento;
        SensorEspacial sensor = robo.get_gerenciadorSensores().getSensorEspacial();

        if(sensor == null){
            throw new SensorMissingException("Sensor Espacial");
        } else {
            raioDeslocamento = sensor.get_raioAlcance();
        }

        //Inicia a missão se estiver ativa
        if(!isAtivo){
            boolean running;
            Random random = new Random();
            isAtivo = true;

            do { 
                finalX = random.nextInt(ambiente.get_largura());
                finalY = random.nextInt(ambiente.get_comprimento());

                if(robo instanceof RoboAereo){
                    finalZ = random.nextInt(ambiente.get_altura());

                    running = (ambiente.estaOcupado(finalX, finalY, finalZ) || finalZ >= ((RoboAereo)robo).get_altitude_max());
                } else {
                    running = (ambiente.estaOcupado(finalX, finalY, finalZ));
                }
     
            } while (running);

            System.out.printf("Ponto final: (%d, %d, %d)\n",finalX, finalY, finalZ);
        }

        //Algoritmo de movimentação
        int[] pontoFinal = {finalX, finalY, finalZ};
        

        ArrayList<int[]> direcoes = acharCaminho(robo, ambiente, pontoFinal);

        //System.out.println(direcoes.size());
        //direcoes.forEach(dir -> System.out.println(Arrays.toString(dir)));

            
        if(direcoes == null || direcoes.isEmpty()){
            //Impossível chegar até o ponto
            isAtivo = false;
        } else {

            //Executar missão
        int deslocamentoX = 0;
        int deslocamentoY = 0;
        int deslocamentoZ = 0;

        boolean running = true;
        int index = 0;
        while (running && index < direcoes.size()) {

            if(
                (deslocamentoX > 0 && direcoes.get(index)[0] < 0) || (deslocamentoX < 0 && direcoes.get(index)[0] > 0) ||
                (deslocamentoY > 0 && direcoes.get(index)[1] < 0) || (deslocamentoY < 0 && direcoes.get(index)[1] > 0) ||
                (deslocamentoZ > 0 && direcoes.get(index)[2] < 0) || (deslocamentoZ < 0 && direcoes.get(index)[2] > 0) 
                ){
                //Caminho volta, ou seja, não é possível ir direto
                running = false;
            } else {
                deslocamentoX += direcoes.get(index)[0];
                deslocamentoY += direcoes.get(index)[1];
                deslocamentoZ += direcoes.get(index)[2];

                index++;
            }
            
            if(Math.abs(deslocamentoX) >= raioDeslocamento || Math.abs(deslocamentoY) >= raioDeslocamento || Math.abs(deslocamentoZ) >= raioDeslocamento){
                running = false;
            }
        }

        robo.get_controleMovimento().mover(deslocamentoX, deslocamentoY, deslocamentoZ);
        }


        if(
            robo.getX() == finalX &&
            robo.getY() == finalY &&
            robo.getZ() == finalZ
        ){
            System.out.println("Missão finalizada");
            isAtivo = false;
        }
        

    }

    @Override
    public boolean isAtivo() {
        return isAtivo;
    }

    private ArrayList<int[]> acharCaminho(Robo robo, Ambiente ambiente, int[] pontoFinal){
        PontoCaminho ponto = new PontoCaminho(robo.getX(), robo.getY(), robo.getZ());

        int largura = ambiente.get_largura();
        int comprimento = ambiente.get_comprimento();
        int altura = ambiente.get_altura();
        boolean[][][] visitado = new boolean[largura][comprimento][altura]; //Inicia com todos os valores como false
        visitado[robo.getX()][robo.getY()][robo.getZ()] = true;

        TipoEntidade mapa[][][] = ambiente.getMapa();

        if(!recursaoAcharCaminho(robo, ambiente, ponto, pontoFinal, visitado, mapa)){
            return null;
        }

        return ponto.getCaminho();
    }


    private boolean recursaoAcharCaminho(Robo robo, Ambiente ambiente, PontoCaminho pontoAtual, int[] pontoFinal, boolean [][][] visitado, TipoEntidade mapa[][][]){
        //Chegou no ponto
        if(pontoAtual.compararPonto(pontoFinal)){
            return true;
        }

        //System.out.printf("%d,%d,%d\n", pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ());

        //Recursão
        

        if (mapa[finalX][finalY][finalZ] != TipoEntidade.VAZIO){
            return false;
        }

        // Dirações: x, y, z (6 movimentos possíveis)
        int dirX = (finalX > pontoAtual.getX())? 1 : -1;
        int dirY = (finalY > pontoAtual.getY())? 1 : -1;
        int dirZ = (finalZ > pontoAtual.getZ())? 1 : -1;

        ArrayList<int[]> dir = new ArrayList<>();
        ArrayList<int[]> despriorizado = new ArrayList<>();

        if(finalX != pontoAtual.getX()){
            dir.add(new int[]{dirX, 0, 0});
        } else {
            despriorizado.add(new int[]{dirX, 0, 0});
        }
        if(finalY != pontoAtual.getY()){
            dir.add(new int[]{0, dirY, 0});
        } else {
            despriorizado.add(new int[]{0, dirY, 0});
        }
        if(finalZ != pontoAtual.getZ()){
            dir.add(new int[]{0, 0, dirZ});
        } else {
            despriorizado.add(new int[]{0, 0, dirZ});
        }
        dir.addAll(despriorizado);
        for (int i = 0, size = dir.size(); i < size; i++){
            dir.add(Arrays.copyOf(dir.get(i), dir.get(i).length));
        }


        for(int i = 3; i < 6; i ++){
            dir.get(i)[0] *= -1;
            dir.get(i)[1] *= -1;
            dir.get(i)[2] *= -1;
        }


        for(int i = 0; i < 6; i++){
            int novoX = pontoAtual.getX() + dir.get(i)[0];
            int novoY = pontoAtual.getY() + dir.get(i)[1];
            int novoZ = pontoAtual.getZ() + dir.get(i)[2];

            if(!ambiente.dentroDosLimites(novoX, novoY, novoZ) || ambiente.getMapa()[novoX][novoY][novoZ] != TipoEntidade.VAZIO){
                continue;
            }

            if(visitado[novoX][novoY][novoZ]){
                continue;
            }

            pontoAtual.setX(novoX);
            pontoAtual.setY(novoY);
            pontoAtual.setZ(novoZ);

            int[] direcao = {dir.get(i)[0], dir.get(i)[1], dir.get(i)[2]};
            pontoAtual.addDirecao(direcao);

            visitado[pontoAtual.getX()][pontoAtual.getY()][pontoAtual.getZ()] = true;

            if(recursaoAcharCaminho(robo, ambiente, pontoAtual, pontoFinal, visitado, mapa)){
                return true;
            } 

            pontoAtual.setX(pontoAtual.getX() - dir.get(i)[0]);
            pontoAtual.setY(pontoAtual.getY() - dir.get(i)[1]);
            pontoAtual.setZ(pontoAtual.getZ() - dir.get(i)[2]);
            pontoAtual.removeLastDirecao();
        }

        return false;
    }

    @Override
    public String getName(){
        return "Buscar Ponto";
    }

}
