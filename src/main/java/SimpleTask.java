public class SimpleTask extends Task {
    private String title;

    public SimpleTask(int id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean matches(String query) {
        if (query == null) {
            return false;
        }
        if (query.isEmpty()) {
            return true;  // пустая строка всегда совпадает
        }
        if (this.title == null) {
            return false;
        }
        return this.title.toLowerCase().contains(query.toLowerCase());
    }
}
