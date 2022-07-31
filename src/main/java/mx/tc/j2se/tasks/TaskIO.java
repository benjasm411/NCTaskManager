package mx.tc.j2se.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * This class allow us to work with serialized data, that includes read JSON files with the Gson dependency
 * @author Benjamin Sanchez Martinez
 */
public class TaskIO {
//public class TaskIOImpl implements TaskIO{

    /**
     * empty constructor for testing purposes
    */
    //public TaskIOImpl(){}

    /**
     * This method takes a collection of task and write the data in a file using a binary format
     * @param tasks the collection of task
     * @param out the stream where the data will be stored
     * @throws IOException
     */
    public static void writeBinary(AbstractTaskList tasks, OutputStream out) throws IOException {
    //public void writeBinary(ArrayTaskList tasks, OutputStream out) {
        try {
            DataOutputStream outputStream = new DataOutputStream(out);
            outputStream.writeInt(tasks.size());
            for (Iterator<Task> it = tasks.iterator(); it.hasNext(); ) {
                Task task = it.next();

                outputStream.writeInt(task.getTitle().length());
                outputStream.writeUTF(task.getTitle());
                outputStream.writeBoolean(task.isActive());
                outputStream.writeLong(task.getRepeatInterval());
                outputStream.writeBoolean(task.isRepeated());
                if (task.isRepeated()){
                    outputStream.writeInt(task.getStartTime().getYear());
                    outputStream.writeInt(task.getStartTime().getMonthValue());
                    outputStream.writeInt(task.getStartTime().getDayOfMonth());
                    outputStream.writeInt(task.getStartTime().getHour());
                    outputStream.writeInt(task.getStartTime().getMinute());

                    outputStream.writeInt(task.getEndTime().getYear());
                    outputStream.writeInt(task.getEndTime().getMonthValue());
                    outputStream.writeInt(task.getEndTime().getDayOfMonth());
                    outputStream.writeInt(task.getEndTime().getHour());
                    outputStream.writeInt(task.getEndTime().getMinute());
                } else {
                    outputStream.writeInt(task.getTime().getYear());
                    outputStream.writeInt(task.getTime().getMonthValue());
                    outputStream.writeInt(task.getTime().getDayOfMonth());
                    outputStream.writeInt(task.getTime().getHour());
                    outputStream.writeInt(task.getTime().getMinute());
                }
            }
            outputStream.close();
        } catch(Exception e){
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Reads a binary collection, and store the data in the tasks list
     * @param tasks is the collection where the data is located
     * @param in is the stream where the method reads the information
     * @throws IOException
     */
    public static void readBinary(AbstractTaskList tasks, InputStream in) throws IOException {
    //public void readBinary(ArrayTaskList tasks, InputStream in){
        try {
            DataInputStream inputStream = new DataInputStream(in);
            int size = inputStream.readInt();
            for (int i = 0; i<size; i++) {
                Task task = new TaskImpl();
                //System.out.println(i);
                int nameSize = inputStream.readInt();
                String name = inputStream.readUTF();
                task.setTitle(name);

                boolean active = inputStream.readBoolean();
                task.setActive(active);

                Long interval = inputStream.readLong();

                boolean repetitive = inputStream.readBoolean();

                if (repetitive){
                    int startYear = inputStream.readInt();
                    int startMonth = inputStream.readInt();
                    int startDay = inputStream.readInt();
                    int startHour = inputStream.readInt();
                    int startMin = inputStream.readInt();

                    int endYear = inputStream.readInt();
                    int endMonth = inputStream.readInt();
                    int endDay = inputStream.readInt();
                    int endHour = inputStream.readInt();
                    int endMin = inputStream.readInt();

                    LocalDateTime start = LocalDateTime.of(
                            startYear,
                            startMonth,
                            startDay,
                            startHour,
                            startMin);
                    LocalDateTime end = LocalDateTime.of(
                            endYear,
                            endMonth,
                            endDay,
                            endHour,
                            endMin);
                    task.setTime(start, end, interval);
                } else {
                    int timeYear = inputStream.readInt();
                    int timeMonth = inputStream.readInt();
                    int timeDay = inputStream.readInt();
                    int timeHour = inputStream.readInt();
                    int timeMin = inputStream.readInt();

                    LocalDateTime time = LocalDateTime.of(
                            timeYear,
                            timeMonth,
                            timeDay,
                            timeHour,
                            timeMin);
                    task.setTime(time);
                }
                tasks.add(task);
                //System.out.println(task);
            }
            inputStream.close();

        }catch (Exception e){
            try {
                throw new IOException(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * this method writes a stream using the previous method that writes a binary file
     * @param tasks is the task that will be stored
     * @param file is the file where the info will be located
     * @throws IOException
     */
    public static void write(AbstractTaskList tasks, File file) throws IOException {
    //public void write(ArrayTaskList tasks, File file){
        try {
            FileOutputStream outputStreamFile = new FileOutputStream(file);
            OutputStream outputStream = new BufferedOutputStream(outputStreamFile);
            writeBinary(tasks, outputStream);
            outputStream.close();
            outputStreamFile.close();
        }catch (IOException e2){
                try {
                    throw new IOException(e2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    /**
     * this method reads a binary file that contains a stream of data using the previous method
     * @param tasks is the collection of data that will contain the info in the file
     * @param file is the fila that will be read
     * @throws IOException
     */
    public static void read(AbstractTaskList tasks, File file) throws IOException {
    //public void read(ArrayTaskList tasks, File file){
        try {
            FileInputStream inputStreamFile = new FileInputStream(file);
            InputStream inputStream = new BufferedInputStream(inputStreamFile);
            readBinary(tasks, inputStream);
            inputStream.close();
            inputStreamFile.close();
        } catch (IOException e2){
            try {
                throw new IOException(e2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Writes a list of tasks in a JSON format using the Gson dependency
     * @param tasks is the collection of task
     * @param out is file where the JSON will be written
     * @throws IOException
     */
    public static void write(AbstractTaskList tasks, Writer out) throws IOException{
    //public void write(ArrayTaskList tasks, Writer out){
        Type type;
        if (LinkedTaskList.class.equals(tasks.getClass())){
            type = new TypeToken<LinkedTaskList>(){}.getType();
        } else {
            type = new TypeToken<ArrayTaskList>(){}.getType();
        }
        Gson gson = new Gson();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(out);
            bufferedWriter.write(gson.toJson(tasks,type));
            bufferedWriter.close();
        } catch (IOException e2){
            try {
                throw new IOException(e2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e){
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Reads a list of tasks in a JSON format using the Gson dependency
     * @param tasks is the collection of task that came as result
     * @param in is the Json file that will be read
     * @throws IOException
     */
    public static void read (AbstractTaskList tasks, Reader in) throws IOException {
    //public void read (ArrayTaskList tasks, Reader in){
        Type type;
        if (LinkedTaskList.class.equals(tasks.getClass())){
            type = new TypeToken<LinkedTaskList>(){}.getType();
        } else {
            type = new TypeToken<ArrayTaskList>(){}.getType();
        }
        Gson gson = new Gson();
        try{
            BufferedReader bufferedReader = new BufferedReader(in);
            String input = bufferedReader.readLine();
            AbstractTaskList collection = gson.fromJson(input, type);
            bufferedReader.close();
        } catch (IOException e2){
            try {
                throw new IOException(e2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e){
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * use the past file that writes a Json for writing a JSON in a file
     * @param tasks is the collection of tasks
     * @param file is the file where the JSON will be stored
     * @throws IOException
     */
    public static void writeText (AbstractTaskList tasks, File file) throws IOException {
    //public void writeText (ArrayTaskList tasks, File file){
        try {
            FileWriter newFile = new FileWriter(file);
            write(tasks, newFile);
            newFile.close();
        } catch (Exception e){
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * use the past file that reads a Json for reading a JSON from a file
     * @param tasks is the collection of tasks that come as result
     * @param file is the file where the JSON will be read
     * @throws IOException
     */
    public static void readText (AbstractTaskList tasks, File file) throws IOException {
    //public void readText (ArrayTaskList tasks, File file){
        try{
            FileReader newFile = new FileReader(file);
            read(tasks, newFile);
            newFile.close();
        } catch (Exception e){
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
