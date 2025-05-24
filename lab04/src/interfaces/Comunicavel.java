package interfaces;

import central_comunicacao.CentralComunicacao;

public interface Comunicavel{

    /**
     * Realiza o envio da mensagem para um objeto que tambem e comunicavel e envia o conteudo.
     */
    void enviarMensagem(Comunicavel destinatario, String mensagem);

    /**
     * Receber a mensagem.
     */
    void receberMensagem(String mensagem);

    /**
     * Seta o interemediario pelo qual vai ser mediado a comunicacao.
     */
    void set_CentralComunicao(CentralComunicacao nova_central);

    /**
     * Retorna qual a central de comunicacao esta sendo usada.
     */
    CentralComunicacao get_centralComunicacao();
}