import java.time.Duration;

public interface DurationParser {

    String WEEK_UNIT = "w";
    String DAY_UNIT = "d";
    String HOUR_UNIT = "h";
    String MINUTE_UNIT = "m";
    String SECOND_UNIT = "s";
    String MILLISECOND_UNIT = "ms";

    Integer MAX_LENGTH_OF_UNIT = 2;

    String[] unitOrder = {WEEK_UNIT, DAY_UNIT, HOUR_UNIT, MINUTE_UNIT, SECOND_UNIT, MILLISECOND_UNIT};

    String[] convertToSeconds = {WEEK_UNIT, DAY_UNIT, HOUR_UNIT, MINUTE_UNIT, SECOND_UNIT};

    String[] convertToNanoseconds = {MILLISECOND_UNIT};

    Duration parse(String durationStr);
}
