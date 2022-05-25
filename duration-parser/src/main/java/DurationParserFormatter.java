import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DurationParserFormatter implements DurationFormatter, DurationParser {

    private static final DurationParserFormatter INSTANCE = new DurationParserFormatter();

    private DurationParserFormatter() {
    }

    public static DurationParserFormatter getInstance() {
        return INSTANCE;
    }

    private static final Map<String, Integer> unitFactorMap = new HashMap<>();

    static {
        unitFactorMap.put(DurationParser.UNIT_WEEK, 604_800_000);      // 1w in millis => x 604_800_000
        unitFactorMap.put(DurationParser.UNIT_DAY, 86_400_000);        // 1d in millis => x 86_400_000
        unitFactorMap.put(DurationParser.UNIT_HOUR, 3_600_000);        // 1h in millis => x 3_600_000
        unitFactorMap.put(DurationParser.UNIT_MINUTE, 60000);          // 1m in millis => x 60_000
        unitFactorMap.put(DurationParser.UNIT_SECOND, 1000);           // 1s in millis => x 1000
        unitFactorMap.put(DurationParser.UNIT_MILLISECOND, 1);         // 1ms in millis => 1 millis
    }

    private boolean isDigit(char ch) {
        int digit = ch - '0';
        return (digit >= 0 && digit <= 9);
    }

    private Map<String, Integer> parseStringIntoMap(String durationStr) {

        Map<String, Integer> stringIntoMap = new LinkedHashMap<>();

        StringBuilder number = new StringBuilder();
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
                throw new IllegalArgumentException(durationStr + " - invalid duration format");
            }

            String unit = String.valueOf(currentSymbol);
            // if there is a next char, and it is not a digit (ex: unit "ms")
            if ((i <= durationStr.length() - 2) && !isDigit(durationStr.charAt(i + 1))) {
                unit += durationStr.charAt(++i);
            }

            if (!unitFactorMap.containsKey(unit)) {
                throw new IllegalArgumentException(durationStr + " - invalid duration format");
            }

            if (!unit.equalsIgnoreCase(expectedUnit)) {
                // if the order of units "w-d-h-m-s-ms" is messed up - throw exception
                if ( j != 0 || stringIntoMap.containsKey(unit)) {
                    throw new IllegalArgumentException(durationStr + " - invalid duration format");
                }
                // if some unites are missed fill them in with 0
                while (!unit.equalsIgnoreCase(expectedUnit)) {
                    stringIntoMap.put(unitOrder[j], 0);
                    expectedUnit = DurationParser.unitOrder[++j];
                }
            }

            stringIntoMap.put(unit, Integer.parseInt(number.toString()));
            number.setLength(0);
            i++;
            j++;
        }

        // if some unites are missed in the end fill them in with 0
        while (j < DurationParser.unitOrder.length) {
            stringIntoMap.put(unitOrder[j++], 0);
        }
        return stringIntoMap;
    }

    private long convertTimeIntoMillis(Map<String, Integer> map) {
        long valueInMillis = 0L;

        for (String unit : DurationParser.unitOrder) {
            Integer value = map.get(unit);
            int factor = unitFactorMap.get(unit);
            valueInMillis += Integer.toUnsignedLong(value) * Integer.toUnsignedLong(factor);
        }
        return valueInMillis;
    }

    @Override
    public Duration parse(String durationStr) {

        if (durationStr == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        if (durationStr.length() == 0) {
            throw new IllegalArgumentException("Date must not be empty");
        }
        if (durationStr.length() < 2) {
            throw new IllegalArgumentException(durationStr + " - invalid duration format");
        }
        // fill in parsedMap
        Map<String, Integer> map = parseStringIntoMap(durationStr);
        long millis = convertTimeIntoMillis(map);

        return Duration.ofMillis(millis);
    }

    @Override
    public String format(Duration duration) {

        StringBuilder result = new StringBuilder();
        long millis = duration.toMillis();

        for (String unit : DurationParser.unitOrder) {
            long factor = unitFactorMap.get(unit);
            long keyValue = Math.toIntExact(millis / factor);
            if (keyValue != 0) {
                result.append(keyValue).append(unit);
                millis = millis % factor;
            }
        }
        return result.toString();
    }
}