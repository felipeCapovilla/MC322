package interfaces;

import ambiente.Ambiente;
import robo.standart.Robo;

public interface Missao {
    public void executar ( Robo r , Ambiente a ) ;

    public boolean isAtivo() ;
}
