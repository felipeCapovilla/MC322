package ambiente;

public class Ambiente {

    private int largura;
    private int altura;

    /**
     * Cria um plano cartesiano. A extremidade esquerda inferior do ambiente se inicia em (0,0).
     * @param largura X -> [0, largura-1]
     * @param altura Y -> [0, altura-1]
     */
    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
    }

    /**
     * Verifica se o robo esta dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y) {
        return (x <largura &&x >=0)&&(y<altura && y>=0);
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }



}
