package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.concurrent.TimeUnit;

/**
 * Created by staho on 10.04.2017.
 */
public class Time {
    private LongProperty millis;
    private LongProperty seconds;
    private LongProperty minutes;
    private LongProperty hours;
    private StringProperty timeString;
    private LongProperty timeSum;

    public SimpleStringProperty timeString(){
        String temp = String.format("%02d:%02d:%02d:%d", hours.get(), minutes.get(), seconds.get(), millis.get());

        return new SimpleStringProperty(temp);
    }
    public long calculateTimeSum(){
        long temp = millis.get();
        temp += TimeUnit.SECONDS.toMillis(seconds.get());
        temp += TimeUnit.MINUTES.toMillis(minutes.get());
        temp += TimeUnit.HOURS.toMillis(hours.get());
        return temp;
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

    public long getMillis() {
        return millis.get();
    }

    public LongProperty millisProperty() {
        return millis;
    }

    public long getSeconds() {
        return seconds.get();
    }

    public LongProperty secondsProperty() {
        return seconds;
    }

    public long getMinutes() {
        return minutes.get();
    }

    public LongProperty minutesProperty() {
        return minutes;
    }

    public long getHours() {
        return hours.get();
    }

    public LongProperty hoursProperty() {
        return hours;
    }

    public String getTimeString() {
        return timeString.get();
    }

    public StringProperty timeStringProperty() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString.set(timeString);
    }

    public long getTimeSum() {
        return timeSum.get();
    }

    public LongProperty timeSumProperty() {
        return timeSum;
    }

    public void setTimeSum(long timeSum) {
        this.timeSum.set(timeSum);
    }
}
