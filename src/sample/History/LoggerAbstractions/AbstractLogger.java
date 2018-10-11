package sample.History.LoggerAbstractions;

public abstract class AbstractLogger implements IFileReader, IFileWriter {

    private String filePath;
    private IFileReader reader;
    private IFileWriter writer;

    public AbstractLogger(String filePath, IFileReader reader, IFileWriter writer) {
        this.filePath = filePath;
        this.reader = reader;
        this.writer = writer;
    }

    public String getFilePath() {
        return filePath;
    }

    public IFileReader getReader() {
        return reader;
    }

    public IFileWriter getWriter() {
        return writer;
    }
}
