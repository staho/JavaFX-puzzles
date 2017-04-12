package model;

import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Observable;

/**
 * Created by staho on 10.04.2017.
 */
@XmlRootElement(name = "times")
public class TimeTableWrapper {

    private List<Time> timeList;

    @XmlElement(name = "time")
    public List<Time> getTimeList(){return timeList;}

    public void setTimeList(List<Time> timeList){this.timeList = timeList;}

}
