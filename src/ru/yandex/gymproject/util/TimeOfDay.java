package ru.yandex.gymproject.util;

import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

    private int hours;
    private int minutes;

    @Override
    public String toString() {
        return "ru.yandex.gymproject.util.TimeOfDay{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public int compareTo(TimeOfDay o) {
    if(this.getHours() != o.getHours()){
        return Integer.compare(this.hours, o.getHours());
    }
        return Integer.compare(this.getMinutes(), o.getMinutes());
    }
}
