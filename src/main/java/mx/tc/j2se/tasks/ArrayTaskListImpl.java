package mx.tc.j2se.tasks;

/**
 * This class create a list of arrayTaskList that are implemented, the arrayTaskList can be active or inactive as well as repetitive or
 * non-repetitive, the list will only store arrayTaskList.
 * @author Benjamin Sanchez Martinez
 */
public class ArrayTaskListImpl implements ArrayTaskList {
    private Task arrayTaskList[] = new Task[0];

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
        int tasksArrayLength = this.arrayTaskList.length;
        Task copyTasks[] = new Task[tasksArrayLength + 1];
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
        boolean exist = false;
        int tasksArrayLength = this.arrayTaskList.length;
        Task copyTasksSub[] = new Task[tasksArrayLength - 1];
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
        return this.arrayTaskList[index];
    }

    /**
     * Method that indicates the tasks that are between a certain time, also the tasks must be active
     *
     * @param from initial time of the tasks selection
     * @param to   final time of the tasks selection
     * @return return an array with the tasks that are between that time
     */
    public ArrayTaskList incoming(int from, int to) {
        /*
        int k = 0;
        boolean exist;
        String currentTaskName;
        Task currentTask;
        int tasksLength = this.arrayTaskList.length;
        Task uniqueTasks[] = new Task[tasksLength];
        // Subtracting the duplicated tasks
        for (int i = 0; i < tasksLength; i++) {
            currentTask = this.arrayTaskList[i];
            currentTaskName = this.arrayTaskList[i].getTitle();
            exist = false;
            for (int j = 0; j < k; j++) {
                if (uniqueTasks[j].getTitle() == currentTaskName) {
                    exist = true;
                }
            }
            if (!exist) {
                uniqueTasks[k] = currentTask;
                k++;
            }
        }
        Task finalTasks[] = new Task[k];
        for (int i = 0; i < k; i++) {
            finalTasks[i] = uniqueTasks[i];
        }*/
        // Select the tasks that are active and are between the time interval
        ArrayTaskList newList = new ArrayTaskListImpl(); // New ArrayTaskList instance is created
        //for (Task currentUniqueTask : finalTasks) {
        for (Task currentUniqueTask : this.arrayTaskList) {
            if (currentUniqueTask.isRepeated() && currentUniqueTask.isActive()){
                int j = currentUniqueTask.getEndTime();
                int k = currentUniqueTask.getRepeatInterval();
                for (int i = currentUniqueTask.getStartTime();
                     i <= j; i = k + i){
                    if (i>from && i<to){
                        newList.add(currentUniqueTask);
                        break;
                    }
                }
            }else {
                if (currentUniqueTask.isActive()
                        && currentUniqueTask.getStartTime() > from
                        && currentUniqueTask.getStartTime() < to){
                    newList.add(currentUniqueTask);
                }
            }
        }
        return newList;
    }
}

