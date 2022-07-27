package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.time.LocalDateTime;

public class MainTest {

	@Test
	public void mainTest() throws CloneNotSupportedException {

		Task task1 = new TaskImpl("Lunch",
				LocalDateTime.of(2021,8,24,16,00, 00, 550000000));
		Task task2 = new TaskImpl("Morning run",
				LocalDateTime.of(2022,3,1,8,15, 55, 150000000),
				LocalDateTime.of(2022,9,1,8,15, 55, 150000000),
				1);
		Task task3 = new TaskImpl("Meditation",
				LocalDateTime.of(2022,8,20,8,15, 22, 890000000),
				LocalDateTime.of(2022,8,28,8,15, 50, 890000000),
				12);
		Task task4 = new TaskImpl("Meeting",
				LocalDateTime.of(2022,9,1,18,00, 20, 460000000));


		task1.setActive(true);
		task2.setActive(true);
		task3.setActive(true);
		task4.setActive(true);

		// Test  for the first task, local date time and next time after function
		/*
		LocalDateTime nowTime = LocalDateTime.now();
		LocalDateTime dummyTime = LocalDateTime.of(2022,6,20,11,25);
		LocalDateTime dummyStartTime = LocalDateTime.of(2022,7,4,12,15);
		LocalDateTime dummyEndTime = LocalDateTime.of(2022,8,6,14,20);
		long dummyInterval = 1;

		task1.setTime(dummyStartTime, dummyEndTime, dummyInterval);
		task2.setTime(dummyTime);

		System.out.println(task1.toString());
		System.out.println(task2.toString());
		System.out.println(task3.toString());
		System.out.println(task4.toString());

		System.out.println("///////////////////////////////////////////////////////////// \n");

		LocalDateTime dummyNextTime = LocalDateTime.of(2022,8,29,9,12);

		System.out.println(task3.nextTimeAfter(dummyNextTime));
		 */

		// Test for the second part of the practice 7
		ArrayTaskListImpl list = new ArrayTaskListImpl();

		//list.add(task1);
		list.add(task2); // <-- First I set a repetitive task with different interval
		//list.add(task3);
		//list.add(task4);


		Tasks newList = new Tasks();

		// The range of time when the calendar will consider the tasks
		LocalDateTime from = LocalDateTime.of(2022,8,25,8,00,00);
		LocalDateTime to = LocalDateTime.of(2022,8,26,8,00,00);


		SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();
		calendar = newList.calendar(list.iterator(), from, to);

		int index = 0; // <-- a counter that says how many items are in the calendar

		// a for loop that will show when the calendar registered the task (the key of the map)
		for (Map.Entry task: calendar.entrySet()){
			index++;
			LocalDateTime date = (LocalDateTime) task.getKey();
			Set<Task> value = (Set<Task>) task.getValue();
			System.out.println("date: " + date + "// value: " + value);
		}
		System.out.println("\n" + index);
	}
}
