/*
 * Main.java
 * 
 * Simulação de um ambiente virtual para a testar robos.
 */
package main;

import Console.Console;
import ambiente.*;
import iofiles.LeitorConfiguracao;
import iofiles.OutputLog;
import java.io.File;

/**
 * Arquivo principal para iniciação do programa
 */
public class Main {
    public static void main(String[] args) {
        //Limpar logs antigos
        OutputLog.clearLog();

        //Importar configurações do arquivo de propriedades
        String path = "src" + File.separator + "resources" + File.separator + "config.txt";

        //Ambiente
        Ambiente ambiente = LeitorConfiguracao.lerConfiguracao(path);

        //Começar programa
        if(ambiente != null){
            Console menu = new Console(ambiente);

            //Menu
            menu.mainMenu();
        }
        
    }}