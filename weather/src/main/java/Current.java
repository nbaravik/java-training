import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {

    private final int temperature;
    private final List<String> weatherIcons;
    private final List<String> weatherDescriptions;
    private final int humidity;
    private final int cloudcover;
    private final int feelslike;

    public Current(@JsonProperty("temperature") int temperature,
                   @JsonProperty("weather_icons") List<String> weatherIcons,
                   @JsonProperty("weather_descriptions") List<String> weatherDescriptions,
                   @JsonProperty("humidity") int humidity,
                   @JsonProperty("cloudcover") int cloudcover,
                   @JsonProperty("feelslike") int feelslike) {
        this.temperature = temperature;
        this.weatherIcons = weatherIcons;
        this.weatherDescriptions = weatherDescriptions;
        this.humidity = humidity;
        this.cloudcover = cloudcover;
        this.feelslike = feelslike;
    }

    public int getTemperature() {
        return temperature;
    }

    public List<String> getWeatherIcons() {
        return weatherIcons;
    }

    public List<String> getWeatherDescriptions() {
        return weatherDescriptions;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloudcover() {
        return cloudcover;
    }

    public int getFeelslike() {
        return feelslike;
    }
}