public class Epic extends Task {
    private String[] subtasks;

    public Epic(int id, String[] subtasks) {
        super(id);
        this.subtasks = subtasks;
    }

    public String[] getSubtasks() {
        return subtasks;
    }

    @Override
    public boolean matches(String query) {
        if (query == null) {
            return false;
        }
        if (query.isEmpty()) {
            return true;  // пустая строка всегда совпадает
        }
        if (subtasks == null || subtasks.length == 0) {
            return false;
        }
        String lowerQuery = query.toLowerCase();
        for (String subtask : subtasks) {
            if (subtask != null && subtask.toLowerCase().contains(lowerQuery)) {
                return true;
            }
        }
        return false;
    }
}
