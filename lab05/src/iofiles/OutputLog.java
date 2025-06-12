package iofiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputLog {
    final private static String PATH = "src" + File.separator + "resources" + File.separator + "logRegister.txt";
    private static final File logRegister = new File(PATH);
    

    static public void addToLogln(String text){
        try {
            BufferedWriter outData = new BufferedWriter(new FileWriter(logRegister, true));

            outData.write(text);
            outData.newLine();

            outData.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } 
    }

    static public void addToLog(String text){
        try {
            BufferedWriter outData = new BufferedWriter(new FileWriter(logRegister, true));

            outData.write(text);

            outData.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } 
    }

    static public void clearLog(){
        File file = new File(PATH);

        file.delete();
    }




}
