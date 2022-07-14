package mx.tc.j2se.tasks;

public interface Task extends Cloneable{
    String getTitle();

    void setTitle(String var1);

    boolean isActive();

    void setActive(boolean var1);

    int getTime();

    void setTime(int var1);

    int getStartTime();

    int getEndTime();

    int getRepeatInterval();

    void setTime(int var1, int var2, int var3);

    boolean isRepeated();

    int nextTimeAfter(int var1);
    boolean equals(Object o);
    int hashCode();
    String toString();

    Task clone() throws CloneNotSupportedException;
}
