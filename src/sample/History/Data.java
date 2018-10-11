package sample.History;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
    private Date timeVisited;
    private String pageVisited;

    public Data(String page){
        timeVisited = new Date();
        pageVisited = page;
    }

    public Data(String page, Date date){
        timeVisited = date;
        pageVisited = page;
    }

    public Date getTimeVisited() {
        return timeVisited;
    }

    public String getPageVisited() {
        return pageVisited;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");


        return formatter.format(timeVisited) + '|' +  pageVisited;
    }
}
