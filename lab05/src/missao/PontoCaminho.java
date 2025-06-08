package missao;

import java.util.ArrayList;

public class PontoCaminho {
    private int x;
    private int y;
    private int z;
    private final ArrayList<int[]> caminho;

    public PontoCaminho(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;

        caminho = new ArrayList<>();
    }

    public void addDirecao(int[] direcao_nova){
        caminho.add(direcao_nova);
    }

    public void removeLastDirecao(){
        caminho.removeLast();
    }

    public boolean compararPonto(int[] ponto){
        return (ponto[0] == x && ponto[1] == y && ponto[2] == z);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public ArrayList<int[]> getCaminho() {
        return caminho;
    }



}
