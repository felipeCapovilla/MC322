public class RoboVoadorTurista extends RoboAereo{

    private int numero_passageiros;
    private int capacidade_maxima;
    private String cidade_turistica;
    private boolean em_passeio;

    public RoboVoadorTurista(String nome,int posicaoX, int posicaoY, String direcao,int altitude,int altitude_max,int capacidade_maxima){
        super(nome,posicaoX,posicaoY,direcao,altitude,altitude_max);
        
        this.capacidade_maxima = capacidade_maxima;
        this.numero_passageiros = 0;
        this.cidade_turistica = "";
        this.em_passeio = false;


    }

    protected void inciar_passeio(int numero_passageiros, String cidade_turistica){
        if(numero_passageiros > this.capacidade_maxima){
            throw new IllegalArgumentException("A capacidade maxima de "+this.capacidade_maxima+"passageiros, foi excedida. \n Circulação não permitida.");
        }
        this.numero_passageiros = numero_passageiros;
        this.cidade_turistica = cidade_turistica;
        this.em_passeio = true;
    }   

    protected int get_numero_passageiros(){
        return this.numero_passageiros;
    }

    protected boolean get_status(){
        return this.em_passeio;
    }

    protected String get_destino(){
        return this.cidade_turistica;
    }
}