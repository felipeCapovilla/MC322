package central_comunicacao;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CentralComunicacao{

    private final ArrayList<String> mensagens;
    private final ArrayList<String> remetentes_associados; //Array com remetentes das mensagens.

    public CentralComunicacao(){
        this.mensagens = new ArrayList<>();
        this.remetentes_associados = new ArrayList<>();
    }

    /**
     * Note que: os remetentes sao colocados em indices similares as mensagens, de modo que ao buscar uma mensagem em O(n) o remetente e encontrado em O(1)
     */
    public void registrarMensagem(String remetente, String msg){
        mensagens.add(msg);
        remetentes_associados.add(remetente);
    }
    /**
     * Imprime todas as mensagens seguindo o formato: 
     *  Remetente: <nome>
     *  Mensagem: <mensagem>
     */
    public void exibirMensagens(){
        System.out.printf("\n+---------MensagensTrocadas---------+\n");
        for(int i =0; i<mensagens.size();i++){
            System.out.printf("Remetente: %s\nMensagem: %s\n",remetentes_associados.get(i),mensagens.get(i));
        }
        System.out.println();
    }

    /**
     * Retorna a quantidade de mensagens acumuladas na central.
     */
    public int get_quantidadeMensagens(){
        return mensagens.size();
    }


    /**
     * A partir da mensagem encontra o remetente.
     */
    public String getRemetente(String mensagem){
        int indice = mensagens.indexOf(mensagem);
        
        if(indice == -1){ //Mensagem nao encontrada.
            throw new NoSuchElementException("Mensagem nao encontrada.");
        }

        return remetentes_associados.get(indice);
    }

    /**
     * Apaga mensagems da central.
     */
    public void removerMensagem(String mensagem){
        int indice = mensagem.indexOf(mensagem);
        if(indice == -1){
            throw new NoSuchElementException("Mensagem nao encontrada");
        }
        this.mensagens.remove(indice); //Remove tanto a mensagem quanto o remetente, para manter a paridade.
        this.remetentes_associados.remove(indice);
    }
}