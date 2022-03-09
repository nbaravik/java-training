public class Task {

    public static final String FORMAT_2_COLUMNS = "%-6s%-10s\n";

    private int id;
    private String name;
    private boolean done;

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
        this.done = false;
    }

    public void setID(int id) { this.id = id; }

    public int getID() { return id; }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setDone(boolean done) { this.done = done; };

    public boolean getDone() { return done; };

    public String toString() {

        return toString(FORMAT_2_COLUMNS);
    }

    private String toString(String format) {

        return String.format(format, this.id, this.name);
    }
}