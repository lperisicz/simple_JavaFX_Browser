package sample.History;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Data> history;
    private Data current;
    private BooleanProperty next;
    private BooleanProperty back;

    public History(){
        history = new ArrayList<>();
        next = new SimpleBooleanProperty();
        back = new SimpleBooleanProperty();
    }

    public Data getCurrent() {
        return current;
    }
    public void addToHistory(String url){
        removeAllLater();
        Data newData = new Data(url);
        history.add(newData);
        current = newData;
    }
    public void next(){
        List<Data> laters = findAllLater(current);
        if(laters.size() != 0){
            current = findMin(laters);
        }
    }
    public void previous(){
        List<Data> befors = findAllBefore(current);
        if(befors.size() != 0){
            current = findMax(befors);
        }
    }
    public ObservableValue<Boolean> isLast(){
        if(findAllLater(getCurrent()).size() > 0){
            next.setValue(false);

        }else next.setValue(true);
        return next;
    }
    public ObservableValue<Boolean> isFirst(){
        if(findAllBefore(getCurrent()).size() > 0){
            back.setValue(false);

        }else back.setValue(true);
        return back;
    }

    private void removeAllLater(){
        if(current != null){
            for (Data data: history) {
                if(data.getTimeVisited().after(current.getTimeVisited())){
                    //history.remove(data);
                }
            }
        }
    }
    private List<Data> findAllLater(Data current){
        List<Data> laters = new ArrayList<>();
        for (Data data:history) {
            if(data != current && data.getTimeVisited().after(current.getTimeVisited()))
                laters.add(data);
        }
        return laters;
    }
    private Data findMin(List<Data> data){
        Data min = data.get(0);
        for (Data url:data) {
            if(url.getTimeVisited().before(min.getTimeVisited()))
                min = url;
        }
        return min;
    }
    private List<Data> findAllBefore(Data current){
        List<Data> befors = new ArrayList<>();
        for (Data data:history) {
            if(data != current && data.getTimeVisited().before(current.getTimeVisited()))
                befors.add(data);
        }
        return befors;
    }
    private Data findMax(List<Data> data){
        Data max = data.get(0);
        for (Data url:data) {
            if(url.getTimeVisited().after(max.getTimeVisited()))
                max = url;
        }
        return max;
    }
}