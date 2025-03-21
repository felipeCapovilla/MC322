public class RoboAereo extends Robo {
    
    private int altitude;
    private int altitude_max;

    public RoboAereo(String nome,int posicaoX, int posicaoY, String direcao,int altitude,int altitude_max){
        
        super(nome, posicaoX, posicaoY, direcao); //Chama o construtor da super-classe Robo.
        this.altitude = altitude;
        this.altitude_max = altitude_max;
    }

    public int subir(int metros){
        
        if(this.altitude + metros > this.altitude_max){
            throw new IllegalArgumentException("A altitude do robo nao pode ultrapassar "+this.altitude_max+"m."); //Se a altura somada a atual for maior que o permitido, lança erro.
        }

        this.altitude+=metros; //Adiciona a altitude.
        
        return this.altitude; //Retorna a nova altitude.

    }

    public int descer(int metros){
        if(this.altitude - metros <= 0){ //Verifica viabilidade de nova altitude descendente.
            throw new IllegalArgumentException("A altitude do robo não pode ser <= 0m.");
        }
    
        this.altitude -=metros; //Ajusta altitude.
        
        return this.altitude; //Retorna nova altitude.
    
    }


    
}
