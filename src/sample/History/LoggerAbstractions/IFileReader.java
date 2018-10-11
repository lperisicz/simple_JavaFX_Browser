package sample.History.LoggerAbstractions;

import sample.History.Data;

import java.util.List;

public interface IFileReader {

    List<Data> read(String filePath);

}
