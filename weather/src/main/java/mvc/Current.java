package mvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {

    @Autowired
    @JsonProperty("temperature")
    private int temperature;

    @Autowired
    @JsonProperty("weather_icons")
    private List<String> weatherIcons;

    @Autowired
    @JsonProperty("weather_descriptions")
    private List<String> weatherDescriptions;

    @Autowired
    @JsonProperty("humidity")
    private int humidity;

    @Autowired
    @JsonProperty("cloudcover")
    private int cloudcover;

    @Autowired
    @JsonProperty("feelslike")
    private int feelslike;
}