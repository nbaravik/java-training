package bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private final String city;
    private final String country;
    private final LocalDateTime localtime;
    private final String timezoneId;

    public Location(@JsonProperty("name") String cityName,
                    @JsonProperty("country") String countryName,
                    @JsonProperty("localtime") LocalDateTime localtime,
                    @JsonProperty("timezone_id") String timezoneId) {
        this.city = cityName;
        this.country = countryName;
        this.localtime = localtime;
        this.timezoneId = timezoneId;
    }

    public String getCity() { return city; }

    public String getCountry() { return country; }

    public LocalDateTime getLocaltime() { return localtime; }

    public String getTimezoneId() { return timezoneId; }
}