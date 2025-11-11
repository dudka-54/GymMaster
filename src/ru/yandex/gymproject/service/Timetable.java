package ru.yandex.gymproject.service;

import ru.yandex.gymproject.model.Coach;
import ru.yandex.gymproject.model.DayOfWeek;
import ru.yandex.gymproject.model.TrainingSession;
import ru.yandex.gymproject.util.TimeOfDay;

import java.util.*;

public class Timetable {
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private HashMap<DayOfWeek, List<TrainingSession>> flatTimetable = new HashMap<>();
// Создаю плоский список, чтобы поддерживались методы get О(1)


    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if(!timetable.containsKey(day)){
            timetable.put(day, new TreeMap<>(Comparator.comparingInt(TimeOfDay::getHours)
                    .thenComparingInt(TimeOfDay::getMinutes)
            ));
        }
        if(!flatTimetable.containsKey(day)) {
            flatTimetable.put(day, new ArrayList<>());
        }
        TreeMap<TimeOfDay, List<TrainingSession>> dayTree = timetable.get(day);
        if(!dayTree.containsKey(time)){
            dayTree.put(time,new ArrayList<>());
        }
        dayTree.get(time).add(trainingSession);

        List<TrainingSession> daySessions = flatTimetable.get(day);
        daySessions.add(trainingSession);
        daySessions.sort(Comparator.<TrainingSession, TimeOfDay>comparing(TrainingSession::getTimeOfDay)
        );
    }


public TrainingSession getOnlyOneTrainingSession(List<TrainingSession> trainingSessions){
        if(trainingSessions.isEmpty()){
            throw new IllegalArgumentException("Список пуст");
        }
        if(trainingSessions.size() > 1){
            throw new IllegalArgumentException("В списке больше одной тренировки: " + trainingSessions.size());        } else {
            return trainingSessions.getFirst();
        }
}
    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return flatTimetable.getOrDefault(dayOfWeek, Collections.emptyList());

    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).getOrDefault(timeOfDay, Collections.emptyList());
    }

    public LinkedHashMap<Coach, Integer> getCountByCoaches(){
        LinkedHashMap<Coach, Integer> coachTrainCount = new LinkedHashMap<>();

         for(DayOfWeek dayOfWeek : flatTimetable.keySet()){
             for(TrainingSession trainingSession : flatTimetable.get(dayOfWeek)){
                 Coach coach = trainingSession.getCoach();
                 if(coachTrainCount.containsKey(coach)){
                     coachTrainCount.put(coach,coachTrainCount.get(coach) + 1);
                 } else {
                     coachTrainCount.put(coach, 1);
                 }
             }
         }

        List<Map.Entry<Coach, Integer>> entryList = new ArrayList<>(coachTrainCount.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        LinkedHashMap<Coach, Integer> sortedMap = new LinkedHashMap<>();

        for(Map.Entry<Coach, Integer> entry : entryList){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
