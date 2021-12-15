public class Task {

    private String name;
    private String date;
    private int time;

    public Task() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String toString() {

        return String.format("%-20s%-10s%-10s%n", this.name, this.date, this.time);
    }
}
