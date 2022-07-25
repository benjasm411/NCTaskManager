package mx.tc.j2se.tasks;

import java.util.Iterator;
import java.util.stream.Stream;

public interface LinkedTaskList{
    void add(Task task);

    boolean remove(Task task);

    int size();

    Task getTask(int index);

    Iterator<Task> iterator();
    boolean equals(Object o);

    int hashCode();

    LinkedTaskList clone();
    Stream<Task> getStream();
}

