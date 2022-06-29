package weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private final Location cityLocation;
    private final Current currentWeather;

    public Weather(@JsonProperty("location") Location location,
                   @JsonProperty("current") Current current) {
        this.cityLocation = location;
        this.currentWeather = current;
    }

    public Location getLocation() {
        return cityLocation;
    }

    public Current getCurrent() {
        return currentWeather;
    }
}