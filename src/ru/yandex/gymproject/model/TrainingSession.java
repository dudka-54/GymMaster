package ru.yandex.gymproject.model;

import ru.yandex.gymproject.util.TimeOfDay;

import java.util.Objects;

public class TrainingSession implements Comparable {
    private Group group;
    private Coach coach;
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSession that = (TrainingSession) o;
        return Objects.equals(group, that.group) && Objects.equals(coach, that.coach) && dayOfWeek == that.dayOfWeek && Objects.equals(timeOfDay, that.timeOfDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, coach, dayOfWeek, timeOfDay);
    }

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }


    @Override
    public String toString() {
        return "ru.yandex.gymproject.model.TrainingSession{" +
                "group=" + group +
                ", coach=" + coach +
                ", dayOfWeek=" + dayOfWeek +
                ", timeOfDay=" + timeOfDay +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(o == null){
            return -1;
        }
        int dayCompare = this.dayOfWeek.compareTo(((TrainingSession)o).dayOfWeek);
        if(dayCompare != 0){
            return dayCompare;
        }
        return this.dayOfWeek.compareTo(((TrainingSession)o).dayOfWeek);


    }
    }
