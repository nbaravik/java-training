import java.time.Duration;

public interface DurationParser {

    String UNIT_WEEK = "w";
    String UNIT_DAY = "d";
    String UNIT_HOUR = "h";
    String UNIT_MINUTE = "m";
    String UNIT_SECOND = "s";
    String UNIT_MILLISECOND = "ms";

    String[] unitOrder = {UNIT_WEEK, UNIT_DAY, UNIT_HOUR, UNIT_MINUTE, UNIT_SECOND, UNIT_MILLISECOND};

    Duration parse(String durationStr);
}
