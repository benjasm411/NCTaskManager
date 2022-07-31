package mx.tc.j2se.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * this class is for creating a task which could be repetitive or non-repetitive
 *
 * @author Benjamin Sanchez Martinez
 */
public class TaskImpl implements Task, Cloneable, Serializable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private long interval;
    private boolean active;
    private boolean repeated;

    /**
     * This is an empty constructor, which will not add or modify information to the program
     */
    public TaskImpl(){}

    /**
     * This constructor add a task that is non-repetitive
     * @param title it's the title of the task
     * @param time it's the exact time when the task will be executed
     */
    public  TaskImpl(String title, LocalDateTime time){
        /*
        // throwing the exception if time is negative
        if (time == null){
            throw new NullPointerException("Date cannot be negative");
        }
         */
        time = LocalDateTime.of(time.getYear(),
                                time.getMonth(),
                                time.getDayOfMonth(),
                                time.getHour(),
                                time.getMinute(),
                                time.getSecond(),
                                time.getNano());
        this.title = title;
        this.time = time;
        this.repeated = false;
    }

    /**
     * This constructor add a repetitive task
     * @param title it's the name of the task
     * @param start it's the time when the task will execute
     * @param end it's the time when the repetition will end
     * @param interval it's how often the task will execute
     */
    public TaskImpl(String title, LocalDateTime start, LocalDateTime end, long interval){
        /*
        if (start == null || end == null){
            // Exception if the start or end times are less than zero
            throw new NullPointerException("The parameters cannot be null");
        } else if (interval <= 0) {
            // Exception if the interval is equal or less than 0
            throw new IllegalAnnotationException("The interval must be positive greater than zero");
        } if (start.isAfter(end)){
            // Exception if the start time begin after the end time
            throw  new IllegalStateException("The start time must be before the end time");
        }
         */

        start = LocalDateTime.of(start.getYear(),
                start.getMonth(),
                start.getDayOfMonth(),
                start.getHour(),
                start.getMinute(),
                start.getSecond(),
                start.getNano());
        end = LocalDateTime.of(end.getYear(),
                end.getMonth(),
                end.getDayOfMonth(),
                end.getHour(),
                end.getMinute(),
                end.getSecond(),
                end.getNano());

        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeated = true;
    }

    /**
     * This method is for get the value of the title, doesn't matter if it is a repetitive or non-repetitive task
     * @return tha name of the task
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method sets the value of title, doesn't matter if it is a repetitive or non-repetitive task
     * @param title the title of the task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method returns the state of the task, if it's active or inactive
     * @return the state of the task in boolean form
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * This method sets the state of the task, if it's active or not
     * @param active a boolean that indicates the state of the task
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * this method gets the time of the task, if it's a repetitive one, will return the time when the task start.
     * otherwise, will return the time when the task will execute
     * @return the value of the time when the task is executed for the first time
     */
    public LocalDateTime getTime() {
        if (!this.repeated) {
            return this.time;
        }
        else {
            return this.start;
        }
    }

    /**
     * This method sets the time of the task, if the task is repetitive, will turn the task into a non-repetitive and set
     * the time, otherwise, just will set the time of the task
     *
     * @param time the time when a non-repetitive task will be executed
     */
    public void setTime(LocalDateTime time) {
        /*
        if (time == null){
            // Exception if time is negative
            throw new IllegalAnnotationException("The time cannot be negative");
        }
         */

        time = LocalDateTime.of(time.getYear(),
                time.getMonth(),
                time.getDayOfMonth(),
                time.getHour(),
                time.getMinute(),
                time.getSecond(),
                time.getNano());

        this.time = time;
        this.end = this.start = null;
        this.interval = 0;
        this.repeated = false;

        /*
        if (!this.repeated){
            this.time = time;
        }
        else {
            this.time = time;
            this.repeated = false;
            this.interval = 0;
        }
         */
    }

    /**
     * This method gets the time when the task is executed for first or only time (in case of a non-repetitive task)
     * @return the time when the task is executed
     */
    public LocalDateTime getStartTime() {
        if (!this.repeated){
            return  this.time;
        }
        else {
            return this.start;
        }
    }

    /**
     * this method gets the last or only time (in case of a non-repetitive task)  when a task it's executed
     * @return the last time when the task it's executed
     */
    public LocalDateTime getEndTime() {
        if (this.repeated){
            return this.end;
        }
        else {
            return this.time;
        }
    }

    /**
     * This method gets the execution interval of a repetitive task
     * @return the execution interval in a repetitive task, if it's a non-repetitive task will return 0
     */
    public long getRepeatInterval() {
        if (!this.repeated){
            return 0;
        }
        else {
            return this.interval;
        }
    }

    /**
     * This method sets the value of the times that are used in a repetitive task, if it's non-repetitive, will
     * turn it into a repetitive and set the values
     * @param start the time when the task starts
     * @param end the time when the task ends
     * @param interval the interval of time when each repetition is executed
     */
    public void setTime(LocalDateTime start, LocalDateTime end, long interval) throws IllegalArgumentException{

        if (start == null || end == null){
            // Exception if start is negative
            throw new IllegalArgumentException("The start parameter cannot be set with a negative value");
        } else if (interval<=0) {
            // Exception if the interval is zero or negative
            throw new IllegalArgumentException("The interval cannot be set in zero or a negative value");
        } else if (start.isAfter(end)) {
            // Exception if the end parameter is equal or greater than the start
            throw new IllegalArgumentException("The end parameter cannot be greater or equal than start parameter");
        } else if (start.getClass() != LocalDateTime.class || end.getClass() != LocalDateTime.class) {
            throw new IllegalArgumentException();
        }


        start = LocalDateTime.of(start.getYear(),
                start.getMonth(),
                start.getDayOfMonth(),
                start.getHour(),
                start.getMinute(),
                start.getSecond(),
                start.getNano());
        end = LocalDateTime.of(end.getYear(),
                end.getMonth(),
                end.getDayOfMonth(),
                end.getHour(),
                end.getMinute(),
                end.getSecond(),
                end.getNano());

        if (!this.isRepeated()){
            this.repeated = true;
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
        else {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }

    }

    /**
     * This method indicates the state of the task, if it's repetitive or not
     * @return a boolean value that indicates if the task is repetitive
     */
    public boolean isRepeated() {
        return this.repeated;
    }

    /**
     * This method indicate the next time when the task will be executed, doesn't matter if the task is repetitive or not.
     * @param current the current time, where the next execution is searched for
     * @return the time when the next execution will happen
     */
    public LocalDateTime nextTimeAfter (LocalDateTime current) {
        /*
        if (current == null){
            // Exception if the current time is negative
            throw new IllegalAnnotationException("The current time cannot be negative");
        }
         */
        LocalDateTime a = LocalDateTime.MIN;
        if (this.isRepeated() && this.isActive()) {
            LocalDateTime i = this.start;
            while (!i.isAfter(this.end)) {
                if (current.isBefore(i)){
                    a = i;
                    break;
                } else if (i == current && i != this.end) {
                    a = i.plusHours(interval);
                    break;
                } else if (i.isAfter(current) && current != i.minusHours(interval)) {
                    a = i;
                    break;
                } else if ((i == current && i == this.end) || current.isAfter(this.end) ) {
                    a = LocalDateTime.MIN;
                    break;
                }
                i = i.plusHours(interval);
            }
        } else if (!this.isRepeated() && this.isActive()) {
            if (current.isBefore(this.time)){
                a = this.time;
            }else {
                a = LocalDateTime.MIN;
            }
        }
        return a;
    }

    /**
     * This method says if a task is exactly equal to another one
     * @param o is the task that is going to be compared in the form of an Object
     * @return a boolean that indicates if the task is equal or not
     */
    public boolean equals (Object o){
        Task task = (Task) o;
        boolean state = false;
        if (task == null){
            throw new NullPointerException("The task is null");
        } else if (!this.repeated &&
                   !task.isRepeated() &&
                   this.title.equals(task.getTitle()) &&
                   this.time == task.getTime() &&
                   this.active == task.isActive()) {
            state = true;
        } else if (this.repeated &&
                   task.isRepeated() &&
                   this.title.equals(task.getTitle()) &&
                   this.start == task.getStartTime() &&
                   this.end == task.getEndTime() &&
                   this.active == task.isActive() &&
                   this.interval == task.getRepeatInterval()){
            state = true;
        }
        return state;
    }

    /**
     * This method finds the hash code of the object
     * @return the hashCode of the object
     */
    public int hashCode (){
        int hashCode = this.title.hashCode();
        return hashCode;
    }

    /**
     * This method shows the info inside the task
     * @return the string that shows the Task information
     */
    @Override
    public String toString() {
        if (isRepeated()){
            return
                    "The repetitive task = '" + this.title + "' has the following parameters \n" +
                    "start= " + this.start +
                    ", end= " + this.end +
                    ", interval= " + this.interval +
                    ", active= " + this.active +
                    ", repeated= " + this.repeated +
                    '\n';
        }else {
            return
                    "The non-repetitive task= '" + this.title + "' has the following parameters \n" +
                    "time= " + this.time +
                    ", active= " + this.active +
                    ", repeated= " + this.repeated +
                    '\n';
        }
    }

    /**
     * This method clones a task
     * @return the task cloned
     * @throws CloneNotSupportedException
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}