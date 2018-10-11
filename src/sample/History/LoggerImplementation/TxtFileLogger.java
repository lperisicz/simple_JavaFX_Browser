package sample.History.LoggerImplementation;

import sample.History.Data;
import sample.History.LoggerAbstractions.AbstractLogger;
import sample.History.LoggerAbstractions.IFileReader;
import sample.History.LoggerAbstractions.IFileWriter;

import java.util.List;

public class TxtFileLogger extends AbstractLogger {

    public TxtFileLogger(String filePath, IFileReader reader, IFileWriter writer) {
        super(filePath, reader, writer);
    }

    @Override
    public List<Data> read(String filePath) {
        return this.getReader().read(filePath);
    }

    @Override
    public void write(Data data, String filePath) {
        this.getWriter().write(data,filePath);
    }
}
