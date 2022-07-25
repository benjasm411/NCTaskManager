package mx.tc.j2se.tasks;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
//public class TasksImpl implements Tasks{

    /**
     * This is an empty constructor, which will not add or modify information to the program
     */
    public Tasks(){}
    //public TasksImpl(){}

    /**
     * This method indicates the tasks that will be done in a certain time
     * @param tasks is the iterator that allows us to manipulate each element in the collection
     * @param start is the time when the method starts to search
     * @param end is the time when the method ends to search
     * @return a list with the elements that are between that time
     */
    public static Iterator<Task> incoming(Iterator<Task> tasks, LocalDateTime start, LocalDateTime end){
    //public Iterator<Task> incoming (Iterator<Task> tasks, LocalDateTime start, LocalDateTime end){
        /*
        // Exceptions
        if (start == null || end == null){
            throw new NullPointerException("The start or end time needs to be not null");
        } else if (start.isAfter(end)) {
            throw new IllegalStateException("Start time needs to be before the end time");
        } else if (tasks == null) {
            throw new NullPointerException("The tasks list cannot be null");
        }
         */

        /*
        // Method
        AbstractTaskList iterator = null;

        if (LinkedTaskListImpl.class.equals(tasks.getClass())){
            iterator = new LinkedTaskListImpl();
        } else if (ArrayTaskListImpl.class.equals(tasks.getClass())){
            iterator = new ArrayTaskListImpl();
        }
         */
        ArrayList<Task> list = new ArrayList<>();

        for (Iterator<Task> it = tasks; it.hasNext(); ) {
            Task task = it.next();
            if (task.isActive()
                    && task.nextTimeAfter(start).isBefore(end)
                    && task.nextTimeAfter(start).isAfter(start)) {
                //System.out.println(task);
                list.add(task);
            }
        }
        return list.iterator();
    }

    /**
     * This method make a sorted map of the tasks
     * @param tasks
     * @param start
     * @param end
     * @return
     */
    //public SortedMap<LocalDateTime, Set<Task>> calendar(Iterator<Task> tasks, LocalDateTime start, LocalDateTime end){
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterator<Task> tasks, LocalDateTime start, LocalDateTime end){

        Iterator<Task> filter = incoming(tasks, start, end);
        SortedMap <LocalDateTime, Set<Task>> calendar = new TreeMap<>();

        for (Iterator<Task> it = filter; it.hasNext(); ) {
            Task task = it.next();

            Set <Task> set = new HashSet<>();
            LocalDateTime startTime = task.nextTimeAfter(start);

            set.add(task);

            while(startTime.isBefore(end)){
                calendar.put(startTime, set);
                startTime = startTime.plusHours(task.getRepeatInterval());
            }
        }
        return calendar;
    }
}
