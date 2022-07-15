package mx.tc.j2se.tasks;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class create a list of arrayTaskList that are implemented, the arrayTaskList can be active or inactive as well as repetitive or
 * non-repetitive, the list will only store arrayTaskList.
 * @author Benjamin Sanchez Martinez
 */
//public class ArrayTaskListImpl implements ArrayTaskList, Cloneable, Iterable<Task>{
public class ArrayTaskListImpl extends AbstractTaskList {
    private Task[] arrayTaskList = new Task[0];

    /**
     * This empty constructor function as class initializer, it doesn't change or add any data into the program
     */
    public ArrayTaskListImpl() {
    }

    /**
     * This method adds a task to a list of arrayTaskList that has adjustable length
     *
     * @param task is the task that will be added to the list of arrayTaskList
     */
    public void add(Task task) {
        if (task == null) {
            // Exception in case the task is null
            throw new NullPointerException("The task must exist for being added");
        }

        int tasksArrayLength = this.arrayTaskList.length;
        Task[] copyTasks = new Task[tasksArrayLength + 1];
        if (tasksArrayLength == 0) {
            copyTasks[0] = task;
        } else {
            for (int i = 0; i < tasksArrayLength; i++) {
                copyTasks[i] = this.arrayTaskList[i];
            }
            copyTasks[copyTasks.length - 1] = task;
        }
        this.arrayTaskList = copyTasks;
    }

    /**
     * This method remove a specific task in the array, also adjust the array's length with the remaining arrayTaskList
     *
     * @param task is the task that is going to be removed in the array
     * @return the method return a boolean value, true in case the task exists in the array, otherwise,
     * will return false
     */
    public boolean remove(Task task) {
        if (task == null) {
            // Exception in case the task is null
            throw new NullPointerException("The task must exist for being deleted");
        }

        boolean exist = false;
        int tasksArrayLength = this.arrayTaskList.length;
        Task[] copyTasksSub = new Task[tasksArrayLength - 1];
        for (Task currentTask : this.arrayTaskList) {
            if (currentTask == task) {
                int currentIndex = 0;
                for (int i = 0; i < this.arrayTaskList.length; i++) {
                    if (this.arrayTaskList[i] != task) {
                        copyTasksSub[currentIndex] = this.arrayTaskList[i];
                        currentIndex++;
                    }
                }
                this.arrayTaskList = copyTasksSub;
                exist = true;
                break;
            }
        }
        return exist;
    }

    /**
     * This method count the number of existing arrayTaskList in the array
     *
     * @return the number of arrayTaskList in memory
     */
    public int size() {
        return this.arrayTaskList.length;
    }

    /**
     * This method return a specific task wich is selected in the index argument
     *
     * @param index it's the index in the array of the selected task
     * @return the data of the task in that index
     */
    public Task getTask(int index) {
        if (index < 0) {
            // Exception for negative index
            throw new ArrayIndexOutOfBoundsException("The index needs to be positive or zero");
        } else if (index > this.arrayTaskList.length) {
            // Exception for index greater that the array's length
            throw new ArrayIndexOutOfBoundsException("The index exceed the array's length");
        }

        return this.arrayTaskList[index];
    }

    /**
     * This method creates a stream that allows to work with collections, iterating each of them
     * @return an Stream element
     */
    @Override
    public Stream<Task> getStream(){
        if (this.size() == 0){
            throw new IllegalStateException();
        }else {
            return StreamSupport.stream(this.spliterator(), false);
        }
    }

