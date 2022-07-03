package mx.tc.j2se.tasks;

public class TaskListFactory {
    public static AbstractTaskList createTAskList (ListTypes.types type){
        if (type == ListTypes.types.ARRAY){
            AbstractTaskList newArrayList = new ArrayTaskListImpl();
            return newArrayList;
        } else if (type == ListTypes.types.LINKED) {
            AbstractTaskList newLinkedList = new LinkedTaskListImpl();
            return newLinkedList;
        } else {
            return null;
        }
    }
}
