package ru.yandex.gymproject.model;

import java.util.Objects;

public class Group {
    private String title;
    private Age age;

    @Override
    public String toString() {
        return "ru.yandex.gymproject.model.Group{" +
                "title='" + title + '\'' +
                ", age=" + age +
                ", duration=" + duration +
                '}';
    }

    private int duration;

    public Group(String title, Age age, int duration) {
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return duration == group.duration && Objects.equals(title, group.title) && age == group.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, age, duration);
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }
}
