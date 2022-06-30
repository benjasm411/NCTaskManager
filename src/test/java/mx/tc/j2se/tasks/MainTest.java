package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
	@Test
	public void mainTest() {
		Task task1 = new TaskImpl("Lunch", 5);
		Task task2 = new TaskImpl("Morning run", 4, 10, 2);
		Task task3 = new TaskImpl("Meditation", 2, 12, 1);
		Task task4 = new TaskImpl("Meeting", 7);

		task1.setActive(true);
		task2.setActive(true);
		task3.setActive(true);
		task4.setActive(true);

		ArrayTaskList list = new ArrayTaskListImpl();
		list.add(task1);
		list.add(task2);
		list.add(task3);
		list.add(task4);

		list.incoming(8, 12);
	}

	//Exception tests
	@Test
	public void Tests(){
		Task task = new TaskImpl("asd", 1, 1,5);
	}

	//Linked Lists Tests
	@Test
	public void linkedListTests(){
		Task task1 = new TaskImpl("Lunch", 5);
		Task task2 = new TaskImpl("Morning run", 4, 10, 2);
		Task task3 = new TaskImpl("Meditation", 2, 12, 1);
		Task task4 = new TaskImpl("Meeting", 7);
		Task task5 = new TaskImpl();

		task1.setActive(true);
		task2.setActive(true);
		task3.setActive(true);
		task4.setActive(true);

		LinkedTaskList linkedList = new LinkedTaskListImpl();
		linkedList.add(task1);
		linkedList.add(task2);
		linkedList.add(task3);
		linkedList.add(task4);

		LinkedTaskList list2 = linkedList.incoming(5, 7);

	}

}
