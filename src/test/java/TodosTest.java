import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TodosTest {

    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void addShouldIncreaseSize() {
        Todos todos = new Todos();
        SimpleTask task1 = new SimpleTask(1, "Задача 1");
        todos.add(task1);
        Assertions.assertEquals(1, todos.findAll().length);
        SimpleTask task2 = new SimpleTask(2, "Задача 2");
        todos.add(task2);
        Assertions.assertEquals(2, todos.findAll().length);
    }

    @Test
    public void addNullTaskShouldNotThrow() {
        Todos todos = new Todos();
        try {
            todos.add(null);
        } catch (Exception e) {
            Assertions.fail("Adding null task should not throw exception");
        }
    }

    @Test
    public void searchShouldFindMultipleTasks() {
        Todos todos = new Todos();
        SimpleTask task1 = new SimpleTask(1, "Купить молоко");
        Epic task2 = new Epic(2, new String[]{"молоко", "Яйца"});
        todos.add(task1);
        todos.add(task2);

        Task[] expected = {task1, task2};
        Task[] actual = todos.search("молоко");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void searchShouldReturnMultipleMatches() {
        Todos todos = new Todos();
        SimpleTask t1 = new SimpleTask(1, "Задача по проекту");
        Meeting t2 = new Meeting(2, "Встреча", "Проект", "Время");
        Epic t3 = new Epic(3, new String[]{"Подзадача по проекту"});
        todos.add(t1);
        todos.add(t2);
        todos.add(t3);

        Task[] result = todos.search("Проект");
        Assertions.assertArrayEquals(new Task[]{t1, t2, t3}, result);
    }

    @Test
    public void searchShouldFindNothing() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Погулять с собакой"));

        Task[] actual = todos.search("кот");
        Assertions.assertEquals(0, actual.length);
    }

    @Test
    public void searchShouldFindMeetingByProject() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(3, "Обсуждение релиза", "Проект Y", "Завтра");
        todos.add(meeting);

        Assertions.assertArrayEquals(new Task[]{meeting}, todos.search("Проект Y"));
    }

    @Test
    public void searchShouldFindMeetingByTopic() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(4, "Выкатка новой версии", "Проект X", "Послезавтра");
        todos.add(meeting);

        Assertions.assertArrayEquals(new Task[]{meeting}, todos.search("версии"));
    }

    @Test
    public void searchShouldReturnEmptyWhenQueryMatchesNothing() {
        Todos todos = new Todos();
        todos.add(new Meeting(7, "Совещание", "Проект X", "В пятницу"));
        Task[] actual = todos.search("Несуществующая строка");
        Assertions.assertEquals(0, actual.length);
    }

    @Test
    public void searchOnEmptyTodosReturnsEmpty() {
        Todos todos = new Todos();
        Task[] result = todos.search("что-то");
        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void findAllOnEmptyTodosShouldReturnEmptyArray() {
        Todos todos = new Todos();
        Task[] result = todos.findAll();
        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void todosSearchWithEmptyQueryReturnsAll() {
        Todos todos = new Todos();
        SimpleTask simpleTask = new SimpleTask(1, "Задача");
        Epic epic = new Epic(2, new String[]{"Подзадача"});
        Meeting meeting = new Meeting(3, "Тема", "Проект", "Время");
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] allTasks = todos.findAll();
        Task[] searchResult = todos.search("");

        Assertions.assertArrayEquals(allTasks, searchResult);
    }

    @Test
    public void todosSearchWithNullQueryReturnsEmpty() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Задача"));

        Task[] searchResult = todos.search(null);

        Assertions.assertEquals(0, searchResult.length);
    }
    @Test
    public void todosAddNonNullTaskIncreasesSize() {
        Todos todos = new Todos();
        Task task = new SimpleTask(1, "Задача");
        todos.add(task);
        Assertions.assertEquals(1, todos.findAll().length);
    }
}
