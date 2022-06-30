package mx.tc.j2se.tasks;

import com.sun.xml.internal.txw2.IllegalAnnotationException;

/**
 * This Class makes the same as the ArrayTaskListImpl class, but this one uses linked-list as main tool
 * @author Benjamin Sanchez Martinez
 */

public class LinkedTaskListImpl implements LinkedTaskList{
    /**
     * This class is used for creating a node that will be part of the list
     */
    private static class Node{
        Task task;
        Node nextNode;

        /**
         * This constructor set the value in the list node
         * @param task is the task that will work with the list
         */
        public Node (Task task){
            this.task = task;
            this.nextNode = null;
        }
    }

    private Node list;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This empty constructor function as class initializer, it doesn't change or add any data into the program
     */
    public LinkedTaskListImpl(){}

    /**
     * This method add a task into the list
     * @param task is the task that will be added
     */
    public void add (Task task){
        // Exceptions
        if (task == null){
            throw new NullPointerException("The task can't be null");
        }
        // Method
        Node taskAdded = new Node(task);
        if (this.list == null){
            this.list = taskAdded;
        } else{
            taskAdded.nextNode = null;
            Node previousList = this.list;
            while (previousList.nextNode != null){
                previousList = previousList.nextNode;
            }
            previousList.nextNode = taskAdded;
        }
    }

    /**
     * This method remove a certain task
     * @param task it's the task that will be deleted
     * @return a boolean true if the task exist in the list, or false if it doesn't
     */
    public boolean remove (Task task){
        // Exceptions
        if (task == null){
            throw new NullPointerException("The task can't be null");
        }
        // Method
        int count = 0;
        int taskIndex = count;
        boolean countBool = false;
        boolean exist = false;
        Node copyList = this.list;
        Node previous = copyList;
        Node current = this.list;
        while (current != null){
            count++;
            Node next = current.nextNode;
            if (current.task == task && !countBool) {
                taskIndex = count;
                exist = true;
                countBool = true;
                previous.nextNode = next;
            } else {
                previous = current;
            }
            current = next;
        }
        if (taskIndex == 1) {
            this.list = copyList.nextNode;
        } else {
            this.list = copyList;
        }
        return exist;
    }

    /**
     * This method count how many elements the list has
     * @return the number of elements in the list
     */
    public int size (){
        int count = 0;
        Node copyList = this.list;
        if (copyList == null) {
            count = 0;
        } else {
            while (copyList != null){
                count++;
                copyList = copyList.nextNode;
            }
        }
        return count;
    }

    /**
     * This method return a task in a certain position
     * @param index is the position of the task that the user is looking for
     * @return the task that points to the index
     */
    public Task getTask (int index){
        // Exceptions
        if (index<0){
            // Exception for negative index
            throw new ArrayIndexOutOfBoundsException("The index needs to be positive or zero");
        }
        // Method
        int count = 0;
        Task task = null;
        Node copyList = this.list;
        while (copyList != null){
            if (count == index){
                task = copyList.task;
            }
            count++;
            copyList =  copyList.nextNode;
        }
        return task;
    }

    /**
     * This method return a linkedlist with the tasks that will happen between a certain time
     * @param from is the time when the interval starts
     * @param to is the time when the interval ends
     * @return the linked list of tasks that are between 'from' and 'to'
     */
    public LinkedTaskList incoming (int from, int to){
        // Exceptions
        if (from<0){
            //Exception in case the 'from' parameter is negative
            throw new IllegalAnnotationException("The from parameter needs a positive number");
        } else if (to<from) {
            //Exception in case 'to' is greater than 'from'
            throw new IllegalAnnotationException("The to parameter needs to be greater than the from parameter");
        }
        // Method
        /*
        Node copyList = this.list;
        LinkedTaskList closerTasks = new LinkedTaskListImpl();
        while (copyList != null){
            if (copyList.task.isRepeated()){// && this.list.task.isActive()){
                int j = copyList.task.getEndTime();
                int k = copyList.task.getRepeatInterval();
                for (int i = copyList.task.getStartTime(); i <= j; i = k+i){
                    if (i>from && i<to){
                        System.out.println(copyList.task.getTitle());
                        closerTasks.add(copyList.task);
                        break;
                    }
                }
            } else if (copyList.task.isActive()
                    && copyList.task.getStartTime() > from
                    && copyList.task.getStartTime() < to) {
                System.out.println(copyList.task.getTitle());
                closerTasks.add(copyList.task);
            }
            copyList =  copyList.nextNode;
        }
        return closerTasks;
         */

        LinkedTaskList copyList = new LinkedTaskListImpl();
        LinkedTaskList closerTasks = new LinkedTaskListImpl();
        Node cycle = this.list;
        while (cycle != null){
            copyList.add(cycle.task);
            cycle = cycle.nextNode;
        }
        for (int i = 0; i<copyList.size(); i++){
            Task currentTask = copyList.getTask(i);
            if (currentTask.isRepeated() && currentTask.isActive()){
                int j = currentTask.getEndTime();
                int k = currentTask.getRepeatInterval();
                for (int h = currentTask.getStartTime(); h<= j; h= h+k){
                    if (h>from && h<to){
                        closerTasks.add(currentTask);
                        break;
                    }
                }
            } else if (currentTask.isActive()
                        && currentTask.getStartTime() > from
                        && currentTask.getStartTime() < to){
                closerTasks.add(currentTask);
            }
        }
        return closerTasks;
    }
}
