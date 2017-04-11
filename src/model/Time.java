package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by staho on 10.04.2017.
 */
public class Time {
    private LongProperty millis;
    private LongProperty seconds;
    private LongProperty minutes;
    private LongProperty hours;
    private StringProperty timeString;

    public SimpleStringProperty timeString(){
        String temp = String.format("%02d:%02d:%02d:%d", hours.get(), minutes.get(), seconds.get(), millis.get());

        return new SimpleStringProperty(temp);
    }

    public void setMillis(long millis) {
        this.millis.set(millis);
    }

    public void setSeconds(long seconds) {
        this.seconds.set(seconds);
    }

    public void setMinutes(long minutes) {
        this.minutes.set(minutes);
    }

    public void setHours(long hours) {
        this.hours.set(hours);
    }

    public Time() {
        this.millis = new SimpleLongProperty(0);
        this.seconds = new SimpleLongProperty(0);
        this.minutes = new SimpleLongProperty(0);
        this.hours = new SimpleLongProperty(2);

        String temp = String.format("%02d:%02d:%02d:%d", hours.get(), minutes.get(), seconds.get(), millis.get());

        this.timeString = new SimpleStringProperty(temp);

    }
}
