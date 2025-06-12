package iofiles;

import java.util.Scanner;

public class ScannerGlobal {
    static final Scanner scanner = new Scanner(System.in);

    /**
     * Scan um numero sem gerar erros
     */
    static public int scannerNumber(){
        int resposta=0;
        boolean passou = false;

        while (!passou) { 
            try {
                resposta = scanner.nextInt();
                passou = true;
            } catch (Exception e) {
                System.out.println("Insira um numero");
                scanner.nextLine();
            }
            
        }        

        return resposta;
    }

    /**
     * Scan de uma linha
     */
    static public String nextLine(){
        scanner.nextLine();
        String resp = scanner.nextLine();
        
        return resp;

    }

    /**
     * Fecha a instância do scanner
     */
    static public void close(){
        scanner.close();;
    }


}
