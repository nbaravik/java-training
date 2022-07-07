package mvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @Autowired
    @JsonProperty("location")
    private Location cityLocation;

    @Autowired
    @JsonProperty("current")
    private Current currentWeather;
}