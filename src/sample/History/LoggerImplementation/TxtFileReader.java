package sample.History.LoggerImplementation;

import sample.History.Data;
import sample.History.LoggerAbstractions.IFileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TxtFileReader implements IFileReader{
    private SimpleDateFormat formatter;

    public TxtFileReader() {
        formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
    }

    public List<Data> read(String filePath) {
        List<Data> data = new ArrayList<>();

        try{
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line != null){
                //System.out.println(line);
                String[] parts = line.split("\\|");
                try {
                    data.add(new Data(parts[1], formatter.parse(parts[0])));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                line = bufferedReader.readLine();
            }
            return data;
        }catch(IOException e){
            e.printStackTrace();
        }

        return data;
    }
}