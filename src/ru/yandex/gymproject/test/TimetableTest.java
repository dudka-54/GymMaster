package ru.yandex.gymproject.test;

import ru.yandex.gymproject.service.Timetable;
import ru.yandex.gymproject.model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.yandex.gymproject.util.TimeOfDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        int expectedMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        int actualMonday = 1;
        Assertions.assertEquals(expectedMonday, actualMonday);
        //Проверить, что за понедельник вернулось одно занятие

        int expectedTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        int actualTuesday = 0;
        Assertions.assertEquals(expectedTuesday, actualTuesday);
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        int expectedMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        int actualMonday = 1;
        Assertions.assertEquals(actualMonday, expectedMonday);
        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> trainingThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(trainingThursday.getFirst(), thursdayChildTrainingSession);
        Assertions.assertEquals(trainingThursday.getLast(), thursdayAdultTrainingSession);

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> expectedTuesdayIsEmpty = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(expectedTuesdayIsEmpty.isEmpty());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        List<TrainingSession> mondayTraining13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, mondayTraining13.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> mondayTraining14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(mondayTraining14.isEmpty());
    }

    @Test
    public void testGetCountByCoachesAdultGroup() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        int expected = timetable.getCountByCoaches().get(coach);
        int actual = 1;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetCountByCoachesChildGroupMoreOne() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        ;
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        int expected = timetable.getCountByCoaches().get(coach);
        int actual = 3;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetCountByCoaches() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Сергеев", "Сергей", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Map<Coach, Integer> coachCountMap = timetable.getCountByCoaches();
        List<Integer> values = new ArrayList<>(coachCountMap.values());


        int expected1 = values.get(1);
        int actual1 = 1;
        Assertions.assertEquals(actual1, expected1);

        int expected2 = values.getFirst();
        int actual2 = 3;
        Assertions.assertEquals(actual2, expected2);

    }

    @Test
    public void testGetCountByCoachesBoundaryValue() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Сергеев", "Сергей", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession);

        Coach coach3 = new Coach("Андреев","Андрей", "Андреевич");
        Group groupAdult2 = new Group("Литрбол", Age.ADULT,60);
        TrainingSession trainingSession3 = new TrainingSession(groupAdult2, coach3,DayOfWeek.FRIDAY,new TimeOfDay(21,0));
        TrainingSession trainingSession4 = new TrainingSession(groupAdult2, coach3, DayOfWeek.SATURDAY,new TimeOfDay(12,0));
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);

        Map<Coach, Integer> coachCountMap = timetable.getCountByCoaches();
        List<Integer> values = new ArrayList<>(coachCountMap.values());

        Assertions.assertEquals(2,values.getFirst());
        Assertions.assertEquals(1,timetable.getCountByCoaches().get(coach2));
        Assertions.assertEquals(1,timetable.getCountByCoaches().get(coach1));
        //одновременно тест сортировки по убыванию и граничащий
    }

    @Test
    public void testGetCountByCoachesNullList() {
        Timetable timetable = new Timetable();
        Assertions.assertTrue(timetable.getCountByCoaches().isEmpty());
    }
}
