package Console;

public class MenuItem {

    final int indice;
    final String descricao;
    final Runnable funcao;


    public MenuItem(int indice, String descricao, Runnable funcao){
        this.indice = indice;
        this.descricao = descricao;
        this.funcao = funcao;
    }

    /**
     * Retorna o indice do item
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Retorna a descricao do item
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a função do item
     */
    public Runnable getFuncao() {
        return funcao;
    }

}
