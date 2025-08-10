import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TasksTest {

    // --- Base Task ---
    @Test
    public void baseTaskMatchesAlwaysFalse() {
        Task base = new Task(99);
        Assertions.assertFalse(base.matches("что-то"));
        Assertions.assertFalse(base.matches(null));
        Assertions.assertFalse(base.matches(""));
    }

    @Test
    public void taskMatchesAlwaysFalse() {
        Task task = new Task(1);
        Assertions.assertFalse(task.matches("любой запрос"));
        Assertions.assertFalse(task.matches(null));
        Assertions.assertFalse(task.matches(""));
    }

    // --- SimpleTask ---
    @Test
    public void simpleTaskMatchesPositive() {
        SimpleTask task = new SimpleTask(1, "Позвонить родителям");
        Assertions.assertTrue(task.matches("Позвонить"));
        Assertions.assertTrue(task.matches("родителям"));
        Assertions.assertTrue(task.matches(""));      // пустая строка — всегда true
    }

    @Test
    public void simpleTaskMatchesNegative() {
        SimpleTask task = new SimpleTask(1, "Позвонить родителям");
        Assertions.assertFalse(task.matches("Написать"));
        Assertions.assertFalse(task.matches(null));
    }

    @Test
    public void simpleTaskMatchesWithNullTitle() {
        SimpleTask task = new SimpleTask(1, null);
        Assertions.assertFalse(task.matches("что-то"));
        Assertions.assertTrue(task.matches("")); // если так логика
    }

    @Test
    public void simpleTaskMatchesCaseInsensitive() {
        SimpleTask task = new SimpleTask(1, "Позвонить родителям");
        Assertions.assertTrue(task.matches("позвонить"));
        Assertions.assertTrue(task.matches("РОДИТЕЛЯМ"));
    }

    @Test
    public void simpleTaskWithNullTitle() {
        SimpleTask task = new SimpleTask(1, null);
        Assertions.assertFalse(task.matches("запрос"));
        Assertions.assertTrue(task.matches("")); // пустая строка
    }
    @Test
    public void simpleTaskGetTitleReturnsCorrectValue() {
        String title = "Тестовое задание";
        SimpleTask task = new SimpleTask(1, title);
        Assertions.assertEquals(title, task.getTitle());
    }



    // --- Epic ---
    @Test
    public void epicMatchesPositive() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);
        Assertions.assertTrue(epic.matches("Молоко"));
        Assertions.assertTrue(epic.matches("Яйца"));
        Assertions.assertTrue(epic.matches(""));     // пустая строка — всегда true
    }

    @Test
    public void epicMatchesNegative() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);
        Assertions.assertFalse(epic.matches("Сыр"));
        Assertions.assertFalse(epic.matches(null));
    }

    @Test
    public void epicMatchesWithEmptySubtasks() {
        Epic epic = new Epic(2, new String[0]);
        Assertions.assertFalse(epic.matches("что-то"));
        Assertions.assertTrue(epic.matches(""));  // пустая строка — всегда true
        Assertions.assertFalse(epic.matches(null));
    }

    @Test
    public void epicMatchesWithNullSubtask() {
        String[] subtasks = {null, "Подзадача"};
        Epic epic = new Epic(3, subtasks);
        Assertions.assertTrue(epic.matches("Подзадача"));
        Assertions.assertFalse(epic.matches("Что-то"));
        Assertions.assertTrue(epic.matches(""));  // пустая строка — всегда true
        Assertions.assertFalse(epic.matches(null));
    }

    @Test
    public void epicMatchesWithNullInSubtasks() {
        String[] subtasks = {null, "Подзадача"};
        Epic epic = new Epic(3, subtasks);
        Assertions.assertTrue(epic.matches("Подзадача"));
        Assertions.assertFalse(epic.matches("что-то"));
    }

    @Test
    public void epicWithNullSubtasks() {
        Epic epic = new Epic(1, null);
        Assertions.assertFalse(epic.matches("запрос"));
        Assertions.assertTrue(epic.matches("")); // пустая строка
    }
    @Test
    public void epicGetSubtasksReturnsCorrectArray() {
        String[] subtasks = {"Подзадача 1", "Подзадача 2"};
        Epic epic = new Epic(1, subtasks);
        Assertions.assertArrayEquals(subtasks, epic.getSubtasks());
    }


    // --- Meeting ---
    @Test
    public void meetingMatchesByTopic() {
        Meeting meeting = new Meeting(3, "Выкатка версии", "Проект Z", "Завтра");
        Assertions.assertTrue(meeting.matches("Выкатка"));
        Assertions.assertTrue(meeting.matches("версии"));
        Assertions.assertTrue(meeting.matches(""));    // пустая строка — всегда true
    }

    @Test
    public void meetingMatchesByProject() {
        Meeting meeting = new Meeting(3, "Обсуждение релиза", "Проект Y", "Завтра");
        Assertions.assertTrue(meeting.matches("Проект"));
        Assertions.assertTrue(meeting.matches("Проект Y"));
        Assertions.assertTrue(meeting.matches(""));    // пустая строка — всегда true
    }

    @Test
    public void meetingMatchesNegative() {
        Meeting meeting = new Meeting(3, "Обсуждение релиза", "Проект Y", "Завтра");
        Assertions.assertFalse(meeting.matches("Обновление сайта"));
        Assertions.assertFalse(meeting.matches(null));
    }
    @Test
    public void meetingGettersReturnCorrectValues() {
        String topic = "Тема встречи";
        String project = "Проект X";
        String start = "Завтра";

        Meeting meeting = new Meeting(1, topic, project, start);

        Assertions.assertEquals(topic, meeting.getTopic());
        Assertions.assertEquals(project, meeting.getProject());
        Assertions.assertEquals(start, meeting.getStart());
    }


    // --- Meeting с null topic ---
    @Test
    public void meetingMatchesWithNullTopic() {
        Meeting meeting = new Meeting(4, null, "Проект X", "Время");
        Assertions.assertTrue(meeting.matches("Проект"));  // совпадает по project
        Assertions.assertFalse(meeting.matches("Выкатка")); // topic null, не совпадает
        Assertions.assertTrue(meeting.matches(""));         // пустая строка — всегда true
        Assertions.assertFalse(meeting.matches(null));
    }

    // --- Meeting с null project ---
    @Test
    public void meetingMatchesWithNullProject() {
        Meeting meeting = new Meeting(5, "Тема", null, "Время");
        Assertions.assertTrue(meeting.matches("Тема"));     // совпадает по topic
        Assertions.assertFalse(meeting.matches("Проект"));  // project null, не совпадает
        Assertions.assertTrue(meeting.matches(""));         // пустая строка — всегда true
        Assertions.assertFalse(meeting.matches(null));
    }

    // --- Meeting с null topic и project ---
    @Test
    public void meetingMatchesWithNullTopicAndProject() {
        Meeting meeting = new Meeting(6, null, null, "Время");
        Assertions.assertFalse(meeting.matches("что-то"));
        Assertions.assertTrue(meeting.matches(""));          // пустая строка — всегда true
        Assertions.assertFalse(meeting.matches(null));
    }

    @Test
    public void baseTaskMatchesAlwaysFalseForEmptyAndNull() {
        Task base = new Task(0);
        Assertions.assertFalse(base.matches(null));
        Assertions.assertFalse(base.matches(""));
        Assertions.assertFalse(base.matches("что-то"));
    }

    @Test
    public void simpleTaskWithNullTitleMatchesOnlyEmptyQuery() {
        SimpleTask task = new SimpleTask(10, null);
        Assertions.assertFalse(task.matches("любое"));
        Assertions.assertTrue(task.matches(""));
        Assertions.assertFalse(task.matches(null));
    }

    @Test
    public void epicWithNullSubtasksMatchesEmptyQueryOnly() {
        Epic epic = new Epic(20, null);
        Assertions.assertTrue(epic.matches(""));
        Assertions.assertFalse(epic.matches("что-то"));
        Assertions.assertFalse(epic.matches(null));
    }

    @Test
    public void meetingWithNullFieldsMatchesEmptyQueryOnly() {
        Meeting meeting = new Meeting(30, null, null, "Время");
        Assertions.assertTrue(meeting.matches(""));
        Assertions.assertFalse(meeting.matches("что-то"));
        Assertions.assertFalse(meeting.matches(null));
    }

    @Test
    public void todosFindAllOnEmptyListReturnsEmpty() {
        Todos todos = new Todos();
        Assertions.assertEquals(0, todos.findAll().length);
    }

    @Test
    public void todosSearchWithNullReturnsEmpty() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Задача"));
        Task[] result = todos.search(null);
        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void todosAddNullTaskDoesNotAdd() {
        Todos todos = new Todos();
        todos.add(null);
        Assertions.assertEquals(0, todos.findAll().length);
    }

    @Test
    public void meetingWithNullTopicAndProject() {
        Meeting meeting = new Meeting(1, null, null, "12:00");
        Assertions.assertFalse(meeting.matches("запрос"));
        Assertions.assertTrue(meeting.matches("")); // пустая строка
    }

    @Test
    public void testEqualsAndHashCode() {
        Task task1 = new Task(1);
        Task task2 = new Task(1);
        Task task3 = new Task(2);

        // equals()
        Assertions.assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны");
        Assertions.assertNotEquals(task1, task3, "Задачи с разным id не должны быть равны");
        Assertions.assertNotEquals(task1, null, "Задача не равна null");
        Assertions.assertNotEquals(task1, "Some String", "Задача не равна объекту другого класса");

        // hashCode()
        Assertions.assertEquals(task1.hashCode(), task2.hashCode(), "Задачи с одинаковым id должны иметь одинаковый hashCode");
        Assertions.assertNotEquals(task1.hashCode(), task3.hashCode(), "Задачи с разным id могут иметь разный hashCode");
    }

    @Test
    public void testGetId() {
        Task task = new Task(42);
        Assertions.assertEquals(42, task.getId(), "getId должен возвращать корректный id");
    }
    @Test
    public void testEqualsBranches() {
        Task task = new Task(1);

        // this == o (рефлексивное равенство)
        Assertions.assertTrue(task.equals(task));

        // o == null
        Assertions.assertFalse(task.equals(null));

        // getClass() != o.getClass()
        Assertions.assertFalse(task.equals("string"));

        // равенство по id (покрыто)
        Task sameIdTask = new Task(1);
        Assertions.assertTrue(task.equals(sameIdTask));

        Task differentIdTask = new Task(2);
        Assertions.assertFalse(task.equals(differentIdTask));
    }

}


