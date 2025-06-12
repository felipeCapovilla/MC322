package missao;

import ambiente.Ambiente;
import constantes.TipoEntidade;
import exceptions.*;
import interfaces.Missao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import robo.standart.Robo;
import sensor.espacial.SensorEspacial;

public class MissaoPatrulhar implements Missao {

    private boolean isAtivo = false;
    private final ArrayList<int[]> caminho = new ArrayList<>();
    private int tamanhoInicial = 0;

    @Override
    public void executar(Robo robo, Ambiente ambiente) throws NoRobotException, NullPointerException, SensorMissingException, NoModuleException {
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
            boolean running = true;
            isAtivo = true;

            do { 
                squarePath(ambiente);
     
                if(verificarCaminho(robo, ambiente, true)){
                    running = false; // Caminho viável, encerra a missão
                    tamanhoInicial = caminho.size();
                }
            } while (running);

            OutputLog.addToLogln(String.format("== Robo %s iniciou a missao %s ==", robo.getID(), getName()));
            OutputLog.addToLogln(String.format("Caminho quadrado de lado %d", (int)Math.ceil(tamanhoInicial/4)));

        }

        //Algoritmo de movimentação
        //Executar missão
        int deslocamentoX = 0;
        int deslocamentoY = 0;
        int deslocamentoZ = 0;

        boolean running = true;
        OutputLog.addToLog(String.format("\t(%d,%d,%d) -> ", robo.getX(), robo.getY(), robo.getZ()));

        while (running && !caminho.isEmpty()) {

            if(
                (deslocamentoX > 0 && caminho.getFirst()[0] < 0) || (deslocamentoX < 0 && caminho.getFirst()[0] > 0) ||
                (deslocamentoY > 0 && caminho.getFirst()[1] < 0) || (deslocamentoY < 0 && caminho.getFirst()[1] > 0) ||
                (deslocamentoZ > 0 && caminho.getFirst()[2] < 0) || (deslocamentoZ < 0 && caminho.getFirst()[2] > 0) 
                ){
                //Caminho volta, ou seja, não é possível ir direto
                running = false;
            } else {
                deslocamentoX += caminho.getFirst()[0];
                deslocamentoY += caminho.getFirst()[1];
                deslocamentoZ += caminho.getFirst()[2];

                caminho.removeFirst();
            }
            
            if(Math.abs(deslocamentoX) >= raioDeslocamento || Math.abs(deslocamentoY) >= raioDeslocamento || Math.abs(deslocamentoZ) >= raioDeslocamento){
                running = false;
            }
        }

        robo.get_controleMovimento().mover(deslocamentoX, deslocamentoY, deslocamentoZ);
        OutputLog.addToLog(String.format("(%d,%d,%d)", robo.getX(), robo.getY(), robo.getZ()));
        OutputLog.addToLogln(String.format(" == Progresso: %.02f%%", ((((float)(tamanhoInicial - caminho.size()))/tamanhoInicial)*100)));
        System.out.printf("Progresso: %.02f%%\n", ((((float)(tamanhoInicial - caminho.size()))/tamanhoInicial)*100));

        if(
            caminho.isEmpty()
        ){
            System.out.println("Missão finalizada");
            isAtivo = false;
            OutputLog.addToLogln("Missão finalizada\n");
        }

    }

    @Override
    public boolean isAtivo() {
        return isAtivo;
    }


    /**
     * Cria um caminho quadrado para o robô patrulhar
     */
    private void squarePath(Ambiente ambiente){
        Random random = new Random();
        int menorDimensao = Math.min(ambiente.get_largura(), Math.min(ambiente.get_comprimento(), ambiente.get_altura()));
        int lado = random.nextInt((int) (Math.ceil(menorDimensao) / 3)) + 1; // Lado do quadrado

        // Dirações: x, y, z (6 movimentos possíveis)
        int dirX = (random.nextBoolean())? 1 : -1;
        int dirY = (random.nextBoolean())? 1 : -1;
        int dirZ = (random.nextBoolean())? 1 : -1;

        ArrayList<int[]> dir = new ArrayList<>(Arrays.asList(
            new int[]{dirX, 0, 0},
            new int[]{0, dirY, 0},
            new int[]{0, 0, dirZ}
        ));

        Collections.shuffle(dir);
        dir.remove(0); // Remove uma direção aleatória para ter apenas 2 movimentos possiveis

        //criar caminho quadrado
        caminho.clear();
        for(int i = 1; i > -2; i -= 2){ // i = 1, -1
            for(int k = 0; k < 2; k++){
                for(int j = 0; j < lado; j++){
                    caminho.add(new int[]{i * dir.get(k)[0], i * dir.get(k)[1], i * dir.get(k)[2]});
                }
            }
        }

    }

    /**
     * Verifica se o caminho é viável para o robô
     */
    private boolean verificarCaminho(Robo robo, Ambiente ambiente, boolean fechado){
        PontoCaminho pontoAtual = new PontoCaminho(robo.getX(), robo.getY(), robo.getZ());
        int retorno = (fechado)? 1 : 0; // Se fechado, retorna ao ponto inicial

        for(int i = 0; i < caminho.size() - retorno; i++){ //último movimento volta ao ponto inicial
            pontoAtual.setX(pontoAtual.getX() + caminho.get(i)[0]);
            pontoAtual.setY(pontoAtual.getY() + caminho.get(i)[1]);
            pontoAtual.setZ(pontoAtual.getZ() + caminho.get(i)[2]);

            if(!ambiente.dentroDosLimites(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ()) ||
               ambiente.getMapa()[pontoAtual.getX()][pontoAtual.getY()][pontoAtual.getZ()] != TipoEntidade.VAZIO) {
                // Se o ponto é vazio, continua
                return false;
            }
        }

        return true;

    }

    @Override
    public String getName(){
        return "Patrulhar";
    }


}
