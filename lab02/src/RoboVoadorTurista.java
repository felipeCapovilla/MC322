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


    /**
     * Inicia o turismo do robo.
     * @param numero_passageiros Indica a quantidade de passageiros atual presentes.
     * @param cidade_turistica Indica o destino do passeio turistico do Robo.
     */
    protected void inciar_passeio(int numero_passageiros, String cidade_turistica){
        if(numero_passageiros > this.capacidade_maxima){ //Verifica se a quantidade de passageiros e permitida.
            throw new IllegalArgumentException("A capacidade maxima de "+this.capacidade_maxima+"passageiros, foi excedida. \n Circulação não permitida.");
        }
        this.numero_passageiros = numero_passageiros;
        this.cidade_turistica = cidade_turistica;
        this.em_passeio = true;
    }   


    /**
     * Retorna o numero de passageiros no robo.
     */
    protected int get_numero_passageiros(){
        return this.numero_passageiros;
    }


    /**
     * Retorna se o robo esta em servico.
     */
    protected boolean get_status(){
        return this.em_passeio;
    }


    /**
     * Retorna o destino turistico do robo.
     */
    protected String get_destino(){
        return this.cidade_turistica;
    }
}