    /**
     * Method that indicates the tasks that are between a certain time, also the tasks must be active
     *
     * @param from initial time of the tasks selection
     * @param to   final time of the tasks selection
     * @return return an array with the tasks that are between that time
     */
    public AbstractTaskList incoming(int from, int to) {
        /*
        if (from < 0) {
            //Exception in case the 'from' parameter is negative
            throw new IllegalAnnotationException("The from parameter needs a positive number");
        } else if (to < from) {
            //Exception in case 'to' is greater than 'from'
            throw new IllegalAnnotationException("The to parameter needs to be greater than the from parameter");
        }

        int k = 0;
        boolean exist;
        String currentTaskName;
        Task currentTask;
        int tasksLength = this.arrayTaskList.length;
        Task uniqueTasks[] = new Task[tasksLength];
        // Select the tasks that are active and are between the time interval
        AbstractTaskList newList = new ArrayTaskListImpl(); // New ArrayTaskList instance is created
        for (Task currentUniqueTask : this.arrayTaskList) {
            if (currentUniqueTask.isRepeated() && currentUniqueTask.isActive()) {
                int j = currentUniqueTask.getEndTime();
                int x = currentUniqueTask.getRepeatInterval();
                for (int i = currentUniqueTask.getStartTime();
                     i <= j; i = x + i) {
                    if (i > from && i < to) {
                        newList.add(currentUniqueTask);
                        break;
                    }
                }
            } else {
                if (currentUniqueTask.isActive()
                        && currentUniqueTask.getStartTime() > from
                        && currentUniqueTask.getStartTime() < to) {
                    newList.add(currentUniqueTask);
                }
            }
        }
        */

        /////////////////////////////////////////////////////////////////////////////
        // With streams
        AbstractTaskList newList = new ArrayTaskListImpl();
        if (this.size() == 0){
            throw new IllegalStateException();
        } else {
            // Stream for non-repetitive tasks
            this.getStream()
                    .filter(task -> task.getStartTime() > from
                            && task.getStartTime() < to
                            && !task.isRepeated()
                            && task.isActive())
                    .forEach(newList::add);
            // Stream for repetitive tasks
            this.getStream()
                    .filter(task -> task.isRepeated()
                            && task.isActive()
                            && task.nextTimeAfter(from) < to)
                    .forEach(newList::add);
        }
        return newList;
    }

    /**
     * This method allows the program iterate over all the elements in the array or list
     *
     * @return return the iterator object which allows to iterate the list
     */
    @Override
    public Iterator<Task> iterator() {
        Task[] copyList = this.arrayTaskList;
        Iterator<Task> iteratorReturned = new Iterator<Task>() {
            int index = 0; //This parameter indicates in which position is going to start the iteration

            @Override
            public boolean hasNext() {
                if (copyList.length >= index + 1) {
                    return true;
                }
                return false;
            }

            @Override
            public Task next() {
                Task task = copyList[index];
                index++; // this statement indicate ho is progressing the iteration
                return task;
            }
        };
        return iteratorReturned;
    }

    /**
     * his method indicates if a array is equal to another one
     * @param o is the object that is going to be compared
     * @return a boolean that indicates if the object is equal to the current list
     */
    public boolean equals (Object o){
        ArrayTaskList array = (ArrayTaskList) o;
        boolean state = false;
        if (this.size() != array.size()){
            return false;
        } else {
            for (int i = 0; i < array.size(); i++) {
                if (array.getTask(i).equals(this.getTask(i))){
                    state = true;
                } else {
                    state = false;
                    break;
                }
            }
        }
        return state;
    }

    /**
     * This method return the unique value, known as hash code
     * @return the list's hash code
     */
    public int hashCode(){
        int hashCode = 1;
        for (Task task: this){
            hashCode = 31*hashCode + (task == null ? 0: task.hashCode());
        }
        return hashCode;
    }

    /**
     * This method clones an array
     * @return the array list cloned
     */
    /*
    @Override
    public ArrayTaskList clone() {
        try {
            return (ArrayTaskList) super.clone();
        } catch (CloneNotSupportedException e){
            return null;
        }
    }
     */


    /**
     * This method shows the info inside the array
     * @return the string that shows the Task information
     */
    public String toString() {
        String message = "This array has the tasks:\n";
        for (Task task : this.arrayTaskList) {
            message = message.concat(task.toString());
        }
        return message;
    }
}

