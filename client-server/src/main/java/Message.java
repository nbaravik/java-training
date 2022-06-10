import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";

    private final int userId;
    private LocalDateTime dateTime;
    private String message;

    public Message(int id, LocalDateTime date, String message) {
        this.userId = id;
        this.dateTime = date;
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() { return dateTime; }

    public String getMessage() {
        return message;
    }
}
