package robo.agenteInteligente;

import ambiente.Ambiente;
import constantes.Bussola;
import interfaces.Missao;
import robo.standart.Robo;

public abstract class AgenteInteligente extends Robo{
    protected Missao missao;

    public AgenteInteligente(String id, int posX, int posY, int posZ, Bussola direcao) {
        super(id, posX, posY, posZ, direcao);
    }

    @Override
    public void executarTarefa() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executarTarefa'");
    }

    public void definirMissao(Missao m){
        this.missao = m;
    }

    public boolean temMissao(){
        return missao != null;
    }

    public abstract void executarMissao ( Ambiente a );

}
