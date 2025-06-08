package modulos;


import robos.Robo;
import java.utils.ArrayList;
import exceptions.NoRobotException;

public class ModuloComunicacao(){
    private String nome;
    private Robo robo_associado;
    private boolean comunicacao_disponivel;

    public ModuloComunicacao(String nome, Robo robo_associado){
        this.nome = nome;
        this.robo_associado = robo_associado;
        this.comunicacao_disponivel = true;
    }
    public ModuloComunicacao(String nome,Robo robo_associado,boolean comunicacao_disponivel){
        this.nome = nome;
        this.robo_associado=robo_associado;
        this.comunicacao_disponivel=comunicacao_disponivel;
    }

    public void ativar_comunicacao(){
        this.comunicacao_disponivel=true;
    }

    public void desativar_comunicacao(){
        this.comunicacao_disponivel=false;
    }

    public void set_roboAssociado(Robo novo_robo){
        this.robo_associado = novo_robo;
    }

    public Robo get_roboAssociado(){
        if(this.robo_associado==null){
            throw new NoRobotException("Nao existe nenhum robo associado a essa operacao.z")
        }
        return this.robo_associado;
    }

    public boolean get_statusComunicacao(){
        return this.comunicacao_disponivel;
    }

    public String get_nome(){
        return this.nome;
    }


}