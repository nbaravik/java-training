package bot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private static int EXPIRATION_PERIOD = 30;

    private final Location cityLocation;
    private final Current currentWeather;

    private final WeatherError error;

    public Weather(@JsonProperty("location") Location location,
                   @JsonProperty("current") Current current,
                   @JsonProperty("error") WeatherError err) {
        this.cityLocation = location;
        this.currentWeather = current;
        this.error = err;
    }

    public Location getLocation() {
        return cityLocation;
    }

    public Current getCurrent() {
        return currentWeather;
    }

    public WeatherError getError() { return error; }

    public boolean hasError() { return error != null; }

    @JsonIgnore
    public boolean isRelevant() {
        LocalDateTime localTime = LocalDateTime.now();

        LocalDateTime weatherTime = cityLocation.getLocaltime();
        ZoneId fromZone = ZoneId.of(cityLocation.getTimezoneId());
        ZoneId toZone = ZoneId.systemDefault();
        LocalDateTime weatherTimeInLocalZone = ZonedDateTime.of(weatherTime, fromZone).withZoneSameInstant(toZone).toLocalDateTime();

        LocalDateTime afterExpirationPeriod = weatherTimeInLocalZone.plusMinutes(EXPIRATION_PERIOD);
        return localTime.isBefore(afterExpirationPeriod);
    }
}