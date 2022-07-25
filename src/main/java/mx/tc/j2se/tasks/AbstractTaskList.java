package mx.tc.j2se.tasks;

import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {

    public abstract void add(Task task);
    public abstract boolean remove (Task task);
    public abstract int size();
    public abstract Task getTask(int index);
    public abstract Iterator<Task> iterator();

    public abstract boolean equals (Object o);
    public abstract int hashCode();
    public abstract Stream<Task> getStream();

    @Override
    public AbstractTaskList clone() {
        try {
            return (AbstractTaskList) super.clone();
        } catch (CloneNotSupportedException e){
            return null;
        }
    }

}
