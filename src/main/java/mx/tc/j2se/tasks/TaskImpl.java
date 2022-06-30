package mx.tc.j2se.tasks;

import com.sun.xml.internal.txw2.IllegalAnnotationException;

/**
 * this class is for creating a task which could be repetitive or non-repetitive
 *
 * @author Benjamin Sanchez Martinez
 */
public class TaskImpl implements Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
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
    public  TaskImpl(String title, int time){
        // throwing the exception if time is negative
        if (time < 0){
            throw new IllegalAnnotationException("Date cannot be negative");
        }
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
    public TaskImpl(String title, int start, int end, int interval){
        if (start < 0){
            // Exception if the start time is less than zero
            throw new IllegalAnnotationException("Start parameter cannot be negative");
        } else if (interval <= 0) {
            // Exception if the interval is zero or less
            throw new IllegalAnnotationException("The interval cannot be zero or negative");
        } else if (end<=start) {
            // Exception if the end is greater than start
            throw new IllegalAnnotationException("The end parameter is greater or equal than the start");
        }
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
    public int getTime() {
        if (this.repeated == false) {
            return this.time;
        }
        else {
            return this.start;
        }
    }

    /**
     * This method sets the time of the task, if the task is repetitive, will turn the task into a non-repetitive and set
     * the time, otherwise, just will set the time of the task
     * @param time the time when a non-repetitive task will be executed
     */
    public void setTime(int time) {
        if (time < 0){
            // Exception if time is negative
            throw new IllegalAnnotationException("The time cannot be negative");
        }
        if (this.repeated == false){
            this.time = time;
        }
        else {
            this.time = time;
            this.repeated = false;
        }
    }

    /**
     * This method gets the time when the task is executed for first or only time (in case of a non-repetitive task)
     * @return the time when the task is executed
     */
    public int getStartTime() {
        if (this.repeated == false){
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
    public int getEndTime() {
        if (this.repeated == true){
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
    public int getRepeatInterval() {
        if (this.repeated == false){
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
    public void setTime(int start, int end, int interval) {
        if (start<0){
            // Exception if start is negative
            throw new IllegalAnnotationException("The start parameter cannot be set with a negative value");
        } else if (interval<=0) {
            // Exception if the interval is zero or negative
            throw new IllegalAnnotationException("The interval cannot be set in zero or a negative value");
        } else if (end>=start) {
            // Exception if the end parameter is equal or greater than the start
            throw new IllegalAnnotationException("The end parameter cannot be greater or equal than start parameter");
        }
        if (this.repeated == false){
            repeated = true;
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
    public int nextTimeAfter (int current) {
        if (current<0){
            // Exception if the current time is negative
            throw new IllegalAnnotationException("The current time cannot be negative");
        }
        int a = -1;
        if (this.repeated == true && this.active == true) {
            for (int i = this.start; i <= this.end; i = i + this.interval) {
                if (current < this.start){
                    a = this.start;
                    break;
                } else if (i == current && i != this.end) {
                    a = (i + this.interval);
                    break;
                } else if (i > current && current != i-this.interval) {
                    a = i;
                    break;
                } else if ((i == current && i == this.end) || current > this.end ) {
                    a = -1;
                    break;
                }
            }
        } else if (this.repeated == false && this.active == true) {
            if (current < this.time){
                a = this.time;
            }else {
                a = -1;
            }
        }
        return a;
    }
}