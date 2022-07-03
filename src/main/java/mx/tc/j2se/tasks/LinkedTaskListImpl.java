package mx.tc.j2se.tasks;

import com.sun.xml.internal.txw2.IllegalAnnotationException;

/**
 * This Class makes the same as the ArrayTaskListImpl class, but this one uses linked-list as main tool
 * @author Benjamin Sanchez Martinez
 */

public class LinkedTaskListImpl extends AbstractTaskList{
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
    private Node tail;
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
            this.tail = this.list;
        } else{
            Node node = new Node(task);
            this.tail.nextNode = node;
            this.tail = node;
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
        int index = 0;
        int count = 1;
        boolean exist = false;
        Node values = this.list;
        while (values != null){
            index++;
            if (values.task == task){
                exist = true;
                break;
            }
            values = values.nextNode;
        }
        if (index == 1){
            Node current = this.list;
            this.list = this.list.nextNode;
            current.nextNode = null;
        }else {
            Node current = this.list;
            Node prev = null;
            while (count < index){
                count++;
                prev = current;
                current = current.nextNode;
            }
            prev.nextNode = current.nextNode;
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
    public AbstractTaskList incoming(int from, int to) {
        // Exceptions
        if (from<0){
            //Exception in case the 'from' parameter is negative
            throw new IllegalAnnotationException("The from parameter needs a positive number");
        } else if (to<from) {
            //Exception in case 'to' is greater than 'from'
            throw new IllegalAnnotationException("The to parameter needs to be greater than the from parameter");
        }
        // Method
        AbstractTaskList copyList = new LinkedTaskListImpl();
        AbstractTaskList theList = new LinkedTaskListImpl();
        Task currentTask;
        int size;
        // Copy the list into a new one
        Node cycle = this.list;
        while (cycle != null){
            copyList.add(cycle.task);
            cycle = cycle.nextNode;
        }
        size = copyList.size();
        //Select the task that are active and are in the interval
        if (size==0){
            return theList;
        }
        for (int h = 0; h<size; h++) {
            currentTask = copyList.getTask(h);
            if (currentTask.isRepeated() && currentTask.isActive()){
                int j = currentTask.getEndTime();
                int k = currentTask.getRepeatInterval();
                for (int i = currentTask.getStartTime();
                     i <= j; i = k + i){
                    if (i>from && i<to){
                        theList.add(currentTask);
                        break;
                    }
                }
            }else {
                if (currentTask.isActive()
                        && currentTask.getStartTime() > from
                        && currentTask.getStartTime() < to){
                    theList.add(currentTask);
                }
            }
        }
        return theList;
    }
}
