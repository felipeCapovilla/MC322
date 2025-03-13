public class Ambiente {

    private int largura;
    private int altura;

    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        //Obs: assumindo que a extremidade esquerda inferior do ambiente se inicia em (0,0) do sistema cartesiano.
    }

    public boolean dentroDosLimites(int x, int y) {
        if((x<=this.largura&&x>0)&&(y<=this.altura&&y>0)){ //Verifica se o robo esta dentro do ambiente, considerando a observação posta.
            return true;
        }
        return false;

    }



}
