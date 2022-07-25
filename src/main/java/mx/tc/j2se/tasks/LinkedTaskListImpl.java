package mx.tc.j2se.tasks;

//import com.sun.xml.internal.txw2.IllegalAnnotationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This Class makes the same as the ArrayTaskListImpl class, but this one uses linked-list as main tool
 * @author Benjamin Sanchez Martinez
 */

//public class LinkedTaskListImpl implements  LinkedTaskList, Cloneable, Iterable <Task>{//extends AbstractTaskList{
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
        /*
        // Exceptions
        if (task == null){
            throw new NullPointerException("The task can't be null");
        }
         */
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
        /*
        // Exceptions
        if (task == null){
            throw new NullPointerException("The task can't be null");
        }
         */
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
        /*
        // Exceptions
        if (index<0){
            // Exception for negative index
            throw new ArrayIndexOutOfBoundsException("The index needs to be positive or zero");
        }
         */
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
     * This method allows the program iterate over all the elements in the array or list
     * @return return the iterator object which allows to iterate the list
     */
    public Iterator<Task> iterator() {
        return new iterator();
    }

    public class iterator implements Iterator <Task>{
        Node index;
        public iterator(){
            index = list;
        }
        @Override
        public boolean hasNext() {
            boolean state = index != null;
            return state;
        }

        @Override
        public Task next() {
            if (!hasNext()){
                throw  new NoSuchElementException();
            } else {
                Task task = index.task;
                index = index.nextNode;
                return task;
            }
        }
    }

    /**
     * This method indicates if a linked-list is equal to another one
     * @param o is the object that is going to be compared
     * @return a boolean that indicates if the object is equal to the current list
     */
    public boolean equals(Object o){
        LinkedTaskList list = (LinkedTaskList) o;
        boolean state = false;
        int index = 0;
        if (this.size() != (list.size())){
            return false;
        } else {
            Node currentList = this.list;
            while (currentList != null){
                if (currentList.task.equals(list.getTask(index))){
                    state = true;
                } else {
                    state = false;
                    break;
                }
                index++;
                currentList = currentList.nextNode;
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
        for (Task task: this) {
            hashCode = 31*hashCode + (task == null ? 0: task.hashCode());
        }
        //System.out.println("The hashCode is " + hashCode);
        return hashCode;
    }

    /**
     * This method creates a stream that allows to work with collections, iterating each of them
     * @return an Steream element
     */
    @Override
    public Stream<Task> getStream() {
        if (this.size() == 0){
            throw new IllegalStateException();
        } else {
            return StreamSupport.stream(this.spliterator(), false);
        }
    }

    /**
     * This method clones a linked list
     * @return the linked list cloned
     */
    /*
    @Override
    public LinkedTaskList clone() {
        try {
            return (LinkedTaskList) super.clone();
        } catch (CloneNotSupportedException e){
            return null;
        }
    }
     */

    /**
     * This method shows the info inside the list
     * @return the string that shows the Task information
     */
    public String toString() {
        String message = "This list has the tasks:\n";
        Node current = this.list;
        while (current != null){
            message = message.concat(current.task.toString());
            current = current.nextNode;
        }
        return message;
    }
}
