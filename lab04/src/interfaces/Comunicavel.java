package interfaces;

public interface Comunicavel{

    /**
     * Realiza o envio da mensagem para um objeto que tambem e comunicavel e envia o conteudo.
     */
    void enviarMensagem(Comunicavel destinatario, String mensagem);

    /**
     * Receber a mensagem.
     */
    void receberMensagem(String mensagem);
}