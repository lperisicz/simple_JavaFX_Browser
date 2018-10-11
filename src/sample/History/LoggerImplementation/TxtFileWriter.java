package sample.History.LoggerImplementation;

import sample.History.Data;
import sample.History.LoggerAbstractions.IFileWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TxtFileWriter implements IFileWriter {

    public void write(Data data, String filePath) {
        try(FileWriter fileWriter = new FileWriter(filePath,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter)){

            printWriter.println(data.toString());


        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
