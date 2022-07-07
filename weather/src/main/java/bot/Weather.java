package bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

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
}