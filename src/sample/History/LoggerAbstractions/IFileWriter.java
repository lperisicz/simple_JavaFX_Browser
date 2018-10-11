package sample.History.LoggerAbstractions;

import sample.History.Data;

public interface IFileWriter {

    void write(Data data, String filePath);
}
