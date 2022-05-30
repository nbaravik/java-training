import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    transient public static final String EXIT = "#exit#";

    private final int user_id;
    private LocalDateTime date;
    private String message;

    public Message(int id, LocalDateTime date, String message) {
        this.user_id = id;
        this.date = date;
        this.message = message;
    }

    public int detId() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Message from the user #");
        sb.append(user_id);
        sb.append(", sent at ");
        sb.append(date.format(DateTimeFormatter.ISO_DATE_TIME));
        sb.append(": \n");
        sb.append(message);
        return sb.toString();
    }
}
