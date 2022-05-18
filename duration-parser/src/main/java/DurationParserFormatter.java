import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DurationParserFormatter implements DurationFormatter, DurationParser {

    private Map<String, Integer> parsedMap;
    private String primaryString;
    private Duration duration;

    public DurationParserFormatter() {
        parsedMap = new LinkedHashMap<>();
    }

    public static final Map<String, String> timeUnits = new HashMap<>();

    static {
        timeUnits.put(DurationParser.WEEK_UNIT, "week");
        timeUnits.put(DurationParser.DAY_UNIT, "day");
        timeUnits.put(DurationParser.HOUR_UNIT, "hour");
        timeUnits.put(DurationParser.MINUTE_UNIT, "minute");
        timeUnits.put(DurationParser.SECOND_UNIT, "second");
        timeUnits.put(DurationParser.MILLISECOND_UNIT, "millisecond");
    }

    public static final Map<String, Integer> formattingFactorsMap = new HashMap<>();

    static {
        formattingFactorsMap.put(DurationParser.WEEK_UNIT, 1);            // unit without format
        formattingFactorsMap.put(DurationParser.DAY_UNIT, 7);             // 1w = 7d
        formattingFactorsMap.put(DurationParser.HOUR_UNIT, 12);           // 1d = 12h
        formattingFactorsMap.put(DurationParser.MINUTE_UNIT, 60);         // 1h = 60m
        formattingFactorsMap.put(DurationParser.SECOND_UNIT, 60);         // 1m = 60s
        formattingFactorsMap.put(DurationParser.MILLISECOND_UNIT, 1000);  // 1s = 1000ms
    }

    public static final Map<String, Integer> durationFactorsMap = new HashMap<>();

    static {
        durationFactorsMap.put(DurationParser.WEEK_UNIT, 604_800);           // 1w in sec => x 604_800
        durationFactorsMap.put(DurationParser.DAY_UNIT, 86_400);             // 1d in sec => x 86_400
        durationFactorsMap.put(DurationParser.HOUR_UNIT, 3_600);             // 1h in sec => x 3_600
        durationFactorsMap.put(DurationParser.MINUTE_UNIT, 60);              // 1m in sec => x 60
        durationFactorsMap.put(DurationParser.SECOND_UNIT, 1);               // 1s in sec => x 1
        durationFactorsMap.put(DurationParser.MILLISECOND_UNIT, 1_000_000);  // 1ms in nanos => x 1_000_000
    }

    private boolean isDigit(char ch) {
        int digit = ch - '0';
        return (digit >= 0 && digit <= 9);
    }

    private void parseStringIntoMap(String durationStr) {

        StringBuilder number = new StringBuilder();
        StringBuilder unit = new StringBuilder(DurationParser.MAX_LENGTH_OF_UNIT);
        int i = 0;  // index for current element
        int j = 0;  // index for unit order

        while (i <= durationStr.length() - 1) {

            String expectedUnit = DurationParser.unitOrder[j];
            char currentSymbol = durationStr.charAt(i);

            while (isDigit(currentSymbol) && i < durationStr.length() - 1) {
                number.append(currentSymbol);
                currentSymbol = durationStr.charAt(++i);
            }
            if (i == durationStr.length() - 1 && isDigit(currentSymbol)) {
                throw new IllegalArgumentException(durationStr + " - invalid date and time format");
            }

            unit.append(currentSymbol);
            // if there is a next char, and it is not a digit (ex: unit "ms")
            if ((i <= durationStr.length() - 2) && !isDigit(durationStr.charAt(i + 1))) {
                unit.append(durationStr.charAt(++i));
            }
            String currentUnit = unit.toString();
            if (timeUnits.containsKey(currentUnit)) {
                // if some unites are missed fill them in with 0
                // if the order of units "w-d-h-m-s-ms" is mixed - throw exception
                if (!currentUnit.equalsIgnoreCase(expectedUnit)) {
                    if (j == 0 || !parsedMap.containsKey(currentUnit)) {
                        while (!currentUnit.equalsIgnoreCase(expectedUnit)) {
                            parsedMap.put(unitOrder[j], 0);
                            expectedUnit = DurationParser.unitOrder[++j];
                        }
                    } else {
                        throw new IllegalArgumentException(durationStr + " - invalid date and time format");
                    }
                }
                parsedMap.put(unit.toString(), Integer.parseInt(number.toString()));
                number.delete(0, number.length());
                unit.delete(0, unit.length());
                i++;
                j++;
            } else {
                throw new IllegalArgumentException(durationStr + " - invalid date and time format");
            }
        }
        // if some unites are missed in the end fill them in with 0
        while (j < DurationParser.unitOrder.length) {
            parsedMap.put(unitOrder[j++], 0);
        }
        // TODO delete in the end
        System.out.println(parsedMap);
    }

    private String parsedMapToString() {
        StringBuffer result = new StringBuffer();
        result.append(primaryString).append(" = ");
        for (Map.Entry<String, Integer> entry : parsedMap.entrySet()) {
            int unitValue = entry.getValue();
            if (unitValue > 0) {
                String unitName = timeUnits.get(entry.getKey());
                result.append(unitValue).append(" ").append(unitName);
                if (unitValue > 1) {
                    result.append("s");
                }
                result.append(" ");
            }
        }
        return result.toString();
    }

    private long convertTimeIntoSeconds() {
        long seconds = 0;

        for (String unit : DurationParser.convertToSeconds) {
            int value = parsedMap.get(unit);
            long valueInSeconds = value * durationFactorsMap.get(unit);
            seconds += valueInSeconds;
        }
        return seconds;
    }

    private int convertTimeIntoNanoseconds() {
        int nanoseconds = 0;

        for (String unit : DurationParser.convertToNanoseconds) {
            int value = parsedMap.get(unit);
            int valueInNanoseconds = value * durationFactorsMap.get(unit);
            nanoseconds += valueInNanoseconds;
        }
        return nanoseconds;
    }

    @Override
    public Duration parse(String durationStr) {

        if (durationStr == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        durationStr.trim();
        if (durationStr.length() == 0) {
            throw new IllegalArgumentException("Date must not be empty");
        }
        if (durationStr.length() < 2) {
            throw new IllegalArgumentException(durationStr + " - invalid date and time format");
        }

        // fill in parsedMap
        parseStringIntoMap(durationStr);

        long durationInSeconds = convertTimeIntoSeconds();
        System.out.println(durationInSeconds);
        int durationInNanoseconds = convertTimeIntoNanoseconds();
        System.out.println(durationInNanoseconds);

        // if durationStr is valid - save its value
        primaryString = durationStr;

        return null;
    }

    @Override
    public String format(Duration duration) {
        return parsedMapToString();
        //return null;
    }

    public static void main(String[] args) {

        DurationParserFormatter pf = new DurationParserFormatter();
        String str1 = "1h20m10s50ms";
        Duration duration = pf.parse(str1);
        //Duration duration = pf.parse("20d24h");
        String durationStr = pf.format(duration);
        System.out.println(durationStr); // prints "3w"
    }
}
