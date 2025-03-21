public class RoboVoadorExplorador extends RoboAereo {

    private int temperatura_atual;
    private int pressao_atual;
    private int velocidade_max;
    private int velocidade_atual;
    private String planeta_atual;
    private boolean em_missao;


    public RoboVoadorExplorador(String nome,int posicaoX, int posicaoY, String direcao,int altitude,int altitude_max,int velocidade_max){
        
        super(nome,posicaoX,posicaoY,direcao,altitude,altitude_max); //Inicializa as variaveis da classe herdada.
        
        this.velocidade_max = velocidade_max; //Atribui vel. maxima.
        //Inicia o robo como 'inativo'.
        this.pressao_atual = 0;
        this.temperatura_atual =0;
        this.velocidade_atual =0;
        this.planeta_atual = "";
        this.em_missao = false;
    }

    protected void iniciar_exploracao(int pressao_atual, int temperatura_atual, int velocidade_atual, String planeta){
        if(velocidade_atual > this.velocidade_max){ //Verifica se a velocidade setada respeita os limites do robo.
            throw new IllegalArgumentException("A velocidade do Robo não pode ultrapassar"+this.velocidade_max+"m/s"); //Se nao for permitido, lança erro.
        }
        this.velocidade_atual = velocidade_atual; //Caso contrario, seta o valor.
        
        if(temperatura_atual < 0){ //Verifica se a temperatura é plausivel.
            throw new IllegalArgumentException("A temperatura nao pode ser menor que 0K"); //Se nao, lança erro.
        }
        this.temperatura_atual = temperatura_atual; //Se sim, seta valor.

        //Atualiza atributos do robo.
        this.em_missao = true; 
        this.pressao_atual = pressao_atual;
        this.planeta_atual = planeta;
    }

    protected int set_temperatura(int nova_temperatura){ //Altera temperatura atual do robo.
        if(nova_temperatura < 0){ //Verifica se a temperatura é plausivel.
            throw new IllegalArgumentException("A temperatura nao pode ser menor que 0K");
        }
        this.temperatura_atual = nova_temperatura; //Se for: seta o valor.

        return nova_temperatura; //Retorna nova temperatura.
    }

    protected int set_pressao(int nova_pressao){
        this.pressao_atual = nova_pressao; //Seta nova pressao.
        return nova_pressao; //Retorna nova_pressao.
    }

    protected int set_velocidade(int nova_velocidade){
        if(nova_velocidade > this.velocidade_max){
            throw new IllegalArgumentException("A velocidade maxima nao deve exceder "+this.velocidade_max+"m/s.");
        }
        this.velocidade_atual = nova_velocidade;
        return nova_velocidade;
    }


    protected int get_temperatura(){
        return this.temperatura_atual;
    }

    protected int get_pressao(){
        return this.pressao_atual;
    }

    protected int get_velocidade(){
        return this.velocidade_atual;
    }
    protected String get_planeta(){
        return this.planeta_atual;
    }
    protected boolean status_missao(){
        return this.em_missao;
    }
    protected void finalizar_exploracao(){
        this.em_missao = false;
        this.planeta_atual = "";
        this.pressao_atual=0;
        this.temperatura_atual=0;
        this.velocidade_atual=0;
    }



}


