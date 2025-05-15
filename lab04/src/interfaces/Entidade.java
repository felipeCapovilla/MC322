package interfaces;

import constantes.TipoEntidade;

public interface Entidade {
    
    /**
     * Retorna posição X
     */
    int getX();

    /**
     * Retorna posição Y
     */
    int getY();

    /**
     * Retorna posição Z
     */
    int getZ();

    /**
     * Retorna o tipo da entidade
     */
    TipoEntidade getTipo();

    /**
     * Descrição da entidade
     */
    String getDescricao();

    /**
     * Representação visual da entidade
     */
    char getRepresentacao();

}